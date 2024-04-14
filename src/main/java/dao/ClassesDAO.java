package dao;

import entity.Classes;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ClassesDAO {



    // Method to create the Classes table
    public void createTable() {
        String classesTableSql = "CREATE TABLE IF NOT EXISTS Classes (" +
                "ClassID serial PRIMARY KEY, " +
                "Cost decimal(10,2) NOT NULL, " +
                "Time time NOT NULL, " +
                "Date date NOT NULL, " +
                "ClassType varchar(255) NOT NULL, " +
                "ClassSize int NOT NULL, " +
                "TrainerID int NOT NULL, " +
                "FOREIGN KEY (TrainerID) REFERENCES Trainer(TrainerID))";

        String classesMembersTableSql = "CREATE TABLE IF NOT EXISTS ClassesMembers (" +
                "ClassID int NOT NULL, " +
                "MemberID int NOT NULL, " +
                "AttendaceDate date NOT NULL, " +
                "PRIMARY KEY (ClassID, MemberID), " +
                "FOREIGN KEY (ClassID) REFERENCES Classes(ClassID), " +
                "FOREIGN KEY (MemberID) REFERENCES Member(MemberID))";

        executeSql(classesTableSql);
        executeSql(classesMembersTableSql);
    }

    private void executeSql(String sql) {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // Method to update a class's schedule and assigned trainer
    public boolean updateClasses(Classes classes) {
        String sql = "UPDATE Classes SET Cost = ?, Time = ?, Date = ?, ClassType = ?, ClassSize = ?, TrainerID = ? WHERE ClassID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, classes.getCost());
            pstmt.setTime(2, Time.valueOf(classes.getTime()));
            pstmt.setDate(3, Date.valueOf(classes.getDate()));
            pstmt.setString(4, classes.getClassType());
            pstmt.setInt(5, classes.getClassSize());
            pstmt.setInt(6, classes.getTrainerId());
            pstmt.setInt(7, classes.getClassId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to add a new class
    public boolean addClass(Classes classes) {
        String sql = "INSERT INTO Classes (Cost, Time, Date, ClassType, ClassSize, TrainerID) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, classes.getCost());
            pstmt.setTime(2, Time.valueOf(classes.getTime()));
            pstmt.setDate(3, Date.valueOf(classes.getDate()));
            pstmt.setString(4, classes.getClassType());
            pstmt.setInt(5, classes.getClassSize());
            pstmt.setInt(6, classes.getTrainerId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public boolean registerMemberForClass(int memberId, int classId, LocalDate attendanceDate) {
        String sql = "INSERT INTO ClassesMembers (ClassID, MemberID, AttendanceDate) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, classId);
            pstmt.setInt(2, memberId);
            pstmt.setDate(3, Date.valueOf(attendanceDate));
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Method to find a class by ID
    public Classes findClassById(int classId) {
        String sql = "SELECT * FROM Classes WHERE ClassID = ?";
        Classes classes = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, classId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                classes = new Classes();
                classes.setClassId(rs.getInt("ClassID"));
                classes.setCost(rs.getDouble("Cost"));
                classes.setTime(rs.getTime("Time").toLocalTime());
                classes.setDate(rs.getDate("Date").toLocalDate());
                classes.setClassType(rs.getString("ClassType"));
                classes.setClassSize(rs.getInt("ClassSize"));
                classes.setTrainerId(rs.getInt("TrainerID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classes;
    }

    public List<Classes> listAllClasses() {
        List<Classes> classList = new ArrayList<>();
        String sql = "SELECT * FROM Classes";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Classes cls = new Classes();
                cls.setClassId(rs.getInt("ClassID"));
                cls.setCost(rs.getDouble("Cost"));
                cls.setTime(rs.getTime("Time").toLocalTime());
                cls.setDate(rs.getDate("Date").toLocalDate());
                cls.setClassType(rs.getString("ClassType"));
                cls.setClassSize(rs.getInt("ClassSize"));
                cls.setTrainerId(rs.getInt("TrainerID"));
                classList.add(cls);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classList;
    }

}
