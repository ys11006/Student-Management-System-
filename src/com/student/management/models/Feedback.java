package com.student.management.models;

import java.sql.Date;
import java.time.LocalDateTime;

public class Feedback {
    private int id;
    private int studentId;
    private int courseId;
    private String feedbackText;
    private LocalDateTime submittedOn;

    // Extra fields for admin display
    private String studentName;
    private String courseName;

    public Feedback() {}

    // Full constructor
    public Feedback(int id, int studentId, int courseId, String feedbackText, LocalDateTime submittedOn) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.feedbackText = feedbackText;
        this.submittedOn = submittedOn;
    }

    // Constructor for student submission
    public Feedback(int studentId, int courseId, String feedbackText) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.feedbackText = feedbackText;
        this.submittedOn = LocalDateTime.now();
    }

    public Feedback(int id, int studentId, int courseId, String text, Date date) {
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public String getFeedbackText() { return feedbackText; }
    public void setFeedbackText(String feedbackText) { this.feedbackText = feedbackText; }

    public LocalDateTime getSubmittedOn() { return submittedOn; }
    public void setSubmittedOn(LocalDateTime submittedOn) { this.submittedOn = submittedOn; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", feedbackText='" + feedbackText + '\'' +
                ", submittedOn=" + submittedOn +
                ", studentName='" + studentName + '\'' +
                ", courseName='" + courseName + '\'' +
                '}';
    }
}
