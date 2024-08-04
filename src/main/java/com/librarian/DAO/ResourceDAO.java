package com.librarian.DAO;

import com.librarian.models.resources.Resource;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ResourceDAO {
    void addResource(Resource resource) throws SQLException;

    Resource fetchResource(String resourceId, String libraryId) throws SQLException;

    void updateResource(Resource resource) throws SQLException;

    void removeResource(String resourceId, String libraryId) throws SQLException;

    boolean resourceExists(String resourceId, String libraryId) throws SQLException;

    String reportMostBorrowed(String libraryId) throws SQLException;

    String reportBestSeller(String libraryId) throws SQLException;

    ArrayList<String> searchResources(String libraryId, String searchQuery) throws SQLException;
}
