package com.student.management.dao;

import com.student.management.db.DBConnection;
import com.student.management.models.Attendance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    public void markAttendance(Attendance a) {
        String sql = "INSERT INTO attendance(student_id, course_id, date, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, a.getStudentId());
            stmt.setInt(2, a.getCourseId());
            stmt.setDate(3, a.getDate());
            stmt.setString(4, a.getStatus());
            stmt.executeUpdate();
            System.out.println("Attendance marked.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Attendance> getAttendanceByStudent(int studentId) {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT * FROM attendance WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Attendance a = new Attendance(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getInt("course_id"),
                        rs.getDate("date"),
                        rs.getString("status")
                );
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void printAttendanceSummary(int studentId) {
        String sql = "SELECT status, COUNT(*) as count FROM attendance WHERE student_id = ? GROUP BY status";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Attendance Summary:");
            while (rs.next()) {
                String status = rs.getString("status");
                int count = rs.getInt("count");
                System.out.println(status + ": " + count + " days");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
