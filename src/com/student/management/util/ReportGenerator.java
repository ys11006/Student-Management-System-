package com.student.management.util;

import com.student.management.db.DBConnection;

import java.sql.*;

public class ReportGenerator {

    public void printStudentPerformance(int studentId) {
        String sql = "SELECT c.name AS course_name, r.marks " +
                "FROM results r JOIN courses c ON r.course_id = c.id " +
                "WHERE r.student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            int total = 0, count = 0;
            System.out.println("\n📊 Student Performance Report:");
            while (rs.next()) {
                String courseName = rs.getString("course_name");
                int marks = rs.getInt("marks");
                total += marks;
                count++;
                System.out.println("📘 Course: " + courseName + " | Marks: " + marks);
            }

            if (count > 0) {
                double avg = total / (double) count;
                String grade = getGrade(avg);
                System.out.println("➕ Total Marks: " + total);
                System.out.printf("📈 Average Marks: %.2f\n", avg);
                System.out.println("🏅 Overall Grade: " + grade);
            } else {
                System.out.println("⚠️ No marks found for this student.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printCourseResults(int courseId) {
        String sql = "SELECT s.name AS student_name, r.marks " +
                "FROM results r JOIN students s ON r.student_id = s.id " +
                "WHERE r.course_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n📋 Course Results:");
            while (rs.next()) {
                String studentName = rs.getString("student_name");
                int marks = rs.getInt("marks");
                System.out.println("👤 Student: " + studentName + " | Marks: " + marks);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printAttendanceSummaryByCourse(int courseId) {
        String sql = "SELECT s.name AS student_name, a.status, COUNT(*) as days " +
                "FROM attendance a JOIN students s ON a.student_id = s.id " +
                "WHERE a.course_id = ? GROUP BY a.student_id, a.status";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n📆 Attendance Summary:");
            while (rs.next()) {
                String studentName = rs.getString("student_name");
                String status = rs.getString("status");
                int days = rs.getInt("days");
                System.out.println("👤 Student: " + studentName + " | " + status + ": " + days + " days");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printTopPerformers() {
        String sql = "SELECT s.name AS student_name, AVG(r.marks) as avg_marks " +
                "FROM results r JOIN students s ON r.student_id = s.id " +
                "GROUP BY r.student_id ORDER BY avg_marks DESC LIMIT 5";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n🏆 Top 5 Performers:");
            int rank = 1;
            while (rs.next()) {
                String name = rs.getString("student_name");
                double avg = rs.getDouble("avg_marks");
                System.out.printf("🥇 Rank %d - %s | Average Marks: %.2f\n", rank++, name, avg);
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
