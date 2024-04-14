package entity;

import java.time.LocalDate;

public class Progress {
    private int progressId;
    private int memberId;
    private LocalDate date;
    private double weight;
    private double bodyFatPercentage;
    private String personalRecords;
    private int classAttendance;
    private String notes;

    // Default Constructor
    public Progress() {
    }

    // Constructor with all fields
    public Progress(int progressId, int memberId, LocalDate date, double weight, double bodyFatPercentage, String personalRecords, int classAttendance, String notes) {
        this.progressId = progressId;
        this.memberId = memberId;
        this.date = date;
        this.weight = weight;
        this.bodyFatPercentage = bodyFatPercentage;
        this.personalRecords = personalRecords;
        this.classAttendance = classAttendance;
        this.notes = notes;
    }

    // Getters and Setters
    public int getProgressId() {
        return progressId;
    }

    public void setProgressId(int progressId) {
        this.progressId = progressId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getBodyFatPercentage() {
        return bodyFatPercentage;
    }

    public void setBodyFatPercentage(double bodyFatPercentage) {
        this.bodyFatPercentage = bodyFatPercentage;
    }

    public String getPersonalRecords() {
        return personalRecords;
    }

    public void setPersonalRecords(String personalRecords) {
        this.personalRecords = personalRecords;
    }

    public int getClassAttendance() {
        return classAttendance;
    }

    public void setClassAttendance(int classAttendance) {
        this.classAttendance = classAttendance;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // toString method for debugging and logging purposes (optional)
    @Override
    public String toString() {
        return "Progress{" +
                "progressId=" + progressId +
                ", memberId=" + memberId +
                ", date=" + date +
                ", weight=" + weight +
                ", bodyFatPercentage=" + bodyFatPercentage +
                ", personalRecords='" + personalRecords + '\'' +
                ", classAttendance=" + classAttendance +
                ", notes='" + notes + '\'' +
                '}';
    }
}
