package com.librarian.services;

import com.librarian.DAO.LibraryDAO;
import com.librarian.DAO.ResourceDAO;
import com.librarian.DAO.UserDAO;
import com.librarian.DAO.impl.LibraryDAOImpl;
import com.librarian.DAO.impl.ResourceDAOImpl;
import com.librarian.DAO.impl.UserDAOImpl;
import com.librarian.exceptions.*;
import com.librarian.models.Library;
import com.librarian.models.resources.*;
import com.librarian.models.resources.Readable;
import com.librarian.models.users.Professor;
import com.librarian.models.users.User;
import com.librarian.utils.PropertyUtil;
import com.librarian.utils.TimeUtil;

import java.sql.SQLException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class LibraryService {
    private static LibraryService instance;
    private final LibraryDAO libraryDAO;
    private final UserDAO userDAO;
    private final ResourceDAO resourceDAO;

    private LibraryService() {
        libraryDAO = LibraryDAOImpl.getInstance();
        userDAO = UserDAOImpl.getInstance();
        resourceDAO = ResourceDAOImpl.getInstance();
    }

    public static LibraryService getInstance() {
        if (instance == null) instance = new LibraryService();
        return instance;
    }

    public void addLibrary(String id, String name, String address, String establishYear, int tableCount) throws LibraryServiceException, SQLException {
        if (libraryDAO.libraryExists(id)) throw new DuplicateEntryException();
        libraryDAO.addLibrary(new Library(id, name, address, establishYear, tableCount));
    }

    public void updateLibrary(String id, String name, String address, String establishYear, String tableCount) throws LibraryServiceException, SQLException {
        Library library = libraryDAO.fetchLibrary(id);
        if (library == null) throw new NotFoundException();

        if (!"-".equals(name)) library.setName(name);
        if (!"-".equals(address)) library.setAddress(address);
        if (!"-".equals(establishYear)) library.setEstablishYear(establishYear);
        if (!"-".equals(tableCount)) library.setTableCount(Integer.parseInt(tableCount));

        libraryDAO.updateLibrary(library);
    }

    public void removeLibrary(String id) throws LibraryServiceException, SQLException {
        if (!libraryDAO.libraryExists(id)) throw new NotFoundException();
        libraryDAO.removeLibrary(id);
    }

    public void buyResource(String userId, String resourceId, String libraryId) throws LibraryServiceException, SQLException {
        Resource resource = resourceDAO.fetchResource(resourceId, libraryId);
        if (!userDAO.userExists(userId) || resource == null) throw new NotFoundException();
        if (!(resource instanceof Purchasable) || !((Purchasable) resource).isAvailable())
            throw new NotAllowedException();
        ((Purchasable) resource).purchase();
        resourceDAO.updateResource(resource);
    }

    public void readResource(String userId, String resourceId, String libraryId, String startTime, String endTime) throws LibraryServiceException, SQLException {
        Resource resource = resourceDAO.fetchResource(resourceId, libraryId);
        User user = userDAO.fetchUser(userId);
        if (user == null || resource == null) throw new NotFoundException();
        if (!(resource instanceof Readable) || !(user instanceof Professor)) throw new NotAllowedException();

        long durationToMinutes = TimeUtil.calculateTimeDifference(startTime, endTime).toMinutes();
        long minDuration = PropertyUtil.getValue("bookReadingTime.min");
        long maxDuration = PropertyUtil.getValue("bookReadingTime.max");

        if (durationToMinutes > maxDuration || durationToMinutes < minDuration) throw new NotAllowedException("Duration must be between " + minDuration + " and " + maxDuration + " minutes");
        if (!libraryDAO.isAvailableToRead(resourceId, libraryId, startTime, endTime)) throw new NotAllowedException("This resource isn't available in your time");
        libraryDAO.readResource(resourceId, libraryId, startTime, endTime);
    }

    public void borrowResource(String userId, String resourceId, String libraryId, String borrowTime) throws LibraryServiceException, SQLException {
        Resource resource = resourceDAO.fetchResource(resourceId, libraryId);
        User user = userDAO.fetchUser(userId);

        if (user == null || resource == null) throw new NotFoundException();
        if (!(resource instanceof Borrowable) || !((Borrowable) resource).isAvailable()) throw new NotAllowedException();
        if (user.hasPenalty() || libraryDAO.hasBorrowed(userId, resourceId, libraryId)) throw new NotAllowedException();
        if (user.getBorrowCount() >= PropertyUtil.getValue("borrowCountLimit", user.getClass().getSimpleName())) throw new NotAllowedException("User has already reached the borrow limit");

        ((Borrowable) resource).borrowResource();
        user.borrowResource();

        libraryDAO.borrowResource(userId, resourceId, libraryId, borrowTime);
        resourceDAO.updateResource(resource);
        userDAO.updateUser(user);
    }

    public String returnResource(String userId, String resourceId, String libraryId, String returnTime) throws LibraryServiceException, SQLException {
        Resource resource = resourceDAO.fetchResource(resourceId, libraryId);
        User user = userDAO.fetchUser(userId);

        if (user == null || resource == null) throw new NotFoundException();
        if (!libraryDAO.hasBorrowed(userId, resourceId, libraryId)) throw new NotFoundException();

        Duration borrowDuration = libraryDAO.returnResource(userId, resourceId, libraryId, returnTime);
        ((Borrowable) resource).returnResource(borrowDuration);

        long hourlyPenaltyRate = PropertyUtil.getValue("penaltyAmount",user.getClass().getSimpleName());
        int resourceBorrowDeadline = PropertyUtil.getValue("borrowDeadline", resource.getClass().getSimpleName(), user.getClass().getSimpleName());

        long overdueHours = borrowDuration.minus(Duration.of(resourceBorrowDeadline, ChronoUnit.DAYS)).toHours();
        long penalty = hourlyPenaltyRate * (overdueHours > 0 ? overdueHours : 0);

        user.returnResource(penalty);
        resourceDAO.updateResource(resource);
        userDAO.updateUser(user);

        return penalty > 0 ? "penalty: " + Long.toString(penalty) : "success";
    }
}
