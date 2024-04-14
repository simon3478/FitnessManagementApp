package entity;

import java.time.LocalDate;

public class AdministrativeStaff {
    private int staffID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private String mobilePhone;
    private LocalDate hireDate;
    private int managerID;

    // Default Constructor
    public AdministrativeStaff() {
    }

    // Constructor with all fields
    public AdministrativeStaff( String firstName, String lastName, String email, String password, String role, String mobilePhone, LocalDate hireDate, int managerID) {
        //this.staffID = staffID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.mobilePhone = mobilePhone;
        this.hireDate = hireDate;
        this.managerID = managerID;
    }

    // Getters and Setters
    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    @Override
    public String toString() {
        return "AdministrativeStaff{" +
                "staffID=" + staffID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='[PROTECTED]'" + // It's a good practice not to log passwords
                ", role='" + role + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", hireDate=" + hireDate +
                ", managerID=" + managerID +
                '}';
    }
}
