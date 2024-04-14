package entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class TrainerAvailability {
    private int availabilityID;
    private int trainerID;
    private LocalDate availableDate;
    private LocalTime startTime;
    private LocalTime endTime;

    // Default Constructor
    public TrainerAvailability() {
    }

    // Constructor with all fields
    public TrainerAvailability(int availabilityID, int trainerID, LocalDate availableDate, LocalTime startTime, LocalTime endTime) {
        this.availabilityID = availabilityID;
        this.trainerID = trainerID;
        this.availableDate = availableDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
    public int getAvailabilityID() {
        return availabilityID;
    }

    public void setAvailabilityID(int availabilityID) {
        this.availabilityID = availabilityID;
    }

    public int getTrainerID() {
        return trainerID;
    }

    public void setTrainerID(int trainerID) {
        this.trainerID = trainerID;
    }

    public LocalDate getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(LocalDate availableDate) {
        this.availableDate = availableDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "TrainerAvailability{" +
                "availabilityID=" + availabilityID +
                ", trainerID=" + trainerID +
                ", availableDate=" + availableDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
