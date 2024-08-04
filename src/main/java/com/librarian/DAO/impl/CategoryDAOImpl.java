package com.librarian.DAO.impl;

import com.librarian.DAO.CategoryDAO;
import com.librarian.models.Category;
import com.librarian.utils.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDAOImpl implements CategoryDAO {
    private static CategoryDAOImpl instance;
    private final Connection connection;

    private CategoryDAOImpl() {
        connection = DatabaseUtil.connect();
    }

    public static CategoryDAOImpl getInstance() {
        if (instance == null) instance = new CategoryDAOImpl();
        return instance;
    }

    @Override
    public void addCategory(Category category) throws SQLException {
        String query = "INSERT INTO categories (id, name, parent_category_id) " +
                "VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, category.getId());
        ps.setString(2, category.getName());
        ps.setString(3, category.getParentCategoryId());
        ps.executeUpdate();
    }

    @Override
    public Category fetchCategory(String id) throws SQLException {
        String query = "SELECT * FROM categories WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) return null;

        String name = rs.getString("name");
        String parentCategoryId = rs.getString("parent_category_id");
        return new Category(id, name, parentCategoryId);
    }

    @Override
    public void updateCategory(Category category) throws SQLException {
        String query = "UPDATE categories SET name = ?, parent_category_id = ? " +
                "WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, category.getName());
        ps.setString(2, category.getParentCategoryId());
        ps.setString(3, category.getId());
        ps.executeUpdate();
    }

    @Override
    public void removeCategory(String id) throws SQLException {
        String query = "DELETE FROM categories WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, id);
        ps.executeUpdate();
    }

    @Override
    public boolean categoryExists(String id) throws SQLException {
        String query = "SELECT 'category_found' FROM categories WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
}
