package dao;

import entity.Nutrition;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NutritionDAO {




        // Method to create the Nutrition table and junction tables
        public void createTable() {
            String nutritionTableSql = "CREATE TABLE IF NOT EXISTS Nutrition (" +
                    "NutritionID serial PRIMARY KEY, " +
                    "NutritionType varchar(255) NOT NULL)";

            String trainerNutritionTableSql = "CREATE TABLE IF NOT EXISTS TrainerNutrition (" +
                    "TrainerID int NOT NULL, " +
                    "NutritionID int NOT NULL, " +
                    "PRIMARY KEY (TrainerID, NutritionID), " +
                    "FOREIGN KEY (TrainerID) REFERENCES Trainer(TrainerID), " +
                    "FOREIGN KEY (NutritionID) REFERENCES Nutrition(NutritionID))";

            String memberNutritionTableSql = "CREATE TABLE IF NOT EXISTS MemberNutrition (" +
                    "MemberID int NOT NULL, " +
                    "NutritionID int NOT NULL, " +
                    "PRIMARY KEY (MemberID, NutritionID), " +
                    "FOREIGN KEY (MemberID) REFERENCES Member(MemberID), " +
                    "FOREIGN KEY (NutritionID) REFERENCES Nutrition(NutritionID))";

            executeSql(nutritionTableSql);
            executeSql(trainerNutritionTableSql);
            executeSql(memberNutritionTableSql);
        }

        private void executeSql(String sql) {
            try (Connection conn = DBConnection.getConnection();
                 Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }




    // Method to add a new Nutrition record
    public boolean addNutrition(Nutrition nutrition) {
        String sql = "INSERT INTO Nutrition (nutritionType) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nutrition.getNutritionType());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to retrieve all Nutrition records
    public static List<Nutrition> getAllNutritions() {
        List<Nutrition> nutritions = new ArrayList<>();
        String sql = "SELECT * FROM Nutrition";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Nutrition nutrition = new Nutrition();
                nutrition.setNutritionId(rs.getInt("nutritionId"));
                nutrition.setNutritionType(rs.getString("nutritionType"));
                nutritions.add(nutrition);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nutritions;
    }

    // Method to update an existing Nutrition record
    public boolean updateNutrition(Nutrition nutrition) {
        String sql = "UPDATE Nutrition SET nutritionType = ? WHERE nutritionId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nutrition.getNutritionType());
            pstmt.setInt(2, nutrition.getNutritionId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete a Nutrition record
    public boolean deleteNutrition(int nutritionId) {
        String sql = "DELETE FROM Nutrition WHERE nutritionId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, nutritionId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to retrieve a single Nutrition record by ID
    public Nutrition getNutritionById(int nutritionId) {
        String sql = "SELECT * FROM Nutrition WHERE nutritionId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, nutritionId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Nutrition nutrition = new Nutrition();
                nutrition.setNutritionId(rs.getInt("nutritionId"));
                nutrition.setNutritionType(rs.getString("nutritionType"));
                return nutrition;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Nutrition> findNutritionsByMemberId(int memberId) {
        List<Nutrition> nutritions = new ArrayList<>();
        String sql = "SELECT * FROM Nutrition WHERE MemberID = ?"; // Assuming your Nutrition table now includes a MemberID column for filtering
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Nutrition nutrition = new Nutrition();
                nutrition.setNutritionId(rs.getInt("NutritionID"));
                nutrition.setNutritionType(rs.getString("NutritionType"));
                nutritions.add(nutrition);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nutritions;
    }


}
