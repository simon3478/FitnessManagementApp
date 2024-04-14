package dao;

import dao.MemberDAO;
import entity.Member;
import entity.Trainer;
import util.DBConnection;

import java.sql.*;
import java.util.List;

public class TrainerDAO {

    // Assuming MemberDAO already exists and has the searchMembersByName method implemented
    private MemberDAO memberDAO;

    // Constructor
    public TrainerDAO() {
        this.memberDAO = new MemberDAO();
    }





        // Method to create the Trainer table and its associated junction table TrainerWorkout
        public void createTable() {
            String trainerTableSql = "CREATE TABLE IF NOT EXISTS Trainer (" +
                    "TrainerID serial PRIMARY KEY, " +
                    "FirstName varchar(255) NOT NULL, " +
                    "LastName varchar(255) NOT NULL, " +
                    "Password varchar(255) NOT NULL, " +
                    "Age int NOT NULL, " +
                    "City varchar(255) NOT NULL, " +
                    "StreetNo varchar(255) NOT NULL, " +
                    "StreetName varchar(255) NOT NULL, " +
                    "ManagerID int NOT NULL, " +
                    "FOREIGN KEY (ManagerID) REFERENCES Manager(ManagerID))";

            String trainerWorkoutTableSql = "CREATE TABLE IF NOT EXISTS TrainerWorkout (" +
                    "TrainerID int NOT NULL, " +
                    "WorkoutID int NOT NULL, " +
                    "FOREIGN KEY (TrainerID) REFERENCES Trainer(TrainerID), " +
                    "FOREIGN KEY (WorkoutID) REFERENCES Workout(WorkoutID), " +
                    "PRIMARY KEY (TrainerID, WorkoutID))";
            String phonetrainer = "CREATE TABLE IF NOT EXISTS PhoneTrainer (" +
                    "TrainerID int NOT NULL, " +
                    "PhoneNo VARCHAR(255) NOT NULL, " +
                    "PRIMARY KEY (TrainerID, PhoneNo), " +
                    "FOREIGN KEY (TrainerID) REFERENCES Trainer(TrainerID))";


            executeSql(trainerTableSql);
            executeSql(trainerWorkoutTableSql);
            executeSql(phonetrainer);
        }

        private void executeSql(String sql) {
            try (Connection conn = DBConnection.getConnection();
                 Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



    public boolean reassignTrainersToNewManager(int oldManagerId, int newManagerId) {
        // SQL to update the ManagerID for all trainers under the old manager
        String sql = "UPDATE Trainer SET ManagerID = ? WHERE ManagerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newManagerId);
            pstmt.setInt(2, oldManagerId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean addTrainer(Trainer trainer) {
        String sql = "INSERT INTO Trainer (FirstName, LastName, Password, Age, City, StreetNo, StreetName, ManagerId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, trainer.getFirstName());
            pstmt.setString(2, trainer.getLastName());
            pstmt.setString(3, trainer.getPassword());
            pstmt.setInt(4, trainer.getAge());
            pstmt.setString(5, trainer.getCity());
            pstmt.setInt(6, trainer.getStreetNo());
            pstmt.setString(7, trainer.getStreetName());
            pstmt.setInt(8, trainer.getManagerId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    trainer.setTrainerId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating trainer failed, no ID obtained.");
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateTrainer(Trainer trainer) {
        String sql = "UPDATE Trainer SET FirstName = ?, LastName = ?, Password = ?, Age = ?, City = ?, StreetNo = ?, StreetName = ?, ManagerId = ? WHERE TrainerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, trainer.getFirstName());
            pstmt.setString(2, trainer.getLastName());
            pstmt.setString(3, trainer.getPassword());
            pstmt.setInt(4, trainer.getAge());
            pstmt.setString(5, trainer.getCity());
            pstmt.setInt(6, trainer.getStreetNo());
            pstmt.setString(7, trainer.getStreetName());
            pstmt.setInt(8, trainer.getManagerId());
            pstmt.setInt(9, trainer.getTrainerId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTrainer(int trainerId) {
        String sql = "DELETE FROM Trainer WHERE TrainerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, trainerId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }





    // Method for trainers to view member profiles by name
    public List<Member> viewMemberProfilesByName(String name) {
        // Delegate the search to MemberDAO
        return memberDAO.searchMembersByName(name);
    }

    // Method to retrieve a Trainer by ID
    public Trainer getTrainerById(int trainerId) {
        String sql = "SELECT * FROM Trainer WHERE TrainerID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, trainerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Trainer trainer = new Trainer(
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Password"),
                        rs.getInt("Age"),
                        rs.getString("City"),
                        rs.getInt("StreetNo"),
                        rs.getString("StreetName"),
                        rs.getInt("ManagerId")
                );
                trainer.setTrainerId(rs.getInt("TrainerID")); // Set the trainerId here, after instantiation
                return trainer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }




}
