package com.student.management.ui;

import com.student.management.dao.CourseDAO;
import com.student.management.dao.ResultDAO;
import com.student.management.dao.StudentDAO;
import com.student.management.models.Course;
import com.student.management.models.Result;
import com.student.management.models.Student;

import java.util.List;
import java.util.Scanner;

public class ResultMenu {

    public static void manageResults() {
        Scanner sc = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n--- Result Management ---");
            System.out.println("1. Add Result");
            System.out.println("2. View All Results");
            System.out.println("3. Update Result");
            System.out.println("4. Delete Result");
            System.out.println("0. Back");
            System.out.print("Enter option: ");
            option = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (option) {
                case 1 -> addResult(sc);
                case 2 -> viewAllResults();
                case 3 -> updateResult(sc);
                case 4 -> deleteResult(sc);
                case 0 -> System.out.println("⬅️ Returning...");
                default -> System.out.println("❌ Invalid option.");
            }
        } while (option != 0);
    }

    private static void addResult(Scanner sc) {
        StudentDAO studentDAO = new StudentDAO();
        CourseDAO courseDAO = new CourseDAO();
        ResultDAO resultDAO = new ResultDAO();

        System.out.print("Enter Student ID: ");
        int studentId = sc.nextInt();

        System.out.print("Enter Course ID: ");
        int courseId = sc.nextInt();

        System.out.print("Enter Marks: ");
        double marks = sc.nextDouble();

        String grade = (marks >= 90) ? "A+" :
                (marks >= 75) ? "A" :
                        (marks >= 60) ? "B" :
                                (marks >= 50) ? "C" : "F";

        Result result = new Result(0, studentId, courseId, marks, grade);
        resultDAO.addOrUpdateMarks(result);
    }

    private static void viewAllResults() {
        ResultDAO resultDAO = new ResultDAO();
        List<Result> results = resultDAO.getAllResults();

        if (results.isEmpty()) {
            System.out.println("ℹ️ No results found.");
            return;
        }

        System.out.println("\n--- All Results ---");
        for (Result r : results) {
            System.out.printf("Student: %s | Course: %s | Marks: %.2f | Grade: %s%n",
                    r.getStudent().getName(),
                    r.getCourse().getName(),
                    r.getMarks(),
                    r.getGrade());
        }
    }

    private static void updateResult(Scanner sc) {
        ResultDAO resultDAO = new ResultDAO();

        System.out.print("Enter Result ID to update: ");
        int id = sc.nextInt();

        System.out.print("Enter new marks: ");
        double marks = sc.nextDouble();

        String grade = (marks >= 90) ? "A+" :
                (marks >= 75) ? "A" :
                        (marks >= 60) ? "B" :
                                (marks >= 50) ? "C" : "F";

        resultDAO.updateResult(id, marks, grade);
    }

    private static void deleteResult(Scanner sc) {
        ResultDAO resultDAO = new ResultDAO();

        System.out.print("Enter Result ID to delete: ");
        int id = sc.nextInt();

        resultDAO.deleteResult(id);
    }
}
