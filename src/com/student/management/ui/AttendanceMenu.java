package com.student.management.ui;

import com.student.management.dao.AttendanceDAO;
import com.student.management.models.Attendance;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class AttendanceMenu {

    private static final AttendanceDAO attendanceDAO = new AttendanceDAO();

    public static void manageAttendance() {
        Scanner sc = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n--- Attendance Management ---");
            System.out.println("1. Mark Attendance");
            System.out.println("2. View Student Attendance");
            System.out.println("3. View Attendance Summary");
            System.out.println("4. Exit to Teacher Menu");
            System.out.print("Enter option: ");
            option = sc.nextInt();

            switch (option) {
                case 1 -> markAttendance(sc);
                case 2 -> viewAttendance(sc);
                case 3 -> viewSummary(sc);
                case 4 -> System.out.println("Returning to Teacher Menu...");
                default -> System.out.println("Invalid option. Try again.");
            }
        } while (option != 4);
    }

    private static void markAttendance(Scanner sc) {
        System.out.print("Enter Student ID: ");
        int studentId = sc.nextInt();
        System.out.print("Enter Course ID: ");
        int courseId = sc.nextInt();
        sc.nextLine(); // consume newline
        System.out.print("Enter Date (yyyy-mm-dd): ");
        String dateStr = sc.nextLine();
        System.out.print("Enter Status (Present/Absent): ");
        String status = sc.nextLine();

        Attendance attendance = new Attendance();
        attendance.setStudentId(studentId);
        attendance.setCourseId(courseId);
        attendance.setDate(Date.valueOf(dateStr));
        attendance.setStatus(status);

        attendanceDAO.markAttendance(attendance);
    }

    private static void viewAttendance(Scanner sc) {
        System.out.print("Enter Student ID: ");
        int studentId = sc.nextInt();
        List<Attendance> list = attendanceDAO.getAttendanceByStudent(studentId);

        System.out.println("\n--- Attendance Records ---");
        for (Attendance a : list) {
            System.out.printf("ID: %d | Course ID: %d | Date: %s | Status: %s\n",
                    a.getId(), a.getCourseId(), a.getDate(), a.getStatus());
        }

        if (list.isEmpty()) {
            System.out.println("No attendance found for this student.");
        }
    }

    private static void viewSummary(Scanner sc) {
        System.out.print("Enter Student ID: ");
        int studentId = sc.nextInt();
        attendanceDAO.printAttendanceSummary(studentId);
    }
}
