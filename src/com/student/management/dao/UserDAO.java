package com.student.management.dao;

import com.student.management.db.DBConnection;
import com.student.management.models.User;

import java.sql.*;

public class UserDAO {

    // ✅ Register a new user (admin use only)
    public void registerUser(User user) {
        String sql = "INSERT INTO users(username, password, role) VALUES (?, SHA2(?, 256), ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); // plain text; will be hashed
            stmt.setString(3, user.getRole());
            stmt.executeUpdate();
            System.out.println("✅ User registered successfully.");
        } catch (SQLException e) {
            System.out.println("❌ Username already exists.");
        }
    }

    // ✅ Login using username + password
    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = SHA2(?, 256)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password); // plain text; hashed in SQL

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        null, // don't return password
                        rs.getString("role")
                );
            } else {
                System.out.println("❌ Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
