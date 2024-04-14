package dao;

import entity.Rooms;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    // Create the Rooms table if it doesn't exist
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Rooms (" +
                "RoomID SERIAL PRIMARY KEY, " +
                "Condition VARCHAR(255) NOT NULL, " +
                "RoomSize INT NOT NULL, " +
                "RoomType VARCHAR(255) NOT NULL)";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isRoomAvailable(int roomId, LocalDate bookingDate, LocalTime startTime, LocalTime endTime) {
        String sql = "SELECT COUNT(*) AS booking_count FROM Booking " +
                "WHERE RoomID = ? AND BookingDate = ? " +
                "AND NOT (EndTime <= ? OR StartTime >= ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, roomId);
            pstmt.setDate(2, Date.valueOf(bookingDate));
            pstmt.setTime(3, Time.valueOf(startTime));
            pstmt.setTime(4, Time.valueOf(endTime));

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // If there are no bookings for the room at the given time, it is available
                return rs.getInt("booking_count") == 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Add a new room
    public boolean addRoom(Rooms room) {
        String sql = "INSERT INTO Rooms (Condition, RoomSize, RoomType) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, room.getCondition());
            pstmt.setInt(2, room.getRoomSize());
            pstmt.setString(3, room.getRoomType());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteRoom(int roomId) {
        String sql = "DELETE FROM Rooms WHERE RoomID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, roomId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    // Update an existing room's information
    public boolean updateRoom(Rooms room) {
        String sql = "UPDATE Rooms SET Condition = ?, RoomSize = ?, RoomType = ? WHERE RoomID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, room.getCondition());
            pstmt.setInt(2, room.getRoomSize());
            pstmt.setString(3, room.getRoomType());
            pstmt.setInt(4, room.getRoomId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Fetch a single room by RoomID
    public Rooms getRoomById(int roomId) {
        String sql = "SELECT * FROM Rooms WHERE RoomID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, roomId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Rooms(
                        rs.getInt("RoomID"),
                        rs.getString("Condition"),
                        rs.getInt("RoomSize"),
                        rs.getString("RoomType")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // List all rooms
    public List<Rooms> getAllRooms() {
        List<Rooms> rooms = new ArrayList<>();
        String sql = "SELECT * FROM Rooms";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                rooms.add(new Rooms(
                        rs.getInt("RoomID"),
                        rs.getString("Condition"),
                        rs.getInt("RoomSize"),
                        rs.getString("RoomType")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }


}
