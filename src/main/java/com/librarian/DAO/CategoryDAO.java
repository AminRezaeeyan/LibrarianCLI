package com.librarian.DAO;

import com.librarian.models.Category;

import java.sql.SQLException;

public interface CategoryDAO {
    void addCategory(Category category) throws SQLException;

    Category fetchCategory(String id) throws SQLException;

    void updateCategory(Category category) throws SQLException;

    void removeCategory(String id) throws SQLException;

    boolean categoryExists(String id) throws SQLException;
}
