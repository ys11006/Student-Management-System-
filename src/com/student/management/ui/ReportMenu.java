package com.student.management.ui;

import com.student.management.dao.AttendanceDAO;
import com.student.management.dao.ResultDAO;

import java.util.Scanner;

public class ReportMenu {

    private static final Scanner sc = new Scanner(System.in);
    private static final ResultDAO resultDAO = new ResultDAO();
    private static final AttendanceDAO attendanceDAO = new AttendanceDAO();

    public static void showReports() {
        int option;

        do {
            System.out.println("\n--- Report Generation ---");
            System.out.println("1. View Student Report (Marks & Attendance)");
            System.out.println("2. View Course-wise Average Marks");
            System.out.println("3. View Attendance by Course");
            System.out.println("0. Back");
            System.out.print("Enter option: ");
            option = sc.nextInt();

            switch (option) {
                case 1 -> viewStudentReport();
                case 2 -> viewCourseAverageMarks();
                case 3 -> viewAttendanceByCourse();
                case 0 -> System.out.println("Returning...");
                default -> System.out.println("Invalid option. Try again.");
            }
        } while (option != 0);
    }

    private static void viewStudentReport() {
        System.out.print("Enter Student ID: ");
        int studentId = sc.nextInt();
        System.out.println("\n--- Marks ---");
        resultDAO.getResultsByStudentId(studentId);
        System.out.println("\n--- Attendance Summary ---");
        attendanceDAO.printAttendanceSummary(studentId);
    }

    private static void viewCourseAverageMarks() {
        resultDAO.printCourseWiseAverageMarks();
    }

    private static void viewAttendanceByCourse() {
        System.out.print("Enter Course ID: ");
        int courseId = sc.nextInt();
        attendanceDAO.printAttendanceByCourse(courseId);
    }
}
