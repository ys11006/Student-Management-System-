package com.student.management.dao;

import com.student.management.db.DBConnection;
import com.student.management.models.Result;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResultDAO {

    public void addOrUpdateMarks(Result result) {
        String check = "SELECT * FROM results WHERE student_id = ? AND course_id = ?";
        String insert = "INSERT INTO results(student_id, course_id, marks) VALUES (?, ?, ?)";
        String update = "UPDATE results SET marks = ? WHERE student_id = ? AND course_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(check)) {
            checkStmt.setInt(1, result.getStudentId());
            checkStmt.setInt(2, result.getCourseId());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                try (PreparedStatement updateStmt = conn.prepareStatement(update)) {
                    updateStmt.setInt(1, result.getMarks());
                    updateStmt.setInt(2, result.getStudentId());
                    updateStmt.setInt(3, result.getCourseId());
                    updateStmt.executeUpdate();
                    System.out.println("Marks updated successfully.");
                }
            } else {
                try (PreparedStatement insertStmt = conn.prepareStatement(insert)) {
                    insertStmt.setInt(1, result.getStudentId());
                    insertStmt.setInt(2, result.getCourseId());
                    insertStmt.setInt(3, result.getMarks());
                    insertStmt.executeUpdate();
                    System.out.println("Marks added successfully.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Result> getResultsByStudentId(int studentId) {
        List<Result> list = new ArrayList<>();
        String sql = "SELECT * FROM results WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Result result = new Result(
                        rs.getInt("student_id"),
                        rs.getInt("course_id"),
                        rs.getInt("marks")
                );
                list.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void printResultSummary(int studentId) {
        List<Result> results = getResultsByStudentId(studentId);
        int total = 0;
        for (Result r : results) {
            total += r.getMarks();
            System.out.println("Course ID: " + r.getCourseId() + " | Marks: " + r.getMarks());
        }

        if (!results.isEmpty()) {
            double avg = total / (double) results.size();
            String grade = getGrade(avg);
            System.out.println("Total: " + total);
            System.out.printf("Average: %.2f%n", avg);
            System.out.println("Grade: " + grade);
        } else {
            System.out.println("No results found for this student.");
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
