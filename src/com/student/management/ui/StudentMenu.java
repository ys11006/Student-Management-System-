package com.student.management.ui;

import com.student.management.dao.AttendanceDAO;
import com.student.management.dao.FeedbackDAO;
import com.student.management.dao.ResultDAO;
import com.student.management.models.Attendance;
import com.student.management.models.Feedback;
import com.student.management.models.Result;
import java.time.LocalDateTime;


import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class StudentMenu {
    public static void show(Scanner scanner, int studentId) {
        ResultDAO resultDAO = new ResultDAO();
        AttendanceDAO attendanceDAO = new AttendanceDAO();
        FeedbackDAO feedbackDAO = new FeedbackDAO();

        while (true) {
            System.out.println("\n--- Student Dashboard ---");
            System.out.println("1. View Marks");
            System.out.println("2. View Attendance");
            System.out.println("3. Submit Feedback");
            System.out.println("0. Logout");
            System.out.print("Enter option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    List<Result> results = resultDAO.getResultsByStudentId(studentId);
                    System.out.println("\n--- Your Results ---");
                    results.forEach(System.out::println);
                }
                case 2 -> {
                    List<Attendance> list = attendanceDAO.getAttendanceByStudent(studentId);
                    System.out.println("\n--- Your Attendance Records ---");
                    list.forEach(System.out::println);
                    attendanceDAO.printAttendanceSummary(studentId);
                }
                case 3 -> {
                    System.out.print("Enter Course ID: ");
                    int courseId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Feedback Text: ");
                    String text = scanner.nextLine();
                    Feedback fb = new Feedback(0, studentId, courseId, text, LocalDateTime.now());

                    feedbackDAO.submitFeedback(fb);
                }
                case 0 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}
