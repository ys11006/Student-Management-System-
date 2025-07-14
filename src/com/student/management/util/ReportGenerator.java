package com.student.management.util;

import com.student.management.db.DBConnection;

import java.sql.*;

public class ReportGenerator {

    public void printStudentPerformance(int studentId) {
        String sql = "SELECT course_id, marks FROM results WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            int total = 0, count = 0;
            System.out.println("Student Performance:");
            while (rs.next()) {
                int courseId = rs.getInt("course_id");
                int marks = rs.getInt("marks");
                total += marks;
                count++;
                System.out.println("Course ID: " + courseId + " | Marks: " + marks);
            }

            if (count > 0) {
                double avg = total / (double) count;
                String grade = getGrade(avg);
                System.out.println("Total: " + total);
                System.out.printf("Average: %.2f%n", avg);
                System.out.println("Grade: " + grade);
            } else {
                System.out.println("No marks found for this student.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printCourseResults(int courseId) {
        String sql = "SELECT student_id, marks FROM results WHERE course_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Course Results:");
            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                int marks = rs.getInt("marks");
                System.out.println("Student ID: " + studentId + " | Marks: " + marks);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printAttendanceSummaryByCourse(int courseId) {
        String sql = "SELECT student_id, status, COUNT(*) as days " +
                "FROM attendance WHERE course_id = ? " +
                "GROUP BY student_id, status";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Attendance Summary:");
            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                String status = rs.getString("status");
                int days = rs.getInt("days");
                System.out.println("Student ID: " + studentId + " | " + status + ": " + days);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printTopPerformers() {
        String sql = "SELECT student_id, AVG(marks) as avg_marks FROM results GROUP BY student_id ORDER BY avg_marks DESC LIMIT 5";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Top Performers:");
            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                double avg = rs.getDouble("avg_marks");
                System.out.printf("Student ID: %d | Average: %.2f%n", studentId, avg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getGrade(double avg) {
        if (avg >= 90) return "A+";
        if (avg >= 75) return "A";
        if (avg >= 60) return "B";
        if (avg >= 40) return "C";
        return "F";
    }
}
