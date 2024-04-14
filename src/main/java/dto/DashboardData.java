package entity;

import java.util.List;

public class DashboardData {
    private List<Progress> progressList;
    private List<Workout> workouts;


    // Constructors
    public DashboardData() {
    }

    public DashboardData(List<Progress> progressList, List<Workout> workouts) {
        this.progressList = progressList;
        this.workouts = workouts;
    }

    // Getters and Setters
    public List<Progress> getProgressList() {
        return progressList;
    }

    public void setProgressList(List<Progress> progressList) {
        this.progressList = progressList;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }


    // Method to retrieve achievements from progress
    // This is a placeholder, adjust according to how you want to define or extract achievements from progress
    public List<String> getAchievements() {
        return progressList.stream()
                .map(Progress::getPersonalRecords) // Assuming getPersonalRecords returns a String or something representing an achievement
                .toList();
    }
}
