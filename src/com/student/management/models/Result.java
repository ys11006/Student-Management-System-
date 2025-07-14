package com.student.management.models;

public class Result {
    private int studentId;
    private int courseId;
    private int marks;

    public Result() {}

    public Result(int studentId, int courseId, int marks) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.marks = marks;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Result{" +
                "studentId=" + studentId +
                ", courseId=" + courseId +
                ", marks=" + marks +
                '}';
    }
}
