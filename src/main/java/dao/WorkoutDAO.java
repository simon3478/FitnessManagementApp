package dao;

import entity.Workout;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutDAO {


    // Method to create the Workout table if it doesn't exist
    public void createTable() {
        String workoutTableSql = "CREATE TABLE IF NOT EXISTS Workout (" +
                "WorkoutID SERIAL PRIMARY KEY, " +
                "WorkoutType VARCHAR(255) NOT NULL)";

        String memberWorkoutTableSql = "CREATE TABLE IF NOT EXISTS MemberWorkout (" +
                "MemberID INT NOT NULL, " +
                "WorkoutID INT NOT NULL, " +
                "PRIMARY KEY (MemberID, WorkoutID)," +
                "FOREIGN KEY (MemberID) REFERENCES Member(MemberID), " +
                "FOREIGN KEY (WorkoutID) REFERENCES Workout(WorkoutID))";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(workoutTableSql);  // Create Workout table
            stmt.execute(memberWorkoutTableSql);  // Create MemberWorkout junction table
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to find all workouts for a specific member
    public List<Workout> findWorkoutsByMemberId(int memberId) {
        List<Workout> workoutList = new ArrayList<>();
        String sql = "SELECT w.* FROM Workout w JOIN MemberWorkout mw ON w.WorkoutID = mw.WorkoutID WHERE mw.MemberID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Workout workout = new Workout();
                // Assuming Workout class has setters for each field
                workout.setWorkoutId(rs.getInt("WorkoutID"));
                workout.setWorkoutType(rs.getString("WorkoutType"));
                // Add more fields based on your Workout entity

                workoutList.add(workout);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workoutList;
    }

    // Method to add a new workout plan
    public boolean addWorkout(Workout workout) {
        String sql = "INSERT INTO Workout (WorkoutType) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, workout.getWorkoutType());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                return false;  // No rows inserted, indicating failure
            }

            return true;  // Workout plan added successfully
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // SQL error occurred
        }
    }
}
