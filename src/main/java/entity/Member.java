package entity;

public class Member {
    private int memberId;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String password;
    private String city;
    private int streetNo;
    private String streetName;
    private String fitnessGoalType;
    private String fitnessGoalTarget;
    private int membershipsubscriptionId;

    private Integer trainerId;

    public Integer getTrainerId() {
        return trainerId;
    }

    // Constructor
    public Member() {
    }

    // Constructor with all fields
    public Member(String firstName, String lastName, int age, String email, String password, String city, int streetNo, String streetName, int membershipsubscriptionId, String fitnessGoalType, String fitnessGoalTarget) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.city = city;
        this.streetNo = streetNo;
        this.streetName = streetName;
        this.membershipsubscriptionId = membershipsubscriptionId;
        this.fitnessGoalType = fitnessGoalType;
        this.fitnessGoalTarget = fitnessGoalTarget;

    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    // Getters and Setters
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public int getMembershipsubscriptionId() {
        return membershipsubscriptionId;
    }

    public void setMembershipsubscriptionId(int membershipsubsId) {
        this.membershipsubscriptionId = membershipsubsId;
    }

    public String getFitnessGoalType() {
        return fitnessGoalType;
    }

    public void setFitnessGoalType(String fitnessGoalType) {
        this.fitnessGoalType = fitnessGoalType;
    }

    public String getFitnessGoalTarget() {
        return fitnessGoalTarget;
    }

    public void setFitnessGoalTarget(String fitnessGoalTarget) {
        this.fitnessGoalTarget = fitnessGoalTarget;
    }

    // toString() method for debugging (optional)
    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", city='" + city + '\'' +
                ", streetNo=" + streetNo +
                ", streetName='" + streetName + '\'' +
                ", membershipsubscriptionId=" + membershipsubscriptionId +
                ", fitnessGoalType='" + fitnessGoalType + '\'' +
                ", fitnessGoalTarget='" + fitnessGoalTarget + '\'' +
                '}';
    }
}
