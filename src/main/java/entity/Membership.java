package entity;

import java.time.LocalDate;

public class Membership {
    private int membershipId;  // Primary key, auto-generated
    private int duration;      // Duration in months
    private double rate;       // Cost of membership

    private String description;
    // Constructor without ID for creating new instances
    public Membership(int duration, double rate, String description) {
        this.duration = duration;
        this.rate = rate;
        this.description = description;
    }

    // Getters and setters
    public int getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // toString method for debugging and logging purposes (optional)
    @Override
    public String toString() {
        return "Membership{" +
                "membershipId=" + membershipId +
                ", membershipDuration=" + duration +
                ", membershipRate=" + rate +
                ", description=" + description +
                '}';
    }
}
