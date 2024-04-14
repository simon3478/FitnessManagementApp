package entity;

public class Rooms {
    private int roomId;
    private String condition;
    private int roomSize;
    private String roomType;

    // Default Constructor
    public Rooms() {
    }

    // Constructor with all fields
    public Rooms( String condition, int roomSize, String roomType) {

        this.condition = condition;
        this.roomSize = roomSize;
        this.roomType = roomType;
    }

    public Rooms(int roomId, String condition, int roomSize, String roomType) {
        this.roomId=roomId;
        this.condition = condition;
        this.roomSize = roomSize;
        this.roomType = roomType;
    }

    // Getters and Setters
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(int roomSize) {
        this.roomSize = roomSize;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    // toString method for debugging and logging purposes (optional)
    @Override
    public String toString() {
        return "Rooms{" +
                "roomId=" + roomId +
                ", condition='" + condition + '\'' +
                ", roomSize=" + roomSize +
                ", roomType='" + roomType + '\'' +
                '}';
    }
}
