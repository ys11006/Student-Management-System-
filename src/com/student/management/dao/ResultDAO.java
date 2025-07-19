package com.student.management.dao;

import com.student.management.db.DBConnection;
import com.student.management.models.Course;
import com.student.management.models.Result;
import com.student.management.models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResultDAO {

    public void addOrUpdateMarks(Result result) {
        String selectQuery = "SELECT * FROM results WHERE student_id = ? AND course_id = ?";
        String insertQuery = "INSERT INTO results (student_id, course_id, marks) VALUES (?, ?, ?)";
        String updateQuery = "UPDATE results SET marks = ? WHERE student_id = ? AND course_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {

            selectStmt.setInt(1, result.getStudentId());
            selectStmt.setInt(2, result.getCourseId());

            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                // Update existing
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setInt(1, result.getMarks());
                    updateStmt.setInt(2, result.getStudentId());
                    updateStmt.setInt(3, result.getCourseId());
                    updateStmt.executeUpdate();
                    System.out.println("✅ Marks updated successfully.");
                }
            } else {
                // Insert new
                try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                    insertStmt.setInt(1, result.getStudentId());
                    insertStmt.setInt(2, result.getCourseId());
                    insertStmt.setInt(3, result.getMarks());
                    insertStmt.executeUpdate();
                    System.out.println("✅ Marks inserted successfully.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printResultSummary(int studentId) {
        String sql = "SELECT course_id, marks FROM results WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Results for Student ID: " + studentId);
            while (rs.next()) {
                int courseId = rs.getInt("course_id");
                double marks = rs.getDouble("marks");
                System.out.println("Course ID: " + courseId + " | Marks: " + marks);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Result> getAllResults() {
        List<Result> list = new ArrayList<>();
        String sql = "SELECT r.*, s.name AS student_name, c.name AS course_name, c.duration, c.fees " +
                "FROM results r " +
                "JOIN students s ON r.student_id = s.id " +
                "JOIN courses c ON r.course_id = c.id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Result result = new Result();
                result.setId(rs.getInt("id"));
                result.setStudentId(rs.getInt("student_id"));
                result.setCourseId(rs.getInt("course_id"));
                result.setMarks(rs.getDouble("marks"));
                result.setGrade(rs.getString("grade"));

                Student student = new Student();
                student.setId(rs.getInt("student_id"));
                student.setName(rs.getString("student_name"));

                Course course = new Course();
                course.setId(rs.getInt("course_id"));
                course.setName(rs.getString("course_name"));
                course.setDuration(rs.getString("duration"));
                course.setFees(rs.getDouble("fees"));

                result.setStudent(student);
                result.setCourse(course);

                list.add(result);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void updateResult(int id, double marks, String grade) {
        String sql = "UPDATE results SET marks = ?, grade = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, marks);
            stmt.setString(2, grade);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            System.out.println("✅ Result updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteResult(int id) {
        String sql = "DELETE FROM results WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("✅ Result deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Result> getResultsByStudentId(int studentId) {
        List<Result> results = new ArrayList<>();
        String sql = "SELECT r.*, c.name AS course_name FROM results r " +
                "JOIN courses c ON r.course_id = c.id WHERE r.student_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Result r = new Result();
                r.setId(rs.getInt("id"));
                r.setStudentId(studentId);
                r.setCourseId(rs.getInt("course_id"));
                r.setMarks(rs.getDouble("marks"));
                r.setGrade(rs.getString("grade"));

                Course course = new Course();
                course.setId(rs.getInt("course_id"));
                course.setName(rs.getString("course_name"));
                r.setCourse(course);

                results.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }


    public void printCourseWiseAverageMarks() {
    }
}
