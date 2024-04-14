package dao;

import entity.Progress;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProgressDAO {

    // Method to create the Progress table if it doesn't exist
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Progress (" +
                "ProgressID SERIAL PRIMARY KEY, " +
                "MemberID INT NOT NULL, " +
                "Date DATE NOT NULL, " +
                "Weight DECIMAL(5, 2), " +
                "BodyFatPercentage DECIMAL(5, 2), " +
                "PersonalRecords TEXT, " +
                "ClassAttendance INT, " +
                "Notes TEXT, " +
                "FOREIGN KEY (MemberID) REFERENCES Member(MemberID))";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    // Method to find all progress records for a specific member
    public List<Progress> findProgressByMemberId(int memberId) {
        List<Progress> progressList = new ArrayList<>();
        String sql = "SELECT * FROM Progress WHERE MemberID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Progress progress = new Progress();
                // Assuming Progress class has setters for each field
                progress.setProgressId(rs.getInt("ProgressID"));
                progress.setMemberId(rs.getInt("MemberID"));
                progress.setDate(rs.getDate("Date").toLocalDate());
                progress.setWeight(rs.getDouble("Weight"));
                progress.setBodyFatPercentage(rs.getDouble("BodyFatPercentage"));
                progress.setPersonalRecords(rs.getString("PersonalRecords"));
                progress.setClassAttendance(rs.getInt("ClassAttendance"));
                progress.setNotes(rs.getString("Notes"));
                // Add more fields based on your Progress entity

                progressList.add(progress);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return progressList;
    }
}
