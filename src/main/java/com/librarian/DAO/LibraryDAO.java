package com.librarian.DAO;

import com.librarian.models.Library;

import java.sql.SQLException;
import java.time.Duration;

public interface LibraryDAO {
    void addLibrary(Library library) throws SQLException;

    Library fetchLibrary(String id) throws SQLException;

    void updateLibrary(Library library) throws SQLException;

    void removeLibrary(String id) throws SQLException;

    boolean libraryExists(String id) throws SQLException;

    boolean isAvailableToRead(String resourceId, String libraryId, String startTime, String endTime) throws SQLException;

    void readResource(String resourceId, String libraryId, String startTime, String endTime) throws SQLException;

    boolean hasBorrowed(String userId, String resourceId, String libraryId) throws SQLException;

    void borrowResource(String userId, String resourceId, String libraryId, String borrowTime) throws SQLException;

    Duration returnResource(String userId, String resourceId, String libraryId, String returnTime) throws SQLException;
}
