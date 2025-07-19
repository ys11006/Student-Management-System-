package com.student.management.dao;

import com.student.management.db.DBConnection;
import com.student.management.models.Feedback;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {

    // ✅ Student submits feedback
    public void submitFeedback(Feedback feedback) {
        String sql = "INSERT INTO feedback(student_id, course_id, feedback_text, submitted_on) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, feedback.getStudentId());
            stmt.setInt(2, feedback.getCourseId());
            stmt.setString(3, feedback.getFeedbackText());
            stmt.setTimestamp(4, Timestamp.valueOf(feedback.getSubmittedOn()));  // Use LocalDateTime
            stmt.executeUpdate();
            System.out.println("✅ Feedback submitted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Admin views all feedback with joined student + course info
    public List<Feedback> getAllFeedback() {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT f.id, f.feedback_text, f.submitted_on, " +
                "s.name AS student_name, c.name AS course_name " +
                "FROM feedback f " +
                "JOIN students s ON f.student_id = s.id " +
                "JOIN courses c ON f.course_id = c.id " +
                "ORDER BY f.submitted_on DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Feedback fb = new Feedback();
                fb.setId(rs.getInt("id"));
                fb.setFeedbackText(rs.getString("feedback_text"));
                fb.setSubmittedOn(rs.getTimestamp("submitted_on").toLocalDateTime());
                fb.setStudentName(rs.getString("student_name"));
                fb.setCourseName(rs.getString("course_name"));
                list.add(fb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
