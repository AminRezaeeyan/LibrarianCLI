package com.librarian.services;

import com.librarian.DAO.CategoryDAO;
import com.librarian.exceptions.DuplicateEntryException;
import com.librarian.exceptions.LibraryServiceException;
import com.librarian.exceptions.NotFoundException;
import com.librarian.models.Category;
import com.librarian.DAO.impl.CategoryDAOImpl;

import java.sql.SQLException;

public class CategoryService {
    private static CategoryService instance;
    private final CategoryDAO categoryDAO;

    private CategoryService() {
        categoryDAO = CategoryDAOImpl.getInstance();
    }

    public static CategoryService getInstance() {
        if (instance == null) instance = new CategoryService();
        return instance;
    }

    public void addCategory(String id, String name, String parentCategoryId) throws LibraryServiceException, SQLException {
        if (categoryDAO.categoryExists(id)) throw new DuplicateEntryException();
        if (!categoryDAO.categoryExists(parentCategoryId)) throw new NotFoundException("Parent category not-found");
        categoryDAO.addCategory(new Category(id, name, parentCategoryId));
    }


    public void updateCategory(String id, String name, String parentCategoryId) throws LibraryServiceException, SQLException {
        Category category = categoryDAO.fetchCategory(id);
        if (category == null) throw new NotFoundException();

        if (!"-".equals(parentCategoryId) && !categoryDAO.categoryExists(parentCategoryId)) throw new NotFoundException("Parent category not found");
        if (!"-".equals(name)) category.setName(name);
        if (!"-".equals(parentCategoryId)) category.setParentCategoryId(parentCategoryId);

        categoryDAO.updateCategory(category);
    }

    public void removeCategory(String id) throws LibraryServiceException, SQLException {
        if (!categoryDAO.categoryExists(id)) throw new NotFoundException();
        categoryDAO.removeCategory(id);
    }
}
