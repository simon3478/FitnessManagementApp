package entity;

public class Equipment {
    private int equipmentID;
    private String description;
    private String status;
    private int roomID;

    // Default Constructor
    public Equipment() {
    }

    // Constructor with all fields
    public Equipment( String description, String status, int roomID) {

        this.description = description;
        this.status = status;
        this.roomID = roomID;
    }

    // Getters and Setters
    public int getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "equipmentID=" + equipmentID +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", roomID=" + roomID +
                '}';
    }
}
