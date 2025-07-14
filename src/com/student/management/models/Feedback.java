package com.student.management.models;

import java.sql.Date;

public class Feedback {
    private int id;
    private int studentId;
    private int courseId;
    private String feedbackText;
    private Date submittedOn;

    public Feedback() {}

    public Feedback(int id, int studentId, int courseId, String feedbackText, Date submittedOn) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.feedbackText = feedbackText;
        this.submittedOn = submittedOn;
    }

    public int getId() { return id; }

    public int getStudentId() { return studentId; }

    public int getCourseId() { return courseId; }

    public String getFeedbackText() { return feedbackText; }

    public Date getSubmittedOn() { return submittedOn; }

    public void setId(int id) { this.id = id; }

    public void setStudentId(int studentId) { this.studentId = studentId; }

    public void setCourseId(int courseId) { this.courseId = courseId; }

    public void setFeedbackText(String feedbackText) { this.feedbackText = feedbackText; }

    public void setSubmittedOn(Date submittedOn) { this.submittedOn = submittedOn; }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", feedbackText='" + feedbackText + '\'' +
                ", submittedOn=" + submittedOn +
                '}';
    }
}
