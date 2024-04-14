package entity;

public class Workout {
    private int workoutId;
    private String workoutType;

    // Default Constructor
    public Workout() {
    }

    // Constructor with all fields
    public Workout( String workoutType) {

        this.workoutType = workoutType;
    }

    // Getters and Setters
    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public String getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(String workoutType) {
        this.workoutType = workoutType;
    }

    // toString method for debugging and logging purposes (optional)
    @Override
    public String toString() {
        return "Workout{" +
                "workoutId=" + workoutId +
                ", workoutType='" + workoutType + '\'' +
                '}';
    }
}
