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
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        null,
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 🔁 Fallback: check in students table using studentId and DOB
        try {
            int studentId = Integer.parseInt(username); // username = studentId
            String sql2 = "SELECT * FROM students WHERE id = ? AND dob = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql2)) {
                stmt.setInt(1, studentId);
                stmt.setDate(2, Date.valueOf(password)); // password = dob in yyyy-mm-dd

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new User(studentId, String.valueOf(studentId), null, "student");
                }
            }
        } catch (Exception e) {
            // Ignore fallback login error
        }

        System.out.println("❌ Invalid username or password.");
        return null;
    }

}
