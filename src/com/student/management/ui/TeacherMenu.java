package com.student.management.ui;

import com.student.management.dao.*;
import com.student.management.models.*;
import com.student.management.util.ReportGenerator;
import  com.student.management.models.Result;


import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class TeacherMenu {
    public static void show(Scanner scanner) {
        ResultDAO resultDAO = new ResultDAO();
        AttendanceDAO attendanceDAO = new AttendanceDAO();

        while (true) {
            System.out.println("\n--- Teacher Dashboard ---");
            System.out.println("1. Result Management");
            System.out.println("2. Attendance Management");
            System.out.println("3. Generate Reports");
            System.out.println("0. Logout");
            System.out.print("Enter option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1 -> {
                    while (true) {
                        System.out.println("\n--- Result Management ---");
                        System.out.println("1. Add/Update Result");
                        System.out.println("2. View Student Results");
                        System.out.println("0. Back");
                        System.out.print("Enter option: ");
                        int r = scanner.nextInt();

                        switch (r) {
                            case 1 -> {
                                System.out.print("Student ID: ");
                                int sid = scanner.nextInt();
                                System.out.print("Course ID: ");
                                int cid = scanner.nextInt();
                                System.out.print("Marks: ");
                                int marks = scanner.nextInt();
                                resultDAO.addOrUpdateMarks(new Result(sid, cid, marks));
                            }
                            case 2 -> {
                                System.out.print("Student ID: ");
                                int sid = scanner.nextInt();
                                resultDAO.printResultSummary(sid);
                            }
                            case 0 -> {}
                            default -> System.out.println("Invalid choice.");
                        }
                        if (r == 0) break;
                    }
                }

                case 2 -> {
                    while (true) {
                        System.out.println("\n--- Attendance Management ---");
                        System.out.println("1. Mark Attendance");
                        System.out.println("2. View Attendance Summary");
                        System.out.println("0. Back");
                        System.out.print("Enter option: ");
                        int a = scanner.nextInt();
                        scanner.nextLine();

                        switch (a) {
                            case 1 -> {
                                System.out.print("Student ID: ");
                                int sid = scanner.nextInt();
                                System.out.print("Course ID: ");
                                int cid = scanner.nextInt();
                                System.out.print("Date (yyyy-mm-dd): ");
                                scanner.nextLine();
                                String date = scanner.nextLine();
                                System.out.print("Status (Present/Absent): ");
                                String status = scanner.nextLine();
                                Attendance att = new Attendance(0, sid, cid, Date.valueOf(date), status);
                                attendanceDAO.markAttendance(att);
                            }
                            case 2 -> {
                                System.out.print("Student ID: ");
                                int sid = scanner.nextInt();
                                attendanceDAO.printAttendanceSummary(sid);
                            }
                            case 0 -> {}
                            default -> System.out.println("Invalid option.");
                        }
                        if (a == 0) break;
                    }
                }

                case 3 -> {
                    ReportGenerator rg = new ReportGenerator();
                    while (true) {
                        System.out.println("\n--- Report Generator ---");
                        System.out.println("1. Student Performance");
                        System.out.println("2. Course-wise Result");
                        System.out.println("3. Attendance Summary by Course");
                        System.out.println("4. Top Performers");
                        System.out.println("0. Back");
                        System.out.print("Enter option: ");
                        int ch = scanner.nextInt();

                        switch (ch) {
                            case 1 -> {
                                System.out.print("Enter Student ID: ");
                                int sid = scanner.nextInt();
                                rg.printStudentPerformance(sid);
                            }
                            case 2 -> {
                                System.out.print("Enter Course ID: ");
                                int cid = scanner.nextInt();
                                rg.printCourseResults(cid);
                            }
                            case 3 -> {
                                System.out.print("Enter Course ID: ");
                                int cid = scanner.nextInt();
                                rg.printAttendanceSummaryByCourse(cid);
                            }
                            case 4 -> rg.printTopPerformers();
                            case 0 -> {}
                            default -> System.out.println("Invalid option.");
                        }
                        if (ch == 0) break;
                    }
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
