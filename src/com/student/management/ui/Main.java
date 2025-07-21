package com.student.management.ui;

import com.student.management.dao.*;
import com.student.management.models.*;
import com.student.management.util.PasswordUtil;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        StudentDAO studentDAO = new StudentDAO();

        while (true) {
            System.out.println("\n--- Welcome to Student Management System ---");
            System.out.println("1. Register (Admin/Teacher)");
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
                String role = scanner.nextLine().toLowerCase();

                if (!role.equals("admin") && !role.equals("teacher")) {
                    System.out.println("❌ Only admin and teacher can register.");
                    continue;
                }

                String hashedPwd = PasswordUtil.hashPassword(pwd);
                userDAO.registerUser(new User(0, uname, hashedPwd, role));
            }

            else if (choice == 2) {
                System.out.println("➡️ For Students: Enter your Student ID and DOB (yyyy-mm-dd) as password.");
                System.out.print("Username (or Student ID): ");
                String uname = scanner.nextLine();
                System.out.print("Password (or DOB for student): ");
                String pwd = scanner.nextLine();


                // 🧠 Try student login first (student ID is numeric)
                if (uname.matches("\\d+")) {
                    int studentId = Integer.parseInt(uname);
                    Student student = studentDAO.getStudentById(studentId);

                    if (student != null && student.getDob().toString().equals(pwd)) {
                        System.out.println("✅ Student login successful.");
                        StudentMenu.show(scanner, studentId);
                        return;
                    } else {
                        System.out.println("❌ Invalid student ID or DOB.");
                    }
                }

                // 🧠 Else, try admin/teacher login
                String hashedPwd = PasswordUtil.hashPassword(pwd);
                User loggedInUser = userDAO.login(uname, hashedPwd);

                if (loggedInUser != null) {
                    System.out.println("✅ Login successful. Welcome, " + loggedInUser.getUsername());

                    switch (loggedInUser.getRole()) {
                        case "admin" -> AdminMenu.show(scanner);
                        case "teacher" -> TeacherMenu.show(scanner);
                        default -> System.out.println("❌ Unknown role.");
                    }
                    return;
                } else {
                    System.out.println("❌ Invalid credentials.");
                }
            }

            else {
                System.out.println("❌ Invalid option.");
            }
        }
    }
}
