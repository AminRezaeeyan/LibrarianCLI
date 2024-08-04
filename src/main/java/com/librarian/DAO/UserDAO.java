package com.librarian.DAO;

import com.librarian.models.users.Staff;
import com.librarian.models.users.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO {
    void addUser(User user) throws SQLException;

    User fetchUser(String id) throws SQLException;

    void updateUser(User user) throws SQLException;

    void removeUser(String id) throws SQLException;

    boolean userExists(String id) throws SQLException;

    long reportPenaltiesSum() throws SQLException;

    ArrayList<String> searchUsers(String searchQuery) throws SQLException;
}
