package com.student.management.ui;

import com.student.management.dao.*;
import com.student.management.models.*;
import com.student.management.util.PasswordUtil;
import com.student.management.util.ReportGenerator;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        User loggedInUser = null;

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
                    System.out.println("Only admin and teacher can register.");
                    continue;
                }
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

        switch (loggedInUser.getRole()) {
            case "admin" -> AdminMenu.show(scanner);
            case "teacher" -> TeacherMenu.show(scanner);
            case "student" -> StudentMenu.show(scanner, loggedInUser.getId());
            default -> System.out.println("Invalid role.");
        }
    }
}
