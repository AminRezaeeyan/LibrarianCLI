package com.librarian.DAO.impl;

import com.librarian.DAO.UserDAO;
import com.librarian.models.users.*;
import com.librarian.utils.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {
    private static UserDAOImpl instance;
    private final Connection connection;

    private UserDAOImpl() {
        connection = DatabaseUtil.connect();
    }

    public static UserDAOImpl getInstance() {
        if (instance == null) instance = new UserDAOImpl();
        return instance;
    }

    @Override
    public void addUser(User user) throws SQLException {
        String query = "INSERT INTO users (id, user_type, first_name, last_name, national_id, birth_year, address, advisor_name, department, role)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, user.getId());
        ps.setString(2, user.getClass().getSimpleName());
        ps.setString(3, user.getFirstname());
        ps.setString(4, user.getLastname());
        ps.setString(5, user.getNationalId());
        ps.setString(6, user.getBirthYear());
        ps.setString(7, user.getAddress());
        ps.setNull(8, java.sql.Types.VARCHAR);
        ps.setNull(9, java.sql.Types.VARCHAR);
        ps.setNull(10, java.sql.Types.VARCHAR);

        if (user instanceof GraduateStudent) {
            ps.setString(8, ((GraduateStudent) user).getAdvisorName());
        } else if (user instanceof Professor) {
            ps.setString(9, ((Professor) user).getDepartment());
        } else if (user instanceof Staff) {
            ps.setString(10, ((Staff) user).getRole());
        }
        ps.executeUpdate();
    }

    @Override
    public User fetchUser(String id) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) return null;

        String userType = rs.getString("user_type");
        String firstname = rs.getString("first_name");
        String lastname = rs.getString("last_name");
        String nationalId = rs.getString("national_id");
        String birthYear = rs.getString("birth_year");
        String address = rs.getString("address");
        int borrowCount = rs.getInt("borrow_count");
        long penalty = rs.getLong("penalty");
        String role = rs.getString("role");
        String department = rs.getString("department");
        String advisorName = rs.getString("advisor_name");


        switch (userType) {
            case "Staff" -> {
                return new Staff(id, firstname, lastname, nationalId, birthYear, address, borrowCount, penalty, role);
            }
            case "Professor" -> {
                return new Professor(id, firstname, lastname, nationalId, birthYear, address, borrowCount, penalty, department);
            }
            case "GraduateStudent" -> {
                return new GraduateStudent(id, firstname, lastname, nationalId, birthYear, address, borrowCount, penalty, advisorName);
            }
            case "UndergraduateStudent" -> {
                return new UndergraduateStudent(id, firstname, lastname, nationalId, birthYear, address, borrowCount, penalty);
            }
            default -> throw new SQLException("Unknown user type: " + userType);
        }
    }

    @Override
    public void updateUser(User user) throws SQLException {
        String query = "UPDATE users SET first_name = ?, last_name = ?, national_id = ?, birth_year = ?, address = ?, advisor_name = ?, department = ?, role = ? , borrow_count = ?, penalty = ?" +
                "WHERE id = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, user.getFirstname());
        ps.setString(2, user.getLastname());
        ps.setString(3, user.getNationalId());
        ps.setString(4, user.getBirthYear());
        ps.setString(5, user.getAddress());
        ps.setNull(6, java.sql.Types.VARCHAR);
        ps.setNull(7, java.sql.Types.VARCHAR);
        ps.setNull(8, java.sql.Types.VARCHAR);
        ps.setInt(9, user.getBorrowCount());
        ps.setLong(10, user.getPenalty());
        ps.setString(11, user.getId());


        if (user instanceof GraduateStudent) {
            ps.setString(6, ((GraduateStudent) user).getAdvisorName());
        } else if (user instanceof Professor) {
            ps.setString(7, ((Professor) user).getDepartment());
        } else if (user instanceof Staff) {
            ps.setString(8, ((Staff) user).getRole());
        }
        ps.executeUpdate();
    }

    @Override
    public void removeUser(String id) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, id);
        ps.executeUpdate();
    }

    @Override
    public boolean userExists(String id) throws SQLException {
        String query = "SELECT 'user_found' FROM users WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    @Override
    public long reportPenaltiesSum() throws SQLException {
        String query = "SELECT SUM(penalty) FROM users";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) return 0;
        return rs.getLong(1);
    }

    @Override
    public ArrayList<String> searchUsers(String searchQuery) throws SQLException {
        ArrayList<String> users = new ArrayList<>();
        String query = "SELECT id FROM users WHERE (first_name || last_name || national_id || address || COALESCE(advisor_name, '') || COALESCE(department, '') || COALESCE(role, '')) LIKE '%' || ? || '%'" +
                "ORDER BY id ASC";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, searchQuery);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            users.add(rs.getString(1));
        }
        return users;
    }

}
