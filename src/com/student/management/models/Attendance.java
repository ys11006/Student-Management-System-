package com.student.management.models;

import java.sql.Date;

public class Attendance {
    private int id;
    private int studentId;
    private int courseId;
    private Date date;
    private String status;

    public Attendance() {}

    public Attendance(int id, int studentId, int courseId, Date date, String status) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.date = date;
        this.status = status;
    }

    // Getters and Setters
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }

    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getCourseId() { return courseId; }

    public void setCourseId(int courseId) { this.courseId = courseId; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }
}
