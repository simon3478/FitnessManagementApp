package entity;

public class Trainer {
    private int trainerId;
    private String firstName;
    private String lastName;
    private String password;
    private int age;
    private String city;
    private int streetNo;
    private String streetName;
    private int managerId; // Assuming you need to keep a reference to the Manager entity

    // Default Constructor
    public Trainer() {
    }

    // Constructor with all fields
    public Trainer( String firstName, String lastName, String password, int age, String city, int streetNo, String streetName, int managerId) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.age = age;
        this.city = city;
        this.streetNo = streetNo;
        this.streetName = streetName;
        this.managerId = managerId;
    }

    // Getters and Setters
    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(int streetNo) {
        this.streetNo = streetNo;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    // toString method for debugging and logging purposes (optional)
    @Override
    public String toString() {
        return "Trainer{" +
                "trainerId=" + trainerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='[PROTECTED]'" +
                ", age=" + age +
                ", city='" + city + '\'' +
                ", streetNo=" + streetNo +
                ", streetName='" + streetName + '\'' +
                ", managerId=" + managerId +
                '}';
    }
}
