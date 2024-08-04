package com.librarian.services;

import com.librarian.DAO.CategoryDAO;
import com.librarian.DAO.ResourceDAO;
import com.librarian.DAO.impl.CategoryDAOImpl;
import com.librarian.DAO.impl.ResourceDAOImpl;
import com.librarian.exceptions.*;
import com.librarian.models.resources.*;

import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;

public class ResourceService {
    private static ResourceService instance;
    private final ResourceDAO resourceDAO;
    private final CategoryDAO categoryDAO;

    private ResourceService() {
        resourceDAO = ResourceDAOImpl.getInstance();
        categoryDAO = CategoryDAOImpl.getInstance();
    }

    public static ResourceService getInstance() {
        if (instance == null) instance = new ResourceService();
        return instance;
    }

    public void addBorrowableBook(String id, String libraryId, String categoryId, String title, String author, String publisher, String publicationYear, int copiesCount) throws LibraryServiceException, SQLException {
        if (resourceDAO.resourceExists(id, libraryId)) throw new DuplicateEntryException();
        if (!categoryDAO.categoryExists(categoryId)) throw new NotFoundException();
        resourceDAO.addResource(new BorrowableBook(id, libraryId, categoryId, title, author, publisher, publicationYear, copiesCount, 0, 0, Duration.ZERO));
    }

    public void addThesis(String id, String libraryId, String categoryId, String title, String author, String advisorName, String defenseYear, int copiesCount) throws LibraryServiceException, SQLException {
        if (resourceDAO.resourceExists(id, libraryId)) throw new DuplicateEntryException();
        if (!categoryDAO.categoryExists(categoryId)) throw new NotFoundException();
        resourceDAO.addResource(new Thesis(id, libraryId, categoryId, title, author, advisorName, defenseYear, copiesCount, 0, 0, Duration.ZERO));
    }

    public void addPurchasableBook(String id, String libraryId, String categoryId, String title, String author, String publisher, String publicationYear, int copiesCount, int price, int discountPercentage) throws LibraryServiceException, SQLException {
        if (resourceDAO.resourceExists(id, libraryId)) throw new DuplicateEntryException();
        if (!categoryDAO.categoryExists(categoryId)) throw new NotFoundException();
        resourceDAO.addResource(new PurchasableBook(id, libraryId, categoryId, title, author, publisher, publicationYear, copiesCount, 0, price, discountPercentage, 0));
    }

    public void addTreasureBook(String id, String libraryId, String categoryId, String title, String author, String publisher, String publicationYear, String donor) throws LibraryServiceException, SQLException {
        if (resourceDAO.resourceExists(id, libraryId)) throw new DuplicateEntryException();
        if (!categoryDAO.categoryExists(categoryId)) throw new NotFoundException();
        resourceDAO.addResource(new TreasureBook(id, libraryId, categoryId, title, author, publisher, publicationYear, donor));
    }

    public void updateBorrowableBook(String id, String libraryId, String categoryId, String title, String author, String publisher, String publicationYear, String copiesCount) throws LibraryServiceException, SQLException {
        Resource resource = resourceDAO.fetchResource(id, libraryId);
        if (resource == null || !(resource instanceof BorrowableBook)) throw new NotFoundException();
        if (!"-".equals(categoryId) && !categoryDAO.categoryExists(categoryId)) throw new NotFoundException("Category not-found");

        BorrowableBook book = (BorrowableBook) resource;
        if (!"-".equals(copiesCount) && book.getBorrowedCopiesCount() > Integer.parseInt(copiesCount)) throw new NotAllowedException("Resources are borrowed and you can't reduce copies count");
        if (!"-".equals(categoryId)) book.setCategoryId(categoryId);
        if (!"-".equals(title)) book.setTitle(title);
        if (!"-".equals(author)) book.setAuthor(author);
        if (!"-".equals(publisher)) book.setPublisher(publisher);
        if (!"-".equals(publicationYear)) book.setPublicationYear(publicationYear);
        if (!"-".equals(copiesCount)) book.setCopiesCount(Integer.parseInt(copiesCount));

        resourceDAO.updateResource(book);
    }

    public void updateThesis(String id, String libraryId, String categoryId, String title, String author, String advisorName, String defenseYear, String copiesCount) throws LibraryServiceException, SQLException {
        Resource resource = resourceDAO.fetchResource(id, libraryId);
        if (resource == null || !(resource instanceof Thesis)) throw new NotFoundException();
        if (!"-".equals(categoryId) && !categoryDAO.categoryExists(categoryId)) throw new NotFoundException("Category not-found");

        Thesis thesis = (Thesis) resource;
        if (!"-".equals(copiesCount) && thesis.getBorrowedCopiesCount() > Integer.parseInt(copiesCount)) throw new NotAllowedException("Resources are borrowed and you can't reduce copies count");
        if (!"-".equals(categoryId)) thesis.setCategoryId(categoryId);
        if (!"-".equals(title)) thesis.setTitle(title);
        if (!"-".equals(author)) thesis.setAuthor(author);
        if (!"-".equals(advisorName)) thesis.setAdvisorName(advisorName);
        if (!"-".equals(defenseYear)) thesis.setDefenseYear(defenseYear);
        if (!"-".equals(copiesCount)) thesis.setCopiesCount(Integer.parseInt(copiesCount));

        resourceDAO.updateResource(thesis);
    }

    public void updatePurchasableBook(String id, String libraryId, String categoryId, String title, String author, String publisher, String publicationYear, String copiesCount, String price, String discountPercentage) throws LibraryServiceException, SQLException {
        Resource resource = resourceDAO.fetchResource(id, libraryId);
        if (resource == null || !(resource instanceof PurchasableBook)) throw new NotFoundException();
        if (!"-".equals(categoryId) && !categoryDAO.categoryExists(categoryId)) throw new NotFoundException("Category not-found");

        PurchasableBook book = (PurchasableBook) resource;

        if (!"-".equals(categoryId)) book.setCategoryId(categoryId);
        if (!"-".equals(title)) book.setTitle(title);
        if (!"-".equals(author)) book.setAuthor(author);
        if (!"-".equals(publisher)) book.setPublisher(publisher);
        if (!"-".equals(publicationYear)) book.setPublicationYear(publicationYear);
        if (!"-".equals(copiesCount)) book.setCopiesCount(Integer.parseInt(copiesCount));
        if (!"-".equals(price)) book.setPrice(Integer.parseInt(price));
        if (!"-".equals(discountPercentage)) book.setDiscountPercentage(Integer.parseInt(discountPercentage));

        resourceDAO.updateResource(book);
    }

    public void updateTreasureBook(String id, String libraryId, String categoryId, String title, String author, String publisher, String publicationYear, String donor) throws LibraryServiceException, SQLException {
        Resource resource = resourceDAO.fetchResource(id, libraryId);
        if (resource == null || !(resource instanceof TreasureBook)) throw new NotFoundException();
        if (!"-".equals(categoryId) && !categoryDAO.categoryExists(categoryId)) throw new NotFoundException("Category not-found");

        TreasureBook book = (TreasureBook) resource;

        if (!"-".equals(categoryId)) book.setCategoryId(categoryId);
        if (!"-".equals(title)) book.setTitle(title);
        if (!"-".equals(author)) book.setAuthor(author);
        if (!"-".equals(publisher)) book.setPublisher(publisher);
        if (!"-".equals(publicationYear)) book.setPublicationYear(publicationYear);
        if (!"-".equals(donor)) book.setDonor(donor);

        resourceDAO.updateResource(book);
    }

    public void removeResource(String resourceId, String libraryId) throws LibraryServiceException, SQLException {
        if (!resourceDAO.resourceExists(resourceId, libraryId)) throw new NotFoundException();

        Resource resource = resourceDAO.fetchResource(resourceId, libraryId);
        if (resource instanceof Borrowable && ((Borrowable) resource).isBorrowed()) throw new NotAllowedException("Resource is borrowed");

        resourceDAO.removeResource(resourceId, libraryId);
    }

    public String reportBestSeller(String libraryId) throws SQLException {
        return resourceDAO.reportBestSeller(libraryId);
    }

    public String reportMostBorrowed(String libraryId) throws SQLException {
        return resourceDAO.reportMostBorrowed(libraryId);
    }

    public String searchResources(String libraryId, String searchQuery) throws SQLException {
        ArrayList<String> resources = resourceDAO.searchResources(libraryId, searchQuery);
        return resources.isEmpty() ? "None" : String.join("|",resources);
    }
}
