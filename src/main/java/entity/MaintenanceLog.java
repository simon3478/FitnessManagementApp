package entity;

import java.time.LocalDate;

public class MaintenanceLog {
    private int logID;
    private int equipmentID;
    private LocalDate maintenanceDate;
    private String description;
    private LocalDate purchaseDate;

    // Default Constructor
    public MaintenanceLog() {
    }

    // Constructor with all fields
    public MaintenanceLog(int equipmentID, LocalDate maintenanceDate, String description, LocalDate purchaseDate) {

        this.equipmentID = equipmentID;
        this.maintenanceDate = maintenanceDate;
        this.description = description;
        this.purchaseDate = purchaseDate;
    }

    // Getters and Setters
    public int getLogID() {
        return logID;
    }

    public void setLogID(int logID) {
        this.logID = logID;
    }

    public int getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }

    public LocalDate getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(LocalDate maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public String toString() {
        return "MaintenanceLog{" +
                "logID=" + logID +
                ", equipmentID=" + equipmentID +
                ", maintenanceDate=" + maintenanceDate +
                ", description='" + description + '\'' +
                ", purchaseDate=" + purchaseDate +
                '}';
    }
}
