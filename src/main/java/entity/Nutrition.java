package entity;

public class Nutrition {
    private int nutritionId;
    private String nutritionType;

    // Default Constructor
    public Nutrition() {
    }

    // Constructor with parameters
    public Nutrition(String nutritionType) {

        this.nutritionType = nutritionType;
    }

    // Getters and Setters
    public int getNutritionId() {
        return nutritionId;
    }

    public void setNutritionId(int nutritionId) {
        this.nutritionId = nutritionId;
    }

    public String getNutritionType() {
        return nutritionType;
    }

    public void setNutritionType(String nutritionType) {
        this.nutritionType = nutritionType;
    }

    // toString method for debugging and logging purposes (optional)
    @Override
    public String toString() {
        return "Nutrition{" +
                "nutritionId=" + nutritionId +
                ", nutritionType='" + nutritionType + '\'' +
                '}';
    }
}
