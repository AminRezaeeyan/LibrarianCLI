package com.librarian.services;

import com.librarian.DAO.UserDAO;
import com.librarian.DAO.impl.UserDAOImpl;
import com.librarian.exceptions.*;
import com.librarian.models.users.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserService {
    private static UserService instance;
    private final UserDAO userDAO;

    private UserService() {
        userDAO = UserDAOImpl.getInstance();
    }

    public static UserService getInstance() {
        if (instance == null) instance = new UserService();
        return instance;
    }

    public void addStaff(String id, String firstname, String lastname, String nationalId, String birthYear, String address, String role) throws LibraryServiceException, SQLException {
        if(userDAO.userExists(id)) throw new DuplicateEntryException();
        userDAO.addUser(new Staff(id, firstname, lastname, nationalId, birthYear, address,0,0, role));
    }

    public void addProfessor(String id, String firstname, String lastname, String nationalId, String birthYear, String address, String department) throws LibraryServiceException, SQLException {
        if(userDAO.userExists(id)) throw new DuplicateEntryException();
        userDAO.addUser(new Professor(id, firstname, lastname, nationalId, birthYear, address,0,0, department));
    }

    public void addGraduateStudent(String id, String firstname, String lastname, String nationalId, String birthYear, String address, String advisorName) throws LibraryServiceException, SQLException {
        if(userDAO.userExists(id)) throw new DuplicateEntryException();
        userDAO.addUser(new GraduateStudent(id, firstname, lastname, nationalId, birthYear, address,0,0, advisorName));
    }

    public void addUndergraduateStudent(String id, String firstname, String lastname, String nationalId, String birthYear, String address) throws LibraryServiceException, SQLException {
        if(userDAO.userExists(id)) throw new DuplicateEntryException();
        userDAO.addUser(new UndergraduateStudent(id, firstname, lastname, nationalId, birthYear, address,0,0));
    }

    public void updateStaff(String id, String firstname, String lastname, String nationalId, String birthYear, String address, String role) throws LibraryServiceException, SQLException {
        User user = userDAO.fetchUser(id);
        if (user == null || !(user instanceof Staff)) throw new NotFoundException();
        Staff staff = (Staff) user;

        if (!"-".equals(firstname)) staff.setFirstname(firstname);
        if (!"-".equals(lastname)) staff.setLastname(lastname);
        if (!"-".equals(nationalId)) staff.setNationalId(nationalId);
        if (!"-".equals(birthYear)) staff.setBirthYear(birthYear);
        if (!"-".equals(address)) staff.setAddress(address);
        if (!"-".equals(role)) staff.setRole(role);

        userDAO.updateUser(staff);
    }

    public void updateProfessor(String id, String firstname, String lastname, String nationalId, String birthYear, String address, String department) throws LibraryServiceException, SQLException {
        User user = userDAO.fetchUser(id);
        if (user == null || !(user instanceof Professor)) throw new NotFoundException();
        Professor professor = (Professor) user;

        if (!"-".equals(firstname)) professor.setFirstname(firstname);
        if (!"-".equals(lastname)) professor.setLastname(lastname);
        if (!"-".equals(nationalId)) professor.setNationalId(nationalId);
        if (!"-".equals(birthYear)) professor.setBirthYear(birthYear);
        if (!"-".equals(address)) professor.setAddress(address);
        if (!"-".equals(department)) professor.setDepartment(department);

        userDAO.updateUser(professor);
    }

    public void updateGraduateStudent(String id, String firstname, String lastname, String nationalId, String birthYear, String address, String advisorName) throws LibraryServiceException, SQLException {
        User user = userDAO.fetchUser(id);
        if (user == null || !(user instanceof GraduateStudent)) throw new NotFoundException();
        GraduateStudent student = (GraduateStudent) user;

        if (!"-".equals(firstname)) student.setFirstname(firstname);
        if (!"-".equals(lastname)) student.setLastname(lastname);
        if (!"-".equals(nationalId)) student.setNationalId(nationalId);
        if (!"-".equals(birthYear)) student.setBirthYear(birthYear);
        if (!"-".equals(address)) student.setAddress(address);
        if (!"-".equals(advisorName)) student.setAdvisorName(advisorName);

        userDAO.updateUser(student);
    }

    public void updateUndergraduateStudent(String id, String firstname, String lastname, String nationalId, String birthYear, String address) throws LibraryServiceException, SQLException {
        User user = userDAO.fetchUser(id);
        if (user == null || !(user instanceof GraduateStudent)) throw new NotFoundException();
        UndergraduateStudent student = (UndergraduateStudent) user;

        if (!"-".equals(firstname)) student.setFirstname(firstname);
        if (!"-".equals(lastname)) student.setLastname(lastname);
        if (!"-".equals(nationalId)) student.setNationalId(nationalId);
        if (!"-".equals(birthYear)) student.setBirthYear(birthYear);
        if (!"-".equals(address)) student.setAddress(address);

        userDAO.updateUser(student);
    }

    public void removeUser(String id) throws LibraryServiceException, SQLException {
        User user = userDAO.fetchUser(id);
        if(user == null) throw new NotFoundException();
        if (user.hasBorrowed()) throw new NotAllowedException("User has borrowed a resource");
        userDAO.removeUser(id);
    }

    public long reportPenaltiesSum() throws SQLException {
        return userDAO.reportPenaltiesSum();
    }

    public String searchUsers(String searchQuery) throws SQLException {
        ArrayList<String> users = userDAO.searchUsers(searchQuery);
        return users.isEmpty() ? "None" : String.join("|", users);
    }
}
