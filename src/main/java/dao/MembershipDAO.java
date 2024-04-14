package dao;

import entity.Membership;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class MembershipDAO {


    // Method to create the Membership table
    public void createTable() {
        String membershipTableSql = "CREATE TABLE IF NOT EXISTS Membership (" +
                "MembershipID SERIAL PRIMARY KEY, " +
                "MembershipDuration INT NOT NULL, " +
                "MembershipRate DECIMAL(10, 2) NOT NULL, " +
                "Description TEXT NOT NULL)";
        executeSql(membershipTableSql);

    }

    private void executeSql(String sql) {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // List all available memberships
    public static List<Membership> listMemberships() {
        List<Membership> memberships = new ArrayList<>();
        String sql = "SELECT * FROM Membership";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                memberships.add(new Membership(
                        rs.getInt("MembershipDuration"),
                        rs.getDouble("MembershipRate"),
                        rs.getString("Description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memberships;
    }

    // Add a new membership plan
// Add a new membership plan
    public boolean addMembership(Membership membership) {
        String sql = "INSERT INTO Membership (MembershipDuration, MembershipRate, Description) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, membership.getDuration());
            pstmt.setDouble(2, membership.getRate());
            pstmt.setString(3, membership.getDescription());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    membership.setMembershipId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating membership failed, no ID obtained.");
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Update an existing membership plan
    public boolean updateMembership(Membership membership) {
        String sql = "UPDATE Membership SET MembershipDuration = ?, MembershipRate = ?, Description = ? WHERE MembershipID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, membership.getDuration());
            pstmt.setDouble(2, membership.getRate());
            pstmt.setString(3, membership.getDescription());
            pstmt.setInt(4, membership.getMembershipId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a membership plan
    public boolean deleteMembership(int membershipId) {
        String sql = "DELETE FROM Membership WHERE membershipId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, membershipId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve a single membership plan by ID
    public static Membership getMembershipById(int membershipId) {
        String sql = "SELECT * FROM Membership WHERE MembershipID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, membershipId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Membership(
                        rs.getInt("MembershipDuration"),
                        rs.getDouble("MembershipRate"),
                        rs.getString("Description")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
