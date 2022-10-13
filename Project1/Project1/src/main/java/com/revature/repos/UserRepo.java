package com.revature.repos;

import com.revature.models.User;
import com.revature.utils.CRUDDaoInterface;
import com.revature.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepo implements CRUDDaoInterface<User> {
    private Connection conn;

    public UserRepo() {
        try {
            conn = ConnectionManager.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int create(User user) {
        String sql = "INSERT INTO users (id, username, pass_word, u_role) VALUES (default, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.isFinanceManager() ? "finance_manager" : "employee");

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();

            return rs.getInt("id");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    @Override
    public List<User> getAll() {
        ArrayList<User> users = new ArrayList<>();
        try {
            String sql = "SELECT * FROM users";

            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("pass_word"));
                user.setFinanceManager(rs.getString("u_role").equals("finance_manager"));

                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    @Override
    public User getByID(int id) {
        try {
            String sql = "SELECT * FROM users WHERE id=?";
            User user = new User();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("pass_word"));
                user.setFinanceManager(rs.getString("u_role").equals("finance_manager"));

                return user;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public User getByUsername(String username) {
        try {
            String sql = "SELECT * FROM users WHERE username=?";
            User user = new User();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("pass_word"));
                user.setFinanceManager(rs.getString("u_role").equals("finance_manager"));

                return user;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public User update(User user) {
        String sql = "UPDATE users SET pass_word = ?, u_role = ? WHERE username = ?";
        User updatedUser = new User();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.isFinanceManager() ? "finance_manager" : "employee");
            stmt.setString(3, user.getUsername());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                updatedUser.setUsername(rs.getString("username"));
                updatedUser.setPassword(rs.getString("pass_word"));
                updatedUser.setFinanceManager(rs.getString("u_role").equals("finance_manager"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return updatedUser;
    }

    public boolean delete(User user) {
        String sql = "DELETE FROM users WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(2, user.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
