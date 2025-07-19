package com.student.management.models;

public class Result {
    private int id;
    private int studentId;
    private int courseId;
    private double marks;
    private String grade;

    private Student student; // optional joined data
    private Course course;   // optional joined data

    public Result() {}

    public Result(int studentId, int courseId, double marks) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.marks = marks;
    }

    public Result(int id, int studentId, int courseId, double marks, String grade) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.marks = marks;
        this.grade = grade;
    }



    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public int getMarks() { return (int) marks; }
    public void setMarks(double marks) { this.marks = marks; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", marks=" + marks +
                ", grade='" + grade + '\'' +
                '}';
    }
}
