package com.student.management.ui;

import com.student.management.dao.*;
import com.student.management.models.*;
import com.student.management.util.ReportGenerator;


import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    public static void show(Scanner scanner) {
        CourseDAO courseDAO = new CourseDAO();
        StudentDAO studentDAO = new StudentDAO();
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        ReportGenerator reportGenerator = new ReportGenerator();

        while (true) {
            System.out.println("\n--- Admin Dashboard ---");
            System.out.println("1. Course Management");
            System.out.println("2. Student Management");
            System.out.println("3. Enrollment Management");
            System.out.println("4. View Feedback");
            System.out.println("5. Generate Reports");
            System.out.println("0. Logout");
            System.out.print("Enter option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1 -> {
                    while (true) {
                        System.out.println("\n--- Course Management ---");
                        System.out.println("1. Add Course");
                        System.out.println("2. View Courses");
                        System.out.println("3. Update Course");
                        System.out.println("4. Delete Course");
                        System.out.println("0. Back");
                        System.out.print("Enter option: ");
                        int c = scanner.nextInt();
                        scanner.nextLine();

                        switch (c) {
                            case 1 -> {
                                System.out.print("Course Name: ");
                                String name = scanner.nextLine();
                                System.out.print("Duration: ");
                                String duration = scanner.nextLine();
                                System.out.print("Fees: ");
                                double fees = scanner.nextDouble();
                                courseDAO.addCourse(new Course(0, name, duration, fees));
                            }
                            case 2 -> courseDAO.getAllCourses().forEach(System.out::println);
                            case 3 -> {
                                System.out.print("Course ID to update: ");
                                int id = scanner.nextInt();
                                scanner.nextLine();
                                System.out.print("New Name: ");
                                String name = scanner.nextLine();
                                System.out.print("New Duration: ");
                                String duration = scanner.nextLine();
                                System.out.print("New Fees: ");
                                double fees = scanner.nextDouble();
                                courseDAO.updateCourse(new Course(id, name, duration, fees));
                            }
                            case 4 -> {
                                System.out.print("Course ID to delete: ");
                                int id = scanner.nextInt();
                                courseDAO.deleteCourse(id);
                            }
                            case 0 -> {}
                            default -> System.out.println("Invalid choice.");
                        }
                        if (c == 0) break;
                    }
                }

                case 2 -> {
                    while (true) {
                        System.out.println("\n--- Student Management ---");
                        System.out.println("1. Add Student");
                        System.out.println("2. View Students");
                        System.out.println("3. Update Student");
                        System.out.println("4. Delete Student");
                        System.out.println("0. Back");
                        System.out.print("Enter option: ");
                        int s = scanner.nextInt();
                        scanner.nextLine();

                        switch (s) {
                            case 1 -> {
                                System.out.print("Name: ");
                                String name = scanner.nextLine();
                                System.out.print("Email: ");
                                String email = scanner.nextLine();
                                System.out.print("DOB (yyyy-mm-dd): ");
                                String dob = scanner.nextLine();
                                studentDAO.addStudent(new Student(0, name, email, Date.valueOf(dob)));
                            }
                            case 2 -> studentDAO.getAllStudents().forEach(System.out::println);
                            case 3 -> {
                                System.out.print("Student ID to update: ");
                                int id = scanner.nextInt();
                                scanner.nextLine();
                                System.out.print("New Name: ");
                                String name = scanner.nextLine();
                                System.out.print("New Email: ");
                                String email = scanner.nextLine();
                                System.out.print("New DOB (yyyy-mm-dd): ");
                                String dob = scanner.nextLine();
                                studentDAO.updateStudent(new Student(id, name, email, Date.valueOf(dob)));
                            }
                            case 4 -> {
                                System.out.print("Student ID to delete: ");
                                int id = scanner.nextInt();
                                studentDAO.deleteStudent(id);
                            }
                            case 0 -> {}
                            default -> System.out.println("Invalid choice.");
                        }
                        if (s == 0) break;
                    }
                }

                case 3 -> {
                    while (true) {
                        System.out.println("\n--- Enrollment Management ---");
                        System.out.println("1. Enroll Student in Course");
                        System.out.println("2. View Student's Courses");
                        System.out.println("3. Remove Enrollment");
                        System.out.println("0. Back");
                        System.out.print("Enter option: ");
                        int e = scanner.nextInt();

                        switch (e) {
                            case 1 -> {
                                System.out.print("Student ID: ");
                                int sid = scanner.nextInt();
                                System.out.print("Course ID: ");
                                int cid = scanner.nextInt();
                                enrollmentDAO.enrollStudentInCourse(sid, cid);
                            }
                            case 2 -> {
                                System.out.print("Student ID: ");
                                int sid = scanner.nextInt();
                                enrollmentDAO.getCoursesByStudentId(sid).forEach(System.out::println);
                            }
                            case 3 -> {
                                System.out.print("Student ID: ");
                                int sid = scanner.nextInt();
                                System.out.print("Course ID: ");
                                int cid = scanner.nextInt();
                                enrollmentDAO.removeEnrollment(sid, cid);
                            }
                            case 0 -> {}
                            default -> System.out.println("Invalid choice.");
                        }
                        if (e == 0) break;
                    }
                }

                case 4 -> {
                    System.out.println("\n--- All Feedback ---");
                    List<Feedback> feedbackList = feedbackDAO.getAllFeedback();
                    feedbackList.forEach(System.out::println);
                }

                case 5 -> {
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
                                reportGenerator.printStudentPerformance(sid);
                            }
                            case 2 -> {
                                System.out.print("Enter Course ID: ");
                                int cid = scanner.nextInt();
                                reportGenerator.printCourseResults(cid);
                            }
                            case 3 -> {
                                System.out.print("Enter Course ID: ");
                                int cid = scanner.nextInt();
                                reportGenerator.printAttendanceSummaryByCourse(cid);
                            }
                            case 4 -> reportGenerator.printTopPerformers();
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
