package dao;

import entity.Booking;
import util.DBConnection;
import java.sql.*;


public class BookingDAO {

    // Constructor
    public BookingDAO() {

    }

    // Create the Booking table if it doesn't exist
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Booking (" +
                "BookingID SERIAL PRIMARY KEY, " +
                "BookingDate DATE NOT NULL, " +
                "StartTime TIME NOT NULL, " +
                "EndTime TIME NOT NULL, " +
                "Status VARCHAR(255) NOT NULL, " +
                "MemberID INT NOT NULL, " +
                "ClassID INT NOT NULL, " +
                "RoomID INT NOT NULL, " +
                "FOREIGN KEY (MemberID) REFERENCES Member (MemberID), " +
                "FOREIGN KEY (ClassID) REFERENCES Classes (ClassID), " +
                "FOREIGN KEY (RoomID) REFERENCES Rooms (RoomID))";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Cancel a booking
    public boolean cancelBooking(int bookingId) {
        String sql = "UPDATE Booking SET Status = 'Cancelled' WHERE BookingID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookingId); // Use camelCase for variable bookingId

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // Return true if the booking status was successfully updated
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an SQL error during the update
        }
    }



}
