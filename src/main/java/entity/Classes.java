package entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Classes {
    private int classId;
    private double cost;
    private LocalTime time;
    private LocalDate date;
    private String classType;
    private int classSize;
    private int trainerId;

    // Default constructor
    public Classes() {
    }

    // Constructor with all fields
    public Classes( double cost, LocalTime time, LocalDate date, String classType, int classSize, int trainerId) {

        this.cost = cost;
        this.time = time;
        this.date = date;
        this.classType = classType;
        this.classSize = classSize;
        this.trainerId = trainerId;
    }

    // Getters and Setters
    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public int getClassSize() {
        return classSize;
    }

    public void setClassSize(int classSize) {
        this.classSize = classSize;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    // toString method for debugging and logging purposes (optional)
    @Override
    public String toString() {
        return "Classes{" +
                "classId=" + classId +
                ", cost=" + cost +
                ", time=" + time +
                ", date=" + date +
                ", classType='" + classType + '\'' +
                ", classSize=" + classSize +
                ", trainerId=" + trainerId +
                '}';
    }
}
