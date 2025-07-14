package com.student.management.dao;

import com.student.management.db.DBConnection;
import com.student.management.models.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public void addCourse(Course course) {
        String sql = "INSERT INTO courses(name, duration, fees) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getName());
            stmt.setString(2, course.getDuration());
            stmt.setDouble(3, course.getFees());
            stmt.executeUpdate();
            System.out.println("Course added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Course> getAllCourses() {
        List<Course> courseList = new ArrayList<>();
        String sql = "SELECT * FROM courses";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Course course = new Course(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("duration"),
                        rs.getDouble("fees")
                );
                courseList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    public void updateCourse(Course course) {
        String sql = "UPDATE courses SET name = ?, duration = ?, fees = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getName());
            stmt.setString(2, course.getDuration());
            stmt.setDouble(3, course.getFees());
            stmt.setInt(4, course.getId());
            stmt.executeUpdate();
            System.out.println("Course updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCourse(int courseId) {
        // Step-by-step deletion to respect foreign key dependencies
        String[] sqls = {
                "DELETE FROM feedback WHERE course_id = ?",
                "DELETE FROM attendance WHERE course_id = ?",
                "DELETE FROM results WHERE course_id = ?",
                "DELETE FROM enrollments WHERE course_id = ?",
                "DELETE FROM courses WHERE id = ?"
        };

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Begin transaction

            for (String sql : sqls) {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, courseId);
                    stmt.executeUpdate();
                }
            }

            conn.commit(); // Commit if all deletions succeeded
            System.out.println("Course and all related data deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                DBConnection.getConnection().rollback(); // Rollback if something failed
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
