package dao;



import entity.MembershipSubscription;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MembershipSubscriptionDAO {

    // Method to create the MembershipSubscriptions table
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS MembershipSubscriptions (" +
                "MembershipSubscriptionID SERIAL PRIMARY KEY, " +
                "MembershipID INT NOT NULL, " +
                "StartDate DATE NOT NULL, " +
                "EndDate DATE NOT NULL, " +
                "FOREIGN KEY (MembershipID) REFERENCES Membership(MembershipID))";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to add a new membership subscription
// Method to add a new membership subscription
    public boolean addMembershipSubscription(MembershipSubscription subscription) {
        String sql = "INSERT INTO MembershipSubscriptions (MembershipID, StartDate, EndDate) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, subscription.getMembershipId());
            pstmt.setDate(2, java.sql.Date.valueOf(subscription.getStartDate()));
            pstmt.setDate(3, java.sql.Date.valueOf(subscription.getEndDate()));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    subscription.setMembershipSubscriptionId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating membership subscription failed, no ID obtained.");
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Method to retrieve a list of subscriptions for a specific member
    public List<MembershipSubscription> getSubscriptionsByMemberId(int memberId) {
        List<MembershipSubscription> subscriptions = new ArrayList<>();
        // SQL query that joins the Member table with the MembershipSubscriptions table
        String sql = "SELECT ms.* FROM MembershipSubscriptions ms " +
                "JOIN Member m ON ms.MembershipSubscriptionID = m.MembershipSubscriptionID " +
                "WHERE m.MemberID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, memberId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                MembershipSubscription subscription = new MembershipSubscription(
                        rs.getInt("MembershipID"),
                        rs.getDate("StartDate").toLocalDate(),
                        rs.getDate("EndDate").toLocalDate()
                );
                subscription.setMembershipSubscriptionId(rs.getInt("MembershipSubscriptionID"));
                subscriptions.add(subscription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptions;
    }


}
