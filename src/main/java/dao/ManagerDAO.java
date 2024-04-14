package dao;

import entity.Manager;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerDAO {



    // Method to create the Manager table if it doesn't exist
    public void createTable() {
        String managerTableSql = "CREATE TABLE IF NOT EXISTS Manager (" +
                "ManagerID serial PRIMARY KEY, " +
                "FirstName varchar(255) NOT NULL, " +
                "LastName varchar(255) NOT NULL, " +
                "Password varchar(255) NOT NULL)";

        executeSql(managerTableSql);
    }

    private void executeSql(String sql) {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Manager findAvailableManager(int excludingManagerId) {
        // SQL query to find a manager who is not the one being deleted
        String sql = "SELECT * FROM Manager WHERE ManagerID <> ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, excludingManagerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Create a new Manager object using the constructor that doesn't include managerId
                return new Manager(rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    // Add a new Manager
    public boolean addManager(Manager manager) {
        String sql = "INSERT INTO Manager (FirstName, LastName, Password) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, manager.getFirstName());
            pstmt.setString(2, manager.getLastName());
            pstmt.setString(3, manager.getPassword());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            // Optionally retrieve and set the generated managerId
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    manager.setManagerId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating manager failed, no ID obtained.");
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve all Managers
    public List<Manager> getAllManagers() {
        List<Manager> managers = new ArrayList<>();
        // SQL query to select all records from the Manager table
        String sql = "SELECT * FROM Manager";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // Iterate through each row in the result set
            while (rs.next()) {
                Manager manager = new Manager(
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Password")
                );
                manager.setManagerId(rs.getInt("ManagerID")); // Set the managerId after construction
                managers.add(manager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return managers;
    }


    // Update an existing Manager
    public boolean updateManager(int managerId, Manager manager) {
        String sql = "UPDATE Manager SET firstName = ?, lastName = ?, password = ? WHERE ManagerId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, manager.getFirstName());
            pstmt.setString(2, manager.getLastName());
            pstmt.setString(3, manager.getPassword());
            pstmt.setInt(4, managerId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a Manager by ID
    public boolean deleteManager(int managerId) {
        String sql = "DELETE FROM Manager WHERE ManagerId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, managerId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve a single Manager by ID
    public Manager getManagerById(int managerId) {
        String sql = "SELECT * FROM Manager WHERE managerId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, managerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Manager manager = new Manager(
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("password")
                );
                manager.setManagerId(rs.getInt("managerId")); // Set the managerId using the setter
                return manager;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
