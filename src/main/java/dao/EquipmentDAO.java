package dao;

import entity.Equipment;
import entity.MaintenanceLog;
import util.DBConnection;

import java.sql.*;

public class EquipmentDAO {

    // Method to create the Equipment table and junction tables
    public void createTable() {
        String equipmentTableSql = "CREATE TABLE IF NOT EXISTS Equipment (" +
                "EquipmentID serial PRIMARY KEY, " +
                "Description varchar(255) NOT NULL, " +
                "Status varchar(255) NOT NULL, " +
                "RoomID int NOT NULL, " +
                "FOREIGN KEY (RoomID) REFERENCES Rooms(RoomID))";



        executeSql(equipmentTableSql);

    }

    private void executeSql(String sql) {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to add new equipment
    public boolean addEquipment(Equipment equipment) {
        String sql = "INSERT INTO Equipment (Description, Status, RoomID) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, equipment.getDescription());
            pstmt.setString(2, equipment.getStatus());
            pstmt.setInt(3, equipment.getRoomID());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to update existing equipment
    public boolean updateEquipment(Equipment equipment) {
        String sql = "UPDATE Equipment SET Description = ?, Status = ?, RoomID = ? WHERE EquipmentID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, equipment.getDescription());
            pstmt.setString(2, equipment.getStatus());
            pstmt.setInt(3, equipment.getRoomID());
            pstmt.setInt(4, equipment.getEquipmentID());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to log maintenance for equipment
    public boolean addMaintenanceLog(MaintenanceLog maintenanceLog) {
        String sql = "INSERT INTO MaintenanceLog (EquipmentID, MaintenanceDate, Description, PurchaseDate) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            // Assuming maintenanceLog.getMaintenanceDate() and maintenanceLog.getPurchaseDate() return LocalDate
            java.sql.Date maintenanceSqlDate = java.sql.Date.valueOf(maintenanceLog.getMaintenanceDate());
            java.sql.Date purchaseSqlDate = java.sql.Date.valueOf(maintenanceLog.getPurchaseDate());



            pstmt.setDate(4, purchaseSqlDate);

            pstmt.setInt(1, maintenanceLog.getEquipmentID());
            pstmt.setDate(2, maintenanceSqlDate);
            pstmt.setString(3, maintenanceLog.getDescription());
            pstmt.setDate(4, purchaseSqlDate);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Additional methods for deleting equipment, finding equipment by ID, listing all equipment, etc. can be added as needed
}
