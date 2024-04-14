package entity;

import dao.PaymentDAO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment {
    private int paymentId;
    private String paymentFor;
    private LocalDate paymentDate;
    private BigDecimal amount;
    private String paymentMethod;
    private String paymentStatus;

    // Default constructor
    public Payment() {
    }

    // Constructor with all fields
    public Payment( String paymentFor, LocalDate paymentDate, BigDecimal amount, String paymentMethod, String paymentStatus) {

        this.paymentFor = paymentFor;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    // Getters and Setters
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentFor() {
        return paymentFor;
    }

    public void setPaymentFor(String paymentFor) {
        this.paymentFor = paymentFor;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }


    // Process a payment

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // toString method for debugging and logging purposes (optional)
    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", paymentFor='" + paymentFor + '\'' +
                ", paymentDate=" + paymentDate +
                ", amount=" + amount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}
