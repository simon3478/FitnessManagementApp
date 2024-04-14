package dao;

import entity.AdministrativeStaff;
import util.DBConnection;
import java.sql.*;


public class AdministrativeStaffDAO {

    public AdministrativeStaffDAO() {

    }

        // Method to create the AdministrativeStaff table and junction table with MaintenanceLog
        public void createTable() {
            String adminStaffTableSql = "CREATE TABLE IF NOT EXISTS AdministrativeStaff (" +
                    "StaffID serial PRIMARY KEY, " +
                    "FirstName varchar(255) NOT NULL, " +
                    "LastName varchar(255) NOT NULL, " +
                    "Email varchar(255) NOT NULL, " +
                    "Password varchar(255) NOT NULL, " +
                    "Role varchar(255) NOT NULL, " +
                    "MobilePhone varchar(20) NOT NULL, " +
                    "HireDate date NOT NULL, " +
                    "ManagerID int NOT NULL, " +
                    "FOREIGN KEY (ManagerID) REFERENCES Manager(ManagerID))";

            String adminMaintenanceTableSql = "CREATE TABLE IF NOT EXISTS Administrative_Maintenance (" +
                    "StaffID int NOT NULL, " +
                    "LogID int NOT NULL, " +
                    "FOREIGN KEY (StaffID) REFERENCES AdministrativeStaff(StaffID), " +
                    "FOREIGN KEY (LogID) REFERENCES MaintenanceLog(LogID), " +
                    "PRIMARY KEY (StaffID, LogID))";

            executeSql(adminStaffTableSql);
            executeSql(adminMaintenanceTableSql);
        }

        private void executeSql(String sql) {
            try (Connection conn = DBConnection.getConnection();
                 Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }





    // Method to add a new administrative staff member
    public boolean addAdministrativeStaff(AdministrativeStaff staff) {
        String sql = "INSERT INTO AdministrativeStaff (FirstName, LastName, Email, Password, Role, MobilePhone, HireDate, ManagerID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, staff.getFirstName());
            pstmt.setString(2, staff.getLastName());
            pstmt.setString(3, staff.getEmail());
            pstmt.setString(4, staff.getPassword());
            pstmt.setString(5, staff.getRole());
            pstmt.setString(6, staff.getMobilePhone());
            pstmt.setDate(7, Date.valueOf(staff.getHireDate()));
            pstmt.setInt(8, staff.getManagerID());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to update an administrative staff member's information
    public boolean updateAdministrativeStaff(AdministrativeStaff staff) {
        String sql = "UPDATE AdministrativeStaff SET FirstName = ?, LastName = ?, Email = ?, Password = ?, Role = ?, MobilePhone = ?, HireDate = ?, ManagerID = ? WHERE StaffID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, staff.getFirstName());
            pstmt.setString(2, staff.getLastName());
            pstmt.setString(3, staff.getEmail());
            pstmt.setString(4, staff.getPassword());
            pstmt.setString(5, staff.getRole());
            pstmt.setString(6, staff.getMobilePhone());
            pstmt.setDate(7, Date.valueOf(staff.getHireDate()));
            pstmt.setInt(8, staff.getManagerID());
            pstmt.setInt(9, staff.getStaffID());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    //to get admin staff by their id(primary key)
    public AdministrativeStaff getStaffById(int staffID) {
        String sql = "SELECT * FROM AdministrativeStaff WHERE StaffID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, staffID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                AdministrativeStaff staff = new AdministrativeStaff();
                staff.setStaffID(rs.getInt("StaffID"));
                staff.setFirstName(rs.getString("FirstName"));
                staff.setLastName(rs.getString("LastName"));
                staff.setEmail(rs.getString("Email"));
                staff.setPassword(rs.getString("Password"));
                staff.setRole(rs.getString("Role"));
                staff.setMobilePhone(rs.getString("MobilePhone"));
                staff.setHireDate(rs.getDate("HireDate").toLocalDate());
                staff.setManagerID(rs.getInt("ManagerID"));

                return staff;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }




    //To delete adminstrative staff by their primary key
    public boolean deleteAdministrativeStaff(int staffId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM AdministrativeStaff WHERE StaffID = ?")) {
            pstmt.setInt(1, staffId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting administrative staff: " + e.getMessage());
            return false;
        }
    }

    //Get any available administrative staff, used in handling payment (customer service)
    public AdministrativeStaff getAnyAdministrativeStaff() {
        String sql = "SELECT * FROM AdministrativeStaff LIMIT 1"; // Fetches any one staff member
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                AdministrativeStaff staff = new AdministrativeStaff(
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Email"),
                        rs.getString("Password"),
                        rs.getString("Role"),
                        rs.getString("MobilePhone"),
                        rs.getDate("HireDate").toLocalDate(),
                        rs.getInt("ManagerID"));
                staff.setStaffID(rs.getInt("StaffID"));
                return staff;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



}
