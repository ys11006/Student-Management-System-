package com.student.management.ui;

import com.student.management.dao.CourseDAO;
import com.student.management.models.Course;
import com.student.management.models.Result;

// Add at the top
import com.student.management.dao.StudentDAO;
import com.student.management.dao.EnrollmentDAO;
import com.student.management.dao.ResultDAO;
import com.student.management.dao.AttendanceDAO;
import com.student.management.dao.FeedbackDAO;

import com.student.management.models.Student;
import com.student.management.models.Feedback;
import com.student.management.models.Attendance;
import com.student.management.dao.UserDAO;
import com.student.management.models.User;
import com.student.management.util.PasswordUtil;
import com.student.management.util.ReportGenerator;


import java.sql.Date;


import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CourseDAO dao = new CourseDAO();
        Scanner scanner = new Scanner(System.in);

        UserDAO userDAO = new UserDAO();
        User loggedInUser = null;

        while (true) {
            System.out.println("\n--- Welcome to Student Management System ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Username: ");
                String uname = scanner.nextLine();
                System.out.print("Password: ");
                String pwd = scanner.nextLine();
                System.out.print("Role (admin/teacher): ");
                String role = scanner.nextLine();

                String hashedPwd = PasswordUtil.hashPassword(pwd);
                userDAO.registerUser(new User(0, uname, hashedPwd, role));
            } else if (choice == 2) {
                System.out.print("Username: ");
                String uname = scanner.nextLine();
                System.out.print("Password: ");
                String pwd = scanner.nextLine();

                String hashedPwd = PasswordUtil.hashPassword(pwd);
                loggedInUser = userDAO.login(uname, hashedPwd);
                if (loggedInUser != null) {
                    System.out.println("Login successful. Welcome, " + loggedInUser.getUsername());
                    break;
                } else {
                    System.out.println("Invalid credentials.");
                }
            } else {
                System.out.println("Invalid option.");
            }
        }


        while (true) {
            System.out.println("\n--- Course Management ---");

            System.out.println("1. Add Course");
            System.out.println("2. View All Courses");
            System.out.println("3. Update Course");
            System.out.println("4. Delete Course");
            System.out.println("5. Student Management");
            System.out.println("6. Enrollment Management");
            System.out.println("7. Result Management");
            System.out.println("8. Attendance Management");
            System.out.println("9. Feedback");
            System.out.println("10. Generate Reports");






            System.out.println("0. Exit");
            System.out.print("Enter option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Course Name: ");
                    scanner.nextLine(); // consume newline
                    String name = scanner.nextLine();
                    System.out.print("Duration: ");
                    String duration = scanner.nextLine();
                    System.out.print("Fees: ");
                    double fees = scanner.nextDouble();
                    dao.addCourse(new Course(0, name, duration, fees));
                }
                case 2 -> {
                    List<Course> list = dao.getAllCourses();
                    list.forEach(System.out::println);
                }
                case 3 -> {
                    System.out.print("Enter Course ID to update: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("New Name: ");
                    String name = scanner.nextLine();
                    System.out.print("New Duration: ");
                    String duration = scanner.nextLine();
                    System.out.print("New Fees: ");
                    double fees = scanner.nextDouble();
                    dao.updateCourse(new Course(id, name, duration, fees));
                }
                case 4 -> {
                    System.out.print("Enter Course ID to delete: ");
                    int id = scanner.nextInt();
                    dao.deleteCourse(id);
                }
                case 5 -> {
                    StudentDAO sdao = new StudentDAO();
                    while (true) {
                        System.out.println("\n--- Student Management ---");
                        System.out.println("1. Add Student");
                        System.out.println("2. View All Students");
                        System.out.println("3. Update Student");
                        System.out.println("4. Delete Student");
                        System.out.println("0. Back");
                        System.out.print("Enter option: ");
                        int studentChoice = scanner.nextInt();

                        switch (studentChoice) {
                            case 1 -> {
                                scanner.nextLine();
                                System.out.print("Name: ");
                                String name = scanner.nextLine();
                                System.out.print("Email: ");
                                String email = scanner.nextLine();
                                System.out.print("Date of Birth (yyyy-mm-dd): ");
                                String dob = scanner.nextLine();
                                sdao.addStudent(new Student(0, name, email, Date.valueOf(dob)));
                            }
                            case 2 -> {
                                List<Student> list = sdao.getAllStudents();
                                list.forEach(System.out::println);
                            }
                            case 3 -> {
                                System.out.print("Enter Student ID to update: ");
                                int id = scanner.nextInt();
                                scanner.nextLine();
                                System.out.print("New Name: ");
                                String name = scanner.nextLine();
                                System.out.print("New Email: ");
                                String email = scanner.nextLine();
                                System.out.print("New DOB (yyyy-mm-dd): ");
                                String dob = scanner.nextLine();
                                sdao.updateStudent(new Student(id, name, email, Date.valueOf(dob)));
                            }
                            case 4 -> {
                                System.out.print("Enter Student ID to delete: ");
                                int id = scanner.nextInt();
                                sdao.deleteStudent(id);
                            }
                            case 0 -> {
                                break;
                            }
                            default -> System.out.println("Invalid option.");
                        }
                        if (studentChoice == 0) break;
                    }
                }
                case 6 -> {
                    EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
                    CourseDAO courseDAO = new CourseDAO();
                    Scanner sc = new Scanner(System.in);
                    while (true) {
                        System.out.println("\n--- Enrollment Management ---");
                        System.out.println("1. Enroll Student in Course");
                        System.out.println("2. View Student's Courses");
                        System.out.println("3. Remove Enrollment");
                        System.out.println("0. Back");
                        System.out.print("Enter option: ");
                        int choice6 = sc.nextInt();

                        switch (choice6) {
                            case 1 -> {
                                System.out.print("Enter Student ID: ");
                                int studentId = sc.nextInt();
                                System.out.print("Enter Course ID: ");
                                int courseId = sc.nextInt();
                                enrollmentDAO.enrollStudentInCourse(studentId, courseId);
                            }
                            case 2 -> {
                                System.out.print("Enter Student ID: ");
                                int studentId = sc.nextInt();
                                List<Course> list = enrollmentDAO.getCoursesByStudentId(studentId);
                                list.forEach(System.out::println);
                            }
                            case 3 -> {
                                System.out.print("Enter Student ID: ");
                                int studentId = sc.nextInt();
                                System.out.print("Enter Course ID: ");
                                int courseId = sc.nextInt();
                                enrollmentDAO.removeEnrollment(studentId, courseId);
                            }
                            case 0 -> {
                                break;
                            }
                            default -> System.out.println("Invalid option.");
                        }
                        if (choice6 == 0) break;
                    }
                }
                case 7 -> {
                    ResultDAO resultDAO = new ResultDAO();
                    while (true) {
                        System.out.println("\n--- Result Management ---");
                        System.out.println("1. Add/Update Marks");
                        System.out.println("2. View Student Results");
                        System.out.println("0. Back");
                        System.out.print("Enter option: ");
                        int resChoice = scanner.nextInt();

                        switch (resChoice) {
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
                            case 0 -> {
                                break;
                            }
                            default -> System.out.println("Invalid choice.");
                        }
                        if (resChoice == 0) break;
                    }
                }
                case 8 -> {
                    AttendanceDAO attendanceDAO = new AttendanceDAO();
                    while (true) {
                        System.out.println("\n--- Attendance Management ---");
                        System.out.println("1. Mark Attendance");
                        System.out.println("2. View Attendance Summary");
                        System.out.println("0. Back");
                        System.out.print("Enter option: ");
                        int choice8 = scanner.nextInt();

                        switch (choice8) {
                            case 1 -> {
                                System.out.print("Student ID: ");
                                int sid = scanner.nextInt();
                                System.out.print("Course ID: ");
                                int cid = scanner.nextInt();
                                System.out.print("Date (yyyy-mm-dd): ");
                                scanner.nextLine(); // consume newline
                                String date = scanner.nextLine();
                                System.out.print("Status (Present/Absent): ");
                                String status = scanner.nextLine();
                                Attendance a = new Attendance(0, sid, cid, Date.valueOf(date), status);
                                attendanceDAO.markAttendance(a);
                            }
                            case 2 -> {
                                System.out.print("Student ID: ");
                                int sid = scanner.nextInt();
                                attendanceDAO.printAttendanceSummary(sid);
                            }
                            case 0 -> {
                                break;
                            }
                            default -> System.out.println("Invalid option.");
                        }

                        if (choice8 == 0) break;
                    }
                }
                case 9 -> {
                    FeedbackDAO feedbackDAO = new FeedbackDAO();
                    while (true) {
                        System.out.println("\n--- Feedback Module ---");
                        System.out.println("1. Submit Feedback");
                        System.out.println("2. View All Feedback (admin only)");
                        System.out.println("0. Back");
                        System.out.print("Enter option: ");
                        int choice9 = scanner.nextInt();
                        scanner.nextLine();

                        switch (choice9) {
                            case 1 -> {
                                System.out.print("Enter Student ID: ");
                                int studentId = scanner.nextInt();
                                System.out.print("Enter Course ID: ");
                                int courseId = scanner.nextInt();
                                scanner.nextLine(); // consume newline
                                System.out.print("Enter Feedback Text: ");
                                String text = scanner.nextLine();
                                Feedback fb = new Feedback(0, studentId, courseId, text, new Date(System.currentTimeMillis()));
                                feedbackDAO.submitFeedback(fb);
                            }
                            case 2 -> {
                                if (!loggedInUser.getRole().equals("admin")) {
                                    System.out.println("Access Denied: Only admins can view feedback.");
                                } else {
                                    List<Feedback> feedbacks = feedbackDAO.getAllFeedback();
                                    feedbacks.forEach(System.out::println);
                                }
                            }
                            case 0 -> {
                                break;
                            }
                            default -> System.out.println("Invalid option.");
                        }

                        if (choice9 == 0) break;
                    }
                }
                case 10 -> {
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
                            case 0 -> { break; }
                            default -> System.out.println("Invalid option.");
                        }

                        if (ch == 0) break;
                    }
                }

                case 0 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}
