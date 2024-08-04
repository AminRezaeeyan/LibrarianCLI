package com.librarian.DAO.impl;

import com.librarian.DAO.LibraryDAO;
import com.librarian.models.Library;
import com.librarian.utils.DatabaseUtil;
import com.librarian.utils.TimeUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;

public class LibraryDAOImpl implements LibraryDAO {
    private static LibraryDAOImpl instance;
    private final Connection connection;

    private LibraryDAOImpl() {
        connection = DatabaseUtil.connect();
    }

    public static LibraryDAOImpl getInstance() {
        if (instance == null) instance = new LibraryDAOImpl();
        return instance;
    }

    @Override
    public void addLibrary(Library library) throws SQLException {
        String query = "INSERT INTO libraries (id, name, address, establish_year, table_count) " +
                "VALUES (?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, library.getId());
        ps.setString(2, library.getName());
        ps.setString(3, library.getAddress());
        ps.setString(4, library.getEstablishYear());
        ps.setInt(5, library.getTableCount());
        ps.executeUpdate();
    }

    @Override
    public Library fetchLibrary(String id) throws SQLException {
        String query = "SELECT * FROM libraries WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) return null;

        String name = rs.getString("name");
        String address = rs.getString("address");
        String establishYear = rs.getString("establish_year");
        int tableCount = rs.getInt("table_count");

        return new Library(id, name, address, establishYear, tableCount);
    }

    @Override
    public void updateLibrary(Library library) throws SQLException {
        String query = "UPDATE libraries SET name = ?, address = ?, establish_year = ?, table_count = ? " +
                "WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, library.getName());
        ps.setString(2, library.getAddress());
        ps.setString(3, library.getEstablishYear());
        ps.setInt(4, library.getTableCount());
        ps.setString(5, library.getId());
        ps.executeUpdate();
    }

    @Override
    public void removeLibrary(String id) throws SQLException {
        String query = "DELETE FROM libraries WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, id);
        ps.executeUpdate();
    }

    @Override
    public boolean libraryExists(String id) throws SQLException {
        String query = "SELECT 'library_found' FROM libraries WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    @Override
    public boolean isAvailableToRead(String resourceId, String libraryId, String startTime, String endTime) throws SQLException {
        String query = "SELECT 'overlapping_records' FROM read_records " +
                "WHERE resource_id = ? AND library_id = ? AND end_time > ? AND start_time < ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, resourceId);
        ps.setString(2, libraryId);
        ps.setString(3, startTime);
        ps.setString(4, endTime);
        ResultSet rs = ps.executeQuery();
        return !rs.next();
    }

    @Override
    public void readResource(String resourceId, String libraryId, String startTime, String endTime) throws SQLException {
        String query = "INSERT INTO read_records (resource_id, library_id, start_time, end_time) " +
                "VALUES (?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, resourceId);
        ps.setString(2, libraryId);
        ps.setString(3, startTime);
        ps.setString(4, endTime);
        ps.executeUpdate();
    }

    @Override
    public boolean hasBorrowed(String userId, String resourceId, String libraryId) throws SQLException {
        String query = "SELECT 'borrowed' FROM borrow_records " +
                "WHERE user_id = ? AND resource_id = ? AND library_id = ? AND return_time IS NULL";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, userId);
        ps.setString(2, resourceId);
        ps.setString(3, libraryId);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    @Override
    public void borrowResource(String userId, String resourceId, String libraryId, String borrowTime) throws SQLException {
        String query = "INSERT INTO borrow_records (user_id, resource_id, library_id, borrow_time) " +
                "VALUES (?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, userId);
        ps.setString(2, resourceId);
        ps.setString(3, libraryId);
        ps.setString(4, borrowTime);
        ps.executeUpdate();
    }

    @Override
    public Duration returnResource(String userId, String resourceId, String libraryId, String returnTime) throws SQLException {
        String fetchQuery = "SELECT borrow_time FROM borrow_records " +
                "WHERE user_id = ? AND resource_id = ? AND library_id = ?";
        PreparedStatement ps = connection.prepareStatement(fetchQuery);
        ps.setString(1, userId);
        ps.setString(2, resourceId);
        ps.setString(3, libraryId);
        ResultSet rs = ps.executeQuery();
        rs.next();

        String updateQuery = "UPDATE borrow_records SET return_time = ? " +
                "WHERE user_id = ? AND resource_id = ? AND library_id = ?";
        ps = connection.prepareStatement(updateQuery);
        ps.setString(1, returnTime);
        ps.setString(2, userId);
        ps.setString(3, resourceId);
        ps.setString(4, libraryId);
        ps.executeUpdate();

        return TimeUtil.calculateTimeDifference(rs.getString( "borrow_time"), returnTime);
    }
}
