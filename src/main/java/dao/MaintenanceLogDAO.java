package dao;

import entity.MaintenanceLog;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceLogDAO {


    // Method to create the MaintenanceLog table and its junction table
    public void createTable() {
        String maintenanceLogTableSql = "CREATE TABLE IF NOT EXISTS MaintenanceLog (" +
                "LogID serial PRIMARY KEY, " +
                "MaintenanceDate date NOT NULL, " +
                "Description varchar(255) NOT NULL, " +
                "PurchaseDate date NOT NULL)";

        String maintenanceLogEquipmentTableSql = "CREATE TABLE IF NOT EXISTS MaintenanceLog_Equipment (" +
                "EquipmentID int NOT NULL, " +
                "LogID int NOT NULL, " +
                "PRIMARY KEY (EquipmentID, LogID), " +
                "FOREIGN KEY (EquipmentID) REFERENCES Equipment(EquipmentID), " +
                "FOREIGN KEY (LogID) REFERENCES MaintenanceLog(LogID) ON DELETE CASCADE)";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(maintenanceLogTableSql);  // Create MaintenanceLog table
            stmt.execute(maintenanceLogEquipmentTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        // Add a new Maintenance Log
        public boolean addMaintenanceLog(MaintenanceLog log) {
            String sql = "INSERT INTO MaintenanceLog (MaintenanceDate, Description, PurchaseDate) VALUES (?, ?, ?)";
            int logId = -1;
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                pstmt.setDate(1, Date.valueOf(log.getMaintenanceDate()));
                pstmt.setString(2, log.getDescription());
                pstmt.setDate(3, Date.valueOf(log.getPurchaseDate()));

                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            logId = generatedKeys.getInt(1);
                            // Now link the log to the equipment
                            if (!linkMaintenanceLogToEquipment(log.getEquipmentID(), logId)) {
                                return false;  // Return false if linking fails
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

    private boolean linkMaintenanceLogToEquipment(int equipmentId, int logId) {
        String linkSql = "INSERT INTO MaintenanceLog_Equipment (EquipmentID, LogID) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(linkSql)) {

            pstmt.setInt(1, equipmentId);
            pstmt.setInt(2, logId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve all Maintenance Logs
    public List<MaintenanceLog> getAllMaintenanceLogs() {
        List<MaintenanceLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM MaintenanceLog";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                MaintenanceLog log = new MaintenanceLog(
                        rs.getInt("equipmentID"),
                        rs.getDate("maintenanceDate").toLocalDate(),
                        rs.getString("description"),
                        rs.getDate("purchaseDate").toLocalDate()
                );
                log.setLogID(rs.getInt("logID")); // Set logID here after object creation
                logs.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }


    // Update an existing Maintenance Log
    public boolean updateMaintenanceLog(MaintenanceLog log) {
        String sql = "UPDATE MaintenanceLog SET equipmentID = ?, maintenanceDate = ?, description = ?, purchaseDate = ? WHERE logID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, log.getEquipmentID());
            pstmt.setDate(2, Date.valueOf(log.getMaintenanceDate()));
            pstmt.setString(3, log.getDescription());
            pstmt.setDate(4, Date.valueOf(log.getPurchaseDate()));
            pstmt.setInt(5, log.getLogID());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a Maintenance Log by ID
    public boolean deleteMaintenanceLog(int logID) {
        String sql = "DELETE FROM MaintenanceLog WHERE logID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, logID);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve a single Maintenance Log by ID
    public MaintenanceLog getMaintenanceLogById(int logID) {
        String sql = "SELECT * FROM MaintenanceLog WHERE logID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, logID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                MaintenanceLog log = new MaintenanceLog(
                        rs.getInt("equipmentID"),
                        rs.getDate("maintenanceDate").toLocalDate(),
                        rs.getString("description"),
                        rs.getDate("purchaseDate").toLocalDate()
                );
                log.setLogID(rs.getInt("logID")); // Set logID here after object creation
                return log;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
