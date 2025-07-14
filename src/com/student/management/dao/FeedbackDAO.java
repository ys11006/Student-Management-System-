package com.student.management.dao;

import com.student.management.db.DBConnection;
import com.student.management.models.Feedback;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {

    public void submitFeedback(Feedback feedback) {
        String sql = "INSERT INTO feedback(student_id, course_id, feedback_text, submitted_on) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, feedback.getStudentId());
            stmt.setInt(2, feedback.getCourseId());
            stmt.setString(3, feedback.getFeedbackText());
            stmt.setDate(4, feedback.getSubmittedOn());
            stmt.executeUpdate();
            System.out.println("Feedback submitted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Feedback> getAllFeedback() {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT * FROM feedback";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Feedback fb = new Feedback(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getInt("course_id"),
                        rs.getString("feedback_text"),
                        rs.getDate("submitted_on")
                );
                list.add(fb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
