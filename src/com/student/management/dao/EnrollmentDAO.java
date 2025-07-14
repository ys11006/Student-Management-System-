package com.student.management.dao;

import com.student.management.db.DBConnection;
import com.student.management.models.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {

    // ✅ 1. Enroll student in course (with duplication check)
    public void enrollStudentInCourse(int studentId, int courseId) {
        if (isAlreadyEnrolled(studentId, courseId)) {
            System.out.println("⚠️ Student is already enrolled in this course.");
            return;
        }

        String sql = "INSERT INTO enrollments(student_id, course_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            stmt.executeUpdate();
            System.out.println("✅ Enrollment successful!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ 2. Check if already enrolled
    private boolean isAlreadyEnrolled(int studentId, int courseId) {
        String sql = "SELECT * FROM enrollments WHERE student_id = ? AND course_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // returns true if entry exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ 3. Get all courses by student
    public List<Course> getCoursesByStudentId(int studentId) {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT c.id, c.name, c.duration, c.fees " +
                "FROM courses c JOIN enrollments e ON c.id = e.course_id " +
                "WHERE e.student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Course course = new Course(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("duration"),
                        rs.getDouble("fees")
                );
                list.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ 4. Remove enrollment
    public void removeEnrollment(int studentId, int courseId) {
        String sql = "DELETE FROM enrollments WHERE student_id = ? AND course_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            stmt.executeUpdate();
            System.out.println("❌ Enrollment removed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
