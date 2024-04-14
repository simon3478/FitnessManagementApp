package dao;

import entity.Member;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {


    // Method to create the Member table
    public void createMemberTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Member (" +
                "MemberID SERIAL PRIMARY KEY, " +
                "FirstName VARCHAR(255), " +
                "LastName VARCHAR(255), " +
                "Age INT, " +
                "Email VARCHAR(255) UNIQUE NOT NULL, " +
                "Password VARCHAR(255), " +
                "City VARCHAR(255), " +
                "StreetNo INT, " +
                "StreetName VARCHAR(255), " +
                "MembershipSubscriptionID INT, " +
                "FitnessGoalType VARCHAR(255), " +
                "FitnessGoalTarget VARCHAR(255), " +
                "FOREIGN KEY (MembershipSubscriptionID) REFERENCES MembershipSubscriptions(MembershipSubscriptionID)) ";


        String phonemember = "CREATE TABLE IF NOT EXISTS PhoneMember (" +
                "MemberID int NOT NULL, " +
                "PhoneNo VARCHAR(255) NOT NULL, " +
                "PRIMARY KEY (MemberID, PhoneNo), " +
                "FOREIGN KEY (MemberID) REFERENCES Member(MemberID))";


        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            stmt.execute(phonemember);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addTrainerForeignKey() {
        String sqlAddForeignKey = "ALTER TABLE Member ADD COLUMN TrainerID INT, " +
                "ADD FOREIGN KEY (TrainerID) REFERENCES Trainer(TrainerID)";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlAddForeignKey);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to search for members by name
    public List<Member> searchMembersByName(String name) {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM Member WHERE FirstName LIKE ? OR LastName LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + name + "%");
            pstmt.setString(2, "%" + name + "%");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Member member = new Member();
                // Setting fields from ResultSet
                member.setMemberId(rs.getInt("MemberID"));
                member.setFirstName(rs.getString("FirstName"));
                member.setLastName(rs.getString("LastName"));
                member.setAge(rs.getInt("Age"));
                member.setEmail(rs.getString("Email"));
                member.setPassword(rs.getString("Password")); // Consider security implications
                member.setCity(rs.getString("City"));
                member.setStreetNo(rs.getInt("StreetNo"));
                member.setStreetName(rs.getString("StreetName"));
                member.setMembershipsubscriptionId(rs.getInt("MembershipsubscriptionID"));
                member.setFitnessGoalType(rs.getString("FitnessGoalType"));
                member.setFitnessGoalTarget(rs.getString("FitnessGoalTarget"));
                // Handling nullable trainerId
                int trainerIdColumnIndex = rs.findColumn("TrainerID");
                if (rs.wasNull()) {
                    member.setTrainerId(null);
                } else {
                    member.setTrainerId(rs.getInt(trainerIdColumnIndex));
                }

                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }



    // Method to add a new member (User Registration)
// Method to add a new member (User Registration)
    public int addMember(Member member) {
        String sql = "INSERT INTO Member (FirstName, LastName, Age, Email, Password, City, StreetNo, StreetName, FitnessGoalType, FitnessGoalTarget, MembershipSubscriptionID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, member.getFirstName());
            pstmt.setString(2, member.getLastName());
            pstmt.setInt(3, member.getAge());
            pstmt.setString(4, member.getEmail());
            pstmt.setString(5, member.getPassword());
            pstmt.setString(6, member.getCity());
            pstmt.setInt(7, member.getStreetNo());
            pstmt.setString(8, member.getStreetName());
            pstmt.setString(9, member.getFitnessGoalType());
            pstmt.setString(10, member.getFitnessGoalTarget());
            pstmt.setInt(11, member.getMembershipsubscriptionId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);  // Return the newly created member ID
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  // Return -1 if insertion failed
    }


    public boolean addPhoneMember(int memberId, String phoneNumber) {
        String sql = "INSERT INTO PhoneMember (MemberID, PhoneNo) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            pstmt.setString(2, phoneNumber);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error adding phone number: " + e.getMessage());
            return false;
        }
    }


    // Method to delete a member
    public boolean deleteMember(int memberId) {
        String sql = "DELETE FROM Member WHERE MemberID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, memberId);  // Set the member ID to delete

            int affectedRows = pstmt.executeUpdate();  // Execute the delete operation
            return affectedRows > 0;  // Return true if the deletion was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Return false if there was an SQL error
        }
    }



    // Method to fetch a member by ID
    public Member getMemberById(int memberId) {
        String sql = "SELECT * FROM Member WHERE MemberID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, memberId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapToMember(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to update a member (Profile Management including fitness goals)
    public boolean updateMember(Member member) {
        String sql = "UPDATE Member SET FirstName = ?, LastName = ?, Age = ?, Email = ?, Password = ?, City = ?, StreetNo = ?, StreetName = ?, MembershipSubscriptionID = ?, FitnessGoalType = ?, FitnessGoalTarget = ? WHERE MemberID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, member.getFirstName());
            pstmt.setString(2, member.getLastName());
            pstmt.setInt(3, member.getAge());
            pstmt.setString(4, member.getEmail());
            pstmt.setString(5, member.getPassword());
            pstmt.setString(6, member.getCity());
            pstmt.setInt(7, member.getStreetNo());
            pstmt.setString(8, member.getStreetName());
            pstmt.setInt(9, member.getMembershipsubscriptionId());
            pstmt.setString(10, member.getFitnessGoalType());
            pstmt.setString(11, member.getFitnessGoalTarget());
            pstmt.setInt(12, member.getMemberId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Additional methods like deleteMember and getAllMembers could follow a similar structure
    // Assuming the deleteMember(int memberId) and getAllMembers() methods remain unchanged

    // Helper method to map a ResultSet to a Member object
    private Member mapToMember(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setMemberId(rs.getInt("MemberID"));
        member.setFirstName(rs.getString("FirstName"));
        member.setLastName(rs.getString("LastName"));
        member.setAge(rs.getInt("Age"));
        member.setEmail(rs.getString("Email"));
        member.setPassword(rs.getString("Password"));
        member.setCity(rs.getString("City"));
        member.setStreetNo(rs.getInt("StreetNo"));
        member.setStreetName(rs.getString("StreetName"));
        member.setMembershipsubscriptionId(rs.getInt("MembershipSubscriptionID"));
        member.setTrainerId((Integer) rs.getObject("TrainerID"));
        member.setFitnessGoalType(rs.getString("FitnessGoalType"));
        member.setFitnessGoalTarget(rs.getString("FitnessGoalTarget"));
        return member;
    }

    public boolean bookTrainingSession(int memberId, int trainerId, Date bookingDate, Time startTime, Time endTime) {
        // This method books a training session with a trainer for a member.

        Connection connection = null;
        PreparedStatement checkAvailabilityStmt = null;
        PreparedStatement bookSessionStmt = null;
        ResultSet resultSet = null;
        boolean bookingSuccessful = false;

        try {
            connection = DBConnection.getConnection(); // dataSource should be defined and configured elsewhere

            // SQL to check the trainer's availability
            String checkAvailabilitySql = "SELECT * FROM TrainerAvailability WHERE TrainerID = ? AND AvailableDate = ? AND StartTime <= ? AND EndTime >= ?";
            checkAvailabilityStmt = connection.prepareStatement(checkAvailabilitySql);
            checkAvailabilityStmt.setInt(1, trainerId);
            checkAvailabilityStmt.setDate(2, bookingDate);
            checkAvailabilityStmt.setTime(3, startTime);
            checkAvailabilityStmt.setTime(4, endTime);

            resultSet = checkAvailabilityStmt.executeQuery();

            // If the trainer is available, proceed to book the session
            if (resultSet.next()) {
                // SQL to insert a new booking
                String bookSessionSql = "INSERT INTO Booking (BookingDate, StartTime, EndTime, Status, MemberID, TrainerID) VALUES (?, ?, ?, 'Confirmed', ?, ?)";
                bookSessionStmt = connection.prepareStatement(bookSessionSql, Statement.RETURN_GENERATED_KEYS);
                bookSessionStmt.setDate(1, bookingDate);
                bookSessionStmt.setTime(2, startTime);
                bookSessionStmt.setTime(3, endTime);
                bookSessionStmt.setInt(4, memberId);
                bookSessionStmt.setInt(5, trainerId);

                int rowsAffected = bookSessionStmt.executeUpdate();
                bookingSuccessful = rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close all resources
            try {
                if (resultSet != null) resultSet.close();
                if (checkAvailabilityStmt != null) checkAvailabilityStmt.close();
                if (bookSessionStmt != null) bookSessionStmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return bookingSuccessful;
    }


    public List<Member> findMembersByTrainerId(int trainerId) {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT m.* FROM Member m JOIN ClassMembers cm ON m.MemberID = cm.MemberID JOIN Classes c ON cm.ClassID = c.ClassID WHERE c.TrainerID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, trainerId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getInt("MemberID"));
                member.setFirstName(rs.getString("FirstName"));
                member.setLastName(rs.getString("LastName"));
                // Populate other member fields as needed
                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }

    public boolean updateMemberTrainerId(int memberId, Integer trainerId) {
        String sql = "UPDATE Member SET TrainerID = ? WHERE MemberID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (trainerId == null) {
                pstmt.setNull(1, Types.INTEGER);
            } else {
                pstmt.setInt(1, trainerId);
            }
            pstmt.setInt(2, memberId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}
