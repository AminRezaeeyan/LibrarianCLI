package com.librarian.DAO.impl;

import com.librarian.DAO.ResourceDAO;
import com.librarian.models.resources.*;
import com.librarian.utils.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class ResourceDAOImpl implements ResourceDAO {
    private static ResourceDAOImpl instance;
    private final Connection connection;

    private ResourceDAOImpl() {
        connection = DatabaseUtil.connect();
    }

    public static ResourceDAOImpl getInstance() {
        if (instance == null) instance = new ResourceDAOImpl();
        return instance;
    }

    @Override
    public void addResource(Resource resource) throws SQLException {
        String query = "INSERT INTO resources (id, library_id, category_id, title, author, resource_type) " +
                "VAlUES (?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, resource.getId());
        ps.setString(2, resource.getLibraryId());
        ps.setString(3, resource.getCategoryId());
        ps.setString(4, resource.getTitle());
        ps.setString(5, resource.getAuthor());
        ps.setString(6, resource.getClass().getSimpleName());
        ps.executeUpdate();

        if (resource instanceof BorrowableBook) addBorrowableBook((BorrowableBook) resource);
        else if (resource instanceof Thesis) addThesis((Thesis) resource);
        else if (resource instanceof PurchasableBook) addPurchasableBook((PurchasableBook) resource);
        else if (resource instanceof TreasureBook) addTreasureBook((TreasureBook) resource);
    }

    @Override
    public Resource fetchResource(String resourceId, String libraryId) throws SQLException {
        String query = "SELECT * FROM resources WHERE id = ? AND library_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, resourceId);
        ps.setString(2, libraryId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) return null;

        String resourceType = rs.getString("resource_type");
        String categoryId = rs.getString("category_id");
        String title = rs.getString("title");
        String author = rs.getString("author");

        switch (resourceType) {
            case "BorrowableBook" :
                return fetchBorrowableBook(resourceId, libraryId, categoryId, title, author);
            case "Thesis":
                return fetchThesis(resourceId, libraryId, categoryId, title, author);
            case "PurchasableBook":
                return fetchPurchasableBook(resourceId, libraryId, categoryId, title, author);
            case "TreasureBook":
                return fetchTreasureBook(resourceId, libraryId, categoryId, title, author);
            default:
                throw new SQLException("Unknown resource type: " + resourceType);
        }
    }

    @Override
    public void updateResource(Resource resource) throws SQLException {
        String query = "UPDATE resources SET category_id = ?, title = ?, author = ? " +
                "WHERE id = ? AND library_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, resource.getCategoryId());
        ps.setString(2, resource.getTitle());
        ps.setString(3, resource.getAuthor());
        ps.setString(4, resource.getId());
        ps.setString(5, resource.getLibraryId());
        ps.executeUpdate();

        if (resource instanceof BorrowableBook) updateBorrowableBook((BorrowableBook) resource);
        else if (resource instanceof Thesis) updateThesis((Thesis) resource);
        else if (resource instanceof PurchasableBook) updatePurchasableBook((PurchasableBook) resource);
        else if (resource instanceof TreasureBook) updateTreasureBook((TreasureBook) resource);
    }

    @Override
    public void removeResource(String resourceId, String libraryId) throws SQLException {
        String query = "DELETE FROM resources WHERE id = ? AND library_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, resourceId);
        ps.setString(1, libraryId);
        ps.executeUpdate();
    }

    @Override
    public boolean resourceExists(String resourceId, String libraryId) throws SQLException {
        String query = "SELECT 'resource_found' FROM resources WHERE id = ? AND library_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, resourceId);
        ps.setString(2, libraryId);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    @Override
    public String reportMostBorrowed(String libraryId) throws SQLException {
        String query = "SELECT id, total_borrows_duration FROM borrowable_books WHERE library_id = ? " +
                "UNION SELECT id, total_borrows_duration FROM theses WHERE library_id = ? " +
                "ORDER BY total_borrows_duration DESC LIMIT 1";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, libraryId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) return "None";
        return "id: " + rs.getString("id") + "|total borrow duration to hours: " + Duration.of(rs.getLong("total_borrows_duration"),ChronoUnit.MINUTES).toHours();
    }

    @Override
    public String reportBestSeller(String libraryId) throws SQLException {
        String query = "SELECT id, total_purchase_profit FROM purchasable_books WHERE library_id = ? " +
                "ORDER BY total_purchase_profit DESC LIMIT 1";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, libraryId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) return "None";
        return "id: " + rs.getString("id") + "|total sell: " + rs.getLong("total_purchase_profit");
    }

    @Override
    public ArrayList<String> searchResources(String libraryId, String searchQuery) throws SQLException {
        ArrayList<String> resources = new ArrayList<>();
        String query = "SELECT * FROM resources WHERE (author || title) LIKE '%' || ? || '%' AND library_id = ? " +
                "UNION SELECT * FROM borrowable_books WHERE (publisher || publication_year) LIKE '%' || ? || '%' AND library_id = ? " +
                "UNION SELECT * FROM purchasable_books WHERE (publisher || publication_year) LIKE '%' || ? || '%' AND library_id = ? " +
                "UNION SELECT * FROM treasure_books WHERE (publisher || publication_year) LIKE '%' || ? || '%' AND library_id = ? " +
                "UNION SELECT * FROM these WHERE (advisor_name || defense_year) LIKE '%' || ? || '%' AND library_id = ? " +
                "ORDER BY id ASC";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, searchQuery);
        ps.setString(2, libraryId);
        ps.setString(3, searchQuery);
        ps.setString(4, libraryId);
        ps.setString(5, searchQuery);
        ps.setString(6, libraryId);
        ps.setString(7, searchQuery);
        ps.setString(8, libraryId);
        ps.setString(9, searchQuery);
        ps.setString(10, libraryId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            resources.add(rs.getString(1));
        }
        return resources;
    }


    private void addBorrowableBook(BorrowableBook book) throws SQLException {
        String query = "INSERT INTO borrowable_books (id, library_id, publisher, publication_year, copies_count, borrowed_copies_count, total_borrows_count, total_borrows_duration) " +
                "VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, book.getId());
        ps.setString(2, book.getLibraryId());
        ps.setString(3, book.getPublisher());
        ps.setString(4, book.getPublicationYear());
        ps.setInt(5, book.getCopiesCount());
        ps.setInt(6, book.getBorrowedCopiesCount());
        ps.setInt(7, book.getTotalBorrowsCount());
        ps.setLong(8, book.getTotalBorrowsDuration().toMinutes());
        ps.executeUpdate();
    }

    private void addThesis(Thesis book) throws SQLException {
        String query = "INSERT INTO theses (id, library_id, advisor_name, defense_year, copies_count,borrowed_copies_count, total_borrows_count, total_borrows_duration) " +
                "VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, book.getId());
        ps.setString(2, book.getLibraryId());
        ps.setString(3, book.getAdvisorName());
        ps.setString(4, book.getDefenseYear());
        ps.setInt(5, book.getCopiesCount());
        ps.setInt(6, book.getBorrowedCopiesCount());
        ps.setInt(7, book.getTotalBorrowsCount());
        ps.setLong(8, book.getTotalBorrowsDuration().toMinutes());
        ps.executeUpdate();
    }

    private void addPurchasableBook(PurchasableBook book) throws SQLException {
        String query = "INSERT INTO purchasable_books (id, library_id, publisher, publication_year, copies_count,purchased_copies_count, price, discount_percentage, total_purchase_profit) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, book.getId());
        ps.setString(2, book.getLibraryId());
        ps.setString(3, book.getPublisher());
        ps.setString(4, book.getPublicationYear());
        ps.setInt(5, book.getCopiesCount());
        ps.setInt(6, book.getPurchasedCopiesCount());
        ps.setInt(7, book.getPrice());
        ps.setInt(8, book.getDiscountPercentage());
        ps.setLong(9, book.getTotalPurchaseProfit());
        ps.executeUpdate();
    }

    private void addTreasureBook(TreasureBook book) throws SQLException {
        String query = "INSERT INTO treasure_books (id, library_id, publisher, publication_year, donor) " +
                "VALUES (?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, book.getId());
        ps.setString(2, book.getLibraryId());
        ps.setString(3, book.getPublisher());
        ps.setString(4, book.getPublicationYear());
        ps.setString(5, book.getDonor());
        ps.executeUpdate();
    }

    private BorrowableBook fetchBorrowableBook(String resourceId, String libraryId, String categoryId, String title, String author) throws SQLException {
        String query = "SELECT * FROM borrowable_books WHERE id = ? AND library_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, resourceId);
        ps.setString(2, libraryId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) throw new SQLException();

        String publisher = rs.getString("publisher");
        String publicationYear = rs.getString("publication_year");
        int copiesCount = rs.getInt("copies_count");
        int borrowedCopiesCount = rs.getInt("borrowed_copies_count");
        int totalBorrowsCount = rs.getInt("total_borrows_count");
        long totalBorrowsDuration = rs.getLong("total_borrows_duration");

        return new BorrowableBook(resourceId, libraryId, categoryId, title, author, publisher, publicationYear, copiesCount, borrowedCopiesCount, totalBorrowsCount, Duration.of(totalBorrowsDuration, ChronoUnit.MINUTES));
    }

    private Thesis fetchThesis(String resourceId, String libraryId, String categoryId, String title, String author) throws SQLException {
        String query = "SELECT * FROM theses WHERE id = ? AND library_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, resourceId);
        ps.setString(2, libraryId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) throw new SQLException();

        String advisorName = rs.getString("advisor_name");
        String defenseYear = rs.getString("defense_year");
        int copiesCount = rs.getInt("copies_count");
        int borrowedCopiesCount = rs.getInt("borrowed_copies_count");
        int totalBorrowsCount = rs.getInt("total_borrows_count");
        long totalBorrowsDuration = rs.getLong("total_borrows_duration");

        return new Thesis(resourceId, libraryId, categoryId, title, author, advisorName, defenseYear, copiesCount, borrowedCopiesCount, totalBorrowsCount, Duration.of(totalBorrowsDuration, ChronoUnit.MINUTES));
    }

    private PurchasableBook fetchPurchasableBook(String resourceId, String libraryId, String categoryId, String title, String author) throws SQLException {
        String query = "SELECT * FROM purchasable_books WHERE id = ? AND library_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, resourceId);
        ps.setString(2, libraryId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) throw new SQLException();

        String publisher = rs.getString("publisher");
        String publicationYear = rs.getString("publication_year");
        int copiesCount = rs.getInt("copies_count");
        int purchasedCopiesCount = rs.getInt("purchased_copies_count");
        int price = rs.getInt("price");
        int discountPercentage = rs.getInt("discount_percentage");
        long totalPurchaseProfit = rs.getLong("total_purchase_profit");

        return new PurchasableBook(resourceId, libraryId, categoryId, title, author, publisher, publicationYear, copiesCount, purchasedCopiesCount, price, discountPercentage, totalPurchaseProfit);
    }

    private TreasureBook fetchTreasureBook(String resourceId, String libraryId, String categoryId, String title, String author) throws SQLException {
        String query = "SELECT * FROM treasure_books WHERE id = ? AND library_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, resourceId);
        ps.setString(2, libraryId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) throw new SQLException();

        String publisher = rs.getString("publisher");
        String publicationYear = rs.getString("publication_year");
        String donor = rs.getString("donor");

        return new TreasureBook(resourceId, libraryId, categoryId, title, author, publisher, publicationYear, donor);
    }

    private void updateBorrowableBook(BorrowableBook book) throws SQLException {
        String query = "UPDATE borrowable_books SET publisher = ?, publication_year = ?, copies_count = ?,borrowed_copies_count = ?, total_borrows_count = ?, total_borrows_duration = ? " +
                "WHERE id = ? AND library_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, book.getPublisher());
        ps.setString(2, book.getPublicationYear());
        ps.setInt(3, book.getCopiesCount());
        ps.setInt(4, book.getBorrowedCopiesCount());
        ps.setInt(5, book.getTotalBorrowsCount());
        ps.setLong(6, book.getTotalBorrowsDuration().toMinutes());
        ps.setString(7, book.getId());
        ps.setString(8, book.getLibraryId());
        ps.executeUpdate();
    }

    private void updateThesis(Thesis thesis) throws SQLException {
        String query = "UPDATE theses SET advisor_name = ?, defense_year = ?, copies_count = ?,borrowed_copies_count = ?, total_borrows_count = ?, total_borrows_duration = ? " +
                "WHERE id = ? AND library_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, thesis.getAdvisorName());
        ps.setString(2, thesis.getDefenseYear());
        ps.setInt(3, thesis.getCopiesCount());
        ps.setInt(4, thesis.getBorrowedCopiesCount());
        ps.setInt(5, thesis.getTotalBorrowsCount());
        ps.setLong(6, thesis.getTotalBorrowsDuration().toMinutes());
        ps.setString(7, thesis.getId());
        ps.setString(8, thesis.getLibraryId());
        ps.executeUpdate();
    }

    private void updatePurchasableBook(PurchasableBook book) throws SQLException {
        String query = "UPDATE purchasable_books SET publisher = ?, publication_year = ?, copies_count = ?,price = ?, discount_percentage = ?, purchased_copies_count = ?, total_purchase_profit = ?" +
                "WHERE id = ? AND library_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, book.getPublisher());
        ps.setString(2, book.getPublicationYear());
        ps.setInt(3, book.getCopiesCount());
        ps.setInt(4, book.getPrice());
        ps.setInt(5, book.getDiscountPercentage());
        ps.setInt(6, book.getPurchasedCopiesCount());
        ps.setLong(7, book.getTotalPurchaseProfit());
        ps.setString(8, book.getId());
        ps.setString(9, book.getLibraryId());
        ps.executeUpdate();
    }

    private void updateTreasureBook(TreasureBook book) throws SQLException {
        String query = "UPDATE treasure_books SET publisher = ?, publication_year = ?, donor = ? " +
                "WHERE id = ? AND library_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, book.getPublisher());
        ps.setString(2, book.getPublicationYear());
        ps.setString(3, book.getDonor());
        ps.setString(4, book.getId());
        ps.setString(5, book.getLibraryId());
        ps.executeUpdate();
    }
}
