package dao;

import entity.Payment;
import util.DBConnection;

import java.sql.*;

public class PaymentDAO {




        // Method to create the Payment table and the junction table with Member
        public void createTable() {
            String paymentTableSql = "CREATE TABLE IF NOT EXISTS Payment (" +
                    "PaymentID serial PRIMARY KEY, " +
                    "PaymentFor varchar(255) NOT NULL, " +
                    "PaymentDate date NOT NULL, " +
                    "Amount decimal(10,2) NOT NULL, " +
                    "PaymentMethod varchar(255) NOT NULL, " +
                    "PaymentStatus varchar(255) NOT NULL)";

            String paymentMemberTableSql = "CREATE TABLE IF NOT EXISTS PaymentMember (" +
                    "PaymentID int NOT NULL, " +
                    "MemberID int NOT NULL, " +
                    "FOREIGN KEY (PaymentID) REFERENCES Payment(PaymentID), " +
                    "FOREIGN KEY (MemberID) REFERENCES Member(MemberID), " +
                    "PRIMARY KEY (PaymentID, MemberID))";

            executeSql(paymentTableSql);
            executeSql(paymentMemberTableSql);
        }

        private void executeSql(String sql) {
            try (Connection conn = DBConnection.getConnection();
                 Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }




    // Method to add a new payment
    public int addPayment(Payment payment) {
        String sql = "INSERT INTO Payment (PaymentFor, PaymentDate, Amount, PaymentMethod, PaymentStatus) VALUES (?, ?, ?, ?, ?)";
        int paymentId = -1; // Default to -1, which indicates failure

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, payment.getPaymentFor());
            pstmt.setDate(2, Date.valueOf(payment.getPaymentDate()));
            pstmt.setBigDecimal(3, payment.getAmount());
            pstmt.setString(4, payment.getPaymentMethod());
            pstmt.setString(5, payment.getPaymentStatus());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        paymentId = generatedKeys.getInt(1); // Get the generated payment ID
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paymentId; // Return the payment ID (or -1 if the operation failed)
    }

    public boolean linkPaymentToMember(int paymentId, int memberId) {
        String sql = "INSERT INTO PaymentMember (PaymentID, MemberID) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, paymentId);
            pstmt.setInt(2, memberId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
