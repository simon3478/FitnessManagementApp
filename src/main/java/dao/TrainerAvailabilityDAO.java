package dao;

import entity.TrainerAvailability;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class TrainerAvailabilityDAO {




        // Method to create the TrainerAvailability table if it doesn't exist
        public void createTable() {
            String trainerAvailabilityTableSql = "CREATE TABLE IF NOT EXISTS TrainerAvailability (" +
                    "AvailabilityID serial PRIMARY KEY, " +
                    "TrainerID int NOT NULL, " +
                    "AvailableDate date NOT NULL, " +
                    "StartTime time NOT NULL, " +
                    "EndTime time NOT NULL, " +
                    "FOREIGN KEY (TrainerID) REFERENCES Trainer(TrainerID))";

            executeSql(trainerAvailabilityTableSql);
        }

        private void executeSql(String sql) {
            try (Connection conn = DBConnection.getConnection();
                 Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }




    public boolean addTrainerAvailability(TrainerAvailability availability) {
        String sql = "INSERT INTO TrainerAvailability (trainerID, availableDate, startTime, endTime) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, availability.getTrainerID());
            pstmt.setDate(2, java.sql.Date.valueOf(availability.getAvailableDate()));
            pstmt.setTime(3, java.sql.Time.valueOf(availability.getStartTime()));
            pstmt.setTime(4, java.sql.Time.valueOf(availability.getEndTime()));

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Method to check if any trainer is available for a given class and time slot
    public boolean isTrainerAvailable(int classId, LocalDate bookingDate, LocalTime startTime, LocalTime endTime) {
        // This SQL checks for any trainer availability matching the classId and time slot
        String sql = "SELECT COUNT(*) FROM TrainerAvailability " +
                "JOIN Classes ON TrainerAvailability.TrainerID = Classes.TrainerID " +
                "WHERE Classes.ClassID = ? AND TrainerAvailability.AvailableDate = ? " +
                "AND NOT (TrainerAvailability.EndTime <= ? OR TrainerAvailability.StartTime >= ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, classId);
            pstmt.setDate(2, Date.valueOf(bookingDate));
            pstmt.setTime(3, Time.valueOf(startTime));
            pstmt.setTime(4, Time.valueOf(endTime));

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // If count is 0, no trainer is available for the class at the requested time
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
