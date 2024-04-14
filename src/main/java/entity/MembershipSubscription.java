package entity;

import java.time.LocalDate;

public class MembershipSubscription {
    private int membershipSubscriptionId;  // Primary key
    private int membershipId;              // Foreign key to Membership
    private LocalDate startDate;
    private LocalDate endDate;

    // Constructor without memberId
    public MembershipSubscription(int membershipId, LocalDate startDate, LocalDate endDate) {
        this.membershipId = membershipId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Setters and getters
    public int getMembershipSubscriptionId() {
        return membershipSubscriptionId;
    }

    public void setMembershipSubscriptionId(int membershipSubscriptionId) {
        this.membershipSubscriptionId = membershipSubscriptionId;
    }

    public int getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    // Optionally override the toString method for debugging and logging purposes
    @Override
    public String toString() {
        return "MembershipSubscription{" +
                "membershipSubscriptionId=" + membershipSubscriptionId +
                ", membershipId=" + membershipId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
