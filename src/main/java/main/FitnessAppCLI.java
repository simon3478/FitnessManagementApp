package main;

import dao.*;
import entity.*;
import entity.DashboardData;
import service.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FitnessAppCLI {

    private static final Scanner scanner = new Scanner(System.in);
    private static MemberDAO memberDAO = new MemberDAO();
    private static TrainerDAO trainerDAO = new TrainerDAO();
    private static ClassesDAO classesDAO = new ClassesDAO();
    private static PaymentDAO paymentDAO = new PaymentDAO();
    private static ProgressDAO progressDAO= new ProgressDAO();
    private static WorkoutDAO workoutDAO = new WorkoutDAO();
    private static ManagerDAO managerDAO= new ManagerDAO();
    private static NutritionDAO nutritionDAO = new NutritionDAO();
    private static RoomDAO roomDAO = new RoomDAO();
    private static EquipmentDAO equipmentDAO = new EquipmentDAO();
    private static  AdministrativeStaffDAO administrativeStaffDAO = new AdministrativeStaffDAO();
    private static TrainerAvailabilityDAO trainerAvailabilityDAO = new TrainerAvailabilityDAO();
    private static MembershipDAO membershipDAO= new MembershipDAO();
    private static BookingDAO bookingDAO= new BookingDAO();
    private static MaintenanceLogDAO maintenanceLogDAO=new MaintenanceLogDAO();

    // Assuming you have services set up for some of the more complex operations
    private static DashboardService dashboardService = new DashboardService(memberDAO, progressDAO, workoutDAO); // Initialize with necessary DAOs
    // Add other services as needed

    private static UserSession currentSession = null;
    private static MembershipSubscriptionDAO membershipsubscriptionDAO= new MembershipSubscriptionDAO();




    private static void initializeDatabase() {
        try {

            // Create independent tables first
            managerDAO.createTable();
            membershipDAO.createTable();
            roomDAO.createTable();
            equipmentDAO.createTable();
            maintenanceLogDAO.createTable();
            administrativeStaffDAO.createTable();
            membershipsubscriptionDAO.createTable();
            memberDAO.createMemberTable();
            workoutDAO.createTable();
            trainerDAO.createTable();//
            trainerAvailabilityDAO.createTable();



            nutritionDAO.createTable();
            classesDAO.createTable();
            bookingDAO.createTable();

            paymentDAO.createTable();
            progressDAO.createTable();

            memberDAO.addTrainerForeignKey();

            System.out.println("Database initialization complete.");
        } catch (Exception e) {
            System.out.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        initializeDatabase();

        while (true) {
            if (currentSession == null) {
                System.out.println("Welcome to FitnessAppCLI!");
                login();
            } else {
                showMenu();
            }
        }
    }

    private static void login() {
        System.out.println("Identify yourself or register:");
        System.out.println("1. Member");
        System.out.println("2. Trainer");
        System.out.println("3. Manager");
        System.out.println("4. Administrative Staff");
        System.out.println("5. Register New Member");
        System.out.println("6. Register New Trainer");
        System.out.println("7. Register New Manager");
        System.out.println("8. Register New Administrative Staff");
        System.out.println("9. Add New Membership plan");
        System.out.println("10. Add New Nutrition Plan");
        System.out.println("11. Add New Workout Plan");
        System.out.println("12. Add New Equipment");
        System.out.println("0. To exit");


        System.out.print("Enter your role number or registration option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                currentSession = new UserSession(verifyMember(), "Member");
                break;
            case 2:
                currentSession = new UserSession(verifyTrainer(), "Trainer");
                break;
            case 3:
                currentSession = new UserSession(verifyManager(), "Manager");
                break;
            case 4:
                currentSession = new UserSession(verifyAdministrativeStaff(), "Admin");
                break;
            case 5:
                userRegistration();
                break;
            case 6:
                trainerRegistration();
                break;
            case 7:
                managerRegistration();
                break;
            case 8:
                adminStaffRegistration();
                break;
            case 9:
                addNewMembershipPlan();
                break;
            case 10:
                addNewNutritionPlan();
                break;
            case 11:
                addNewWorkoutPlan();
                break;
            case 12:
                addNewEquipment();
                break;
            case 0:
                System.out.println("Exiting........");
                return;

            default:
                System.out.println("Invalid selection. Please try again.");
                break;
        }

        if (choice >= 1 && choice <= 4) {
            if (currentSession == null || currentSession.getUser() == null) {
                System.out.println("Login failed. Please try again.");
            } else {
                System.out.println("Login successful.");
            }
        }
    }


    private static void logout() {
        currentSession = null;
        System.out.println("You have been logged out.");
    }

    private static void showMenu() {
        if (currentSession == null || currentSession.getUser() == null) {
            System.out.println("No user logged in. Returning to login screen.");
            return;
        }

        System.out.println("Choose an operation:");

        switch (currentSession.getRole()) {
            case "Member":
                System.out.println("1. Update Profile");
                System.out.println("2. View Dashboard");
                System.out.println("3. Book Training Session");
                System.out.println("4. Delete Member");

                break;
            case "Trainer":
                System.out.println("1. Set Trainer Availability");
                System.out.println("2. View Member Profile");
                System.out.println("3. Trainer Registration");
                System.out.println("4. Delete Trainer");
                break;
            case "Manager":

                System.out.println("1. Delete Manager");
                break;
            case "Admin":
                System.out.println("1. Room Booking Management");
                System.out.println("2. Log Equipment Maintenance");
                System.out.println("3. Update Class Schedule");
                System.out.println("4. Process Payment");
                System.out.println("5. Delete Admin Staff");
                break;
        }
        System.out.println("0. Logout");
        System.out.print("Enter choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        performRoleSpecificOperations(choice);
    }


    private static void performRoleSpecificOperations(int choice) {
        if (currentSession == null || currentSession.getUser() == null) {
            System.out.println("Session invalid or user not verified.");
            return;
        }

        try {
            switch (currentSession.getRole()) {
                case "Member":
                    switch (choice) {
                        case 1:
                            updateProfile((Member) currentSession.getUser());
                            break;
                        case 2:
                            viewDashboard((Member) currentSession.getUser());
                            break;
                        case 3:
                            bookTrainingSession((Member) currentSession.getUser());
                            break;
                        case 4:
                            deleteMember((Member) currentSession.getUser());
                            break;
                        case 0:
                            logout();
                            break;
                        default:
                            System.out.println("Invalid choice for Member.");
                            break;
                    }
                    break;
                case "Trainer":
                    switch (choice) {
                        case 1:
                            setTrainerAvailability((Trainer) currentSession.getUser());
                            break;
                        case 2:
                            viewMemberProfile((Trainer) currentSession.getUser());
                            break;

                        case 0:
                            logout();
                            break;
                        default:
                            System.out.println("Invalid choice for Trainer.");
                            break;
                    }
                    break;
                case "Manager":
                    switch (choice) {

                        case 1:
                            deleteManager((Manager) currentSession.getUser());
                            break;
                        case 0:
                            logout();
                            break;
                        default:
                            System.out.println("Invalid choice for Manager.");
                            break;
                    }
                    break;
                case "Admin":
                    switch (choice) {
                        case 1:
                            roomBookingManagement((AdministrativeStaff) currentSession.getUser());
                            break;
                        case 2:
                            logEquipmentMaintenance((AdministrativeStaff) currentSession.getUser());
                            break;
                        case 3:
                            updateClassSchedule((AdministrativeStaff) currentSession.getUser());
                            break;
                        case 4:
                            processPayment((AdministrativeStaff) currentSession.getUser());
                            break;

                        case 5:
                            deleteAdminStaff((AdministrativeStaff) currentSession.getUser());
                            break;
                        case 0:
                            logout();
                            break;
                        default:
                            System.out.println("Invalid choice for Administrative Staff.");
                            break;
                    }
                    break;
            }
        } catch (ClassCastException e) {
            System.out.println("Role and operation mismatch, please log in again.");
            currentSession = null; // Reset session on error
        }
    }


    private static void userRegistration() {
        System.out.println("---- User Registration ----");

        // Prompt for user details
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();  // consume newline
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter city: ");
        String city = scanner.nextLine();
        System.out.print("Enter street number: ");
        int streetNo = scanner.nextInt();
        scanner.nextLine();  // consume newline
        System.out.print("Enter street name: ");
        String streetName = scanner.nextLine();

        // Ask for phone numbers
        System.out.print("How many phone numbers would you like to register? ");
        int numberOfPhones = scanner.nextInt();
        scanner.nextLine();  // consume newline
        List<String> phoneNumbers = new ArrayList<>();
        for (int i = 0; i < numberOfPhones; i++) {
            System.out.print("Enter phone number #" + (i + 1) + ": ");
            phoneNumbers.add(scanner.nextLine());
        }

        // Select membership plan and determine dates
        int membershipId = selectMembership();
        System.out.print("Enter membership start date (YYYY-MM-DD): ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter membership end date (YYYY-MM-DD): ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());

        // Collect additional user details for fitness goals
        System.out.print("Enter fitness goal type: ");
        String fitnessGoalType = scanner.nextLine();
        System.out.print("Enter fitness goal target: ");
        String fitnessGoalTarget = scanner.nextLine();

        // Register the member without a subscription ID
        Member newMember = new Member(firstName, lastName, age, email, password, city, streetNo, streetName, 0, fitnessGoalType, fitnessGoalTarget);
        int memberId = memberDAO.addMember(newMember);

        if (memberId == -1) {
            System.out.println("Registration failed. Please try again.");
            return;
        }

        // Create the membership subscription using the new Member ID
        MembershipSubscription subscription = new MembershipSubscription(membershipId, startDate, endDate);
        boolean subscriptionAdded = membershipsubscriptionDAO.addMembershipSubscription(subscription);

        if (!subscriptionAdded) {
            System.out.println("Failed to create membership subscription.");
            return;
        }

        // Update the member with the correct MembershipSubscriptionID
        newMember.setMembershipsubscriptionId(subscription.getMembershipSubscriptionId());
        boolean updateSuccess = memberDAO.updateMember(newMember);

        if (!updateSuccess) {
            System.out.println("Failed to update member with subscription ID.");
            return;
        }

        // Link phone numbers
        for (String phoneNumber : phoneNumbers) {
            boolean phoneLinked = memberDAO.addPhoneMember(memberId, phoneNumber);
            if (!phoneLinked) {
                System.out.println("Failed to link phone number: " + phoneNumber);
            }
        }

        System.out.println("Registration and phone number linking successful. Member ID: " + memberId);
    }





    private static void trainerRegistration() {
        System.out.println("---- Trainer Registration ----");

        // Prompt for trainer details
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter city: ");
        String city = scanner.nextLine();

        System.out.print("Enter street number: ");
        int streetNo = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter street name: ");
        String streetName = scanner.nextLine();

        System.out.print("Enter manager ID: ");
        int managerId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        // Instantiate the Trainer object
        Trainer newTrainer = new Trainer(firstName, lastName, password, age, city, streetNo, streetName, managerId);

        // Add trainer to the database
        boolean isRegistered = trainerDAO.addTrainer(newTrainer);
        if (isRegistered) {
            System.out.println("Trainer registration successful.");
        } else {
            System.out.println("Trainer registration failed. Please try again.");
        }
    }


    private static void adminStaffRegistration() {
        System.out.println("---- Administrative Staff Registration ----");

        // Collect details
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter role: ");
        String role = scanner.nextLine();

        System.out.print("Enter mobile phone: ");
        String mobilePhone = scanner.nextLine();

        System.out.print("Enter hire date (YYYY-MM-DD): ");
        String hireDateStr = scanner.nextLine();
        LocalDate hireDate = LocalDate.parse(hireDateStr);

        System.out.print("Enter manager ID: ");
        int managerId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        // Instantiate the AdministrativeStaff object
        AdministrativeStaff newStaff = new AdministrativeStaff(firstName, lastName, email, password, role, mobilePhone, hireDate, managerId);

        // Register administrative staff
        boolean isRegistered = administrativeStaffDAO.addAdministrativeStaff(newStaff);
        if (isRegistered) {
            System.out.println("Administrative staff registration successful.");
        } else {
            System.out.println("Administrative staff registration failed. Please try again.");
        }
    }


    private static void managerRegistration() {
        System.out.println("---- Manager Registration ----");

        // Collect details
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Instantiate Manager object
        Manager newManager = new Manager(firstName, lastName, password);

        // Add manager to the database
        boolean isRegistered = managerDAO.addManager(newManager);
        if (isRegistered) {
            System.out.println("Manager registration successful.");
        } else {
            System.out.println("Manager registration failed. Please try again.");
        }
    }



    private static void addNewMembershipPlan() {
        System.out.println("---- Add New Membership Plan ----");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter membership duration (in months): ");
        int duration = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter membership rate: ");
        double rate = scanner.nextDouble();
        scanner.nextLine();  // consume newline

        System.out.print("Enter membership description: ");
        String description = scanner.nextLine();

        Membership newMembership = new Membership(duration, rate, description);
        boolean success = membershipDAO.addMembership(newMembership);

        if (success) {
            System.out.println("New membership plan added successfully.");
        } else {
            System.out.println("Failed to add new membership plan.");
        }
    }

    private static void addNewNutritionPlan() {
        System.out.println("---- Add New Nutrition Plan ----");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter nutrition type: ");
        String nutritionType = scanner.nextLine();

        Nutrition newNutrition = new Nutrition(nutritionType);
        boolean success = nutritionDAO.addNutrition(newNutrition);

        if (success) {
            System.out.println("New nutrition plan added successfully.");
        } else {
            System.out.println("Failed to add new nutrition plan.");
        }
    }

    private static void addNewWorkoutPlan() {
        System.out.println("---- Add New Workout Plan ----");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter workout type: ");
        String workoutType = scanner.nextLine();

        Workout newWorkout = new Workout(workoutType);
        boolean success = workoutDAO.addWorkout(newWorkout);

        if (success) {
            System.out.println("New workout plan added successfully.");
        } else {
            System.out.println("Failed to add new workout plan.");
        }
    }

    private static void addNewEquipment() {
        System.out.println("---- Add New Equipment ----");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter equipment description: ");
        String description = scanner.nextLine();

        System.out.print("Enter equipment status (e.g., new, good, needs repair): ");
        String status = scanner.nextLine();

        System.out.print("Enter room ID where the equipment will be located: ");
        int roomID = scanner.nextInt();
        scanner.nextLine();

        Equipment newEquipment = new Equipment(description, status, roomID);
        boolean success = equipmentDAO.addEquipment(newEquipment);

        if (success) {
            System.out.println("New equipment added successfully.");
        } else {
            System.out.println("Failed to add new equipment.");
        }
    }



    private static void deleteMember(Member currentMember) {
        System.out.println("---- Delete Your Member Profile ----");

        // Confirm deletion with an extra step
        System.out.println("Are you sure you want to delete your profile? This cannot be undone. Enter 'yes' to confirm:");
        String confirmation = scanner.nextLine().trim();
        if (!confirmation.equalsIgnoreCase("yes")) {
            System.out.println("Deletion cancelled.");
            return;
        }

        // Check if the session user's ID matches the ID they want to delete
        if (currentMember == null) {
            System.out.println("Invalid session or user data.");
            return;
        }

        // Proceed with deletion
        boolean deletionSuccess = memberDAO.deleteMember(currentMember.getMemberId());
        if (deletionSuccess) {
            System.out.println("Your profile has been successfully deleted.");
            // Log the user out after deletion
            logout();
        } else {
            System.out.println("Failed to delete your profile.");
        }
    }


    private static void deleteAdminStaff(AdministrativeStaff currentAdmin) {
        System.out.println("---- Delete Administrative Staff Profile ----");

        // Ask for confirmation to prevent accidental deletions
        System.out.println("Are you sure you want to delete your administrative staff profile? This action cannot be undone. Type 'yes' to confirm:");
        String confirmation = scanner.nextLine().trim();
        if (!confirmation.equalsIgnoreCase("yes")) {
            System.out.println("Deletion cancelled.");
            return;
        }

        if (currentAdmin == null) {
            System.out.println("Invalid session or user data.");
            return;
        }

        // Proceed with deletion
        boolean deletionSuccess = administrativeStaffDAO.deleteAdministrativeStaff(currentAdmin.getStaffID());
        if (deletionSuccess) {
            System.out.println("Your administrative staff profile has been successfully deleted.");
            // Log out after deletion
            logout();
        } else {
            System.out.println("Failed to delete your administrative staff profile.");
        }
    }





    private static void updateProfile(Member member) {
        System.out.println("---- Update Profile ----");



        if (member == null) {
            // Verification failed or member not found, exit the method
            return;
        }


        boolean keepUpdating = true;
        while (keepUpdating) {
            System.out.println("What would you like to update?");
            System.out.println("1. First Name");
            System.out.println("2. Last Name");
            System.out.println("3. Email");
            System.out.println("4. Fitness Goal Type");
            System.out.println("5. Fitness Goal Target");
            System.out.println("6. Password");
            System.out.println("7. Done updating");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter new first name: ");
                    member.setFirstName(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Enter new last name: ");
                    member.setLastName(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Enter new email: ");
                    member.setEmail(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Enter new fitness goal type: ");
                    member.setFitnessGoalType(scanner.nextLine());
                    break;
                case 5:
                    System.out.print("Enter new fitness goal target: ");
                    member.setFitnessGoalTarget(scanner.nextLine());
                    break;
                case 6:
                    System.out.print("Enter new password: ");

                    String newPassword = scanner.nextLine();
                    member.setPassword(hashPassword(newPassword));
                    break;
                case 7:
                    keepUpdating = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        boolean updateSuccessful = memberDAO.updateMember(member);
        if (updateSuccessful) {
            System.out.println("Profile updated successfully.");
        } else {
            System.out.println("Failed to update profile.");
        }
    }


    private static String hashPassword(String password) {
        //for simplicity i will use this without hashing
        return password;
    }



    private static void viewDashboard(Member member) {
        System.out.println("Accessing Member Dashboard...");


        if (member != null) {
            System.out.println("Member verified successfully. Displaying dashboard...");
            // Assuming you already have the memberId after verification
            int memberId = member.getMemberId();
            DashboardData dashboardData = dashboardService.getDashboardDataForUser(memberId);
            displayDashboardData(dashboardData);
        } else {
            System.out.println("Verification failed. Access denied.");
        }
    }



    private static void displayDashboardData(DashboardData dashboardData) {
        // Display logic for dashboard data
        System.out.println("Your Progress:");
        dashboardData.getProgressList().forEach(progress -> {
            System.out.println("Date: " + progress.getDate() + ", Record: " + progress.getPersonalRecords());
        });

        System.out.println("Your Workouts:");
        dashboardData.getWorkouts().forEach(workout -> {
            System.out.println("Workout Type: " + workout.getWorkoutType()); // Adjust based on actual data model
        });

        // Further details can be added as per the DashboardData structure
    }




    private static void bookTrainingSession(Member currentMember) {
        System.out.println("---- Book Training Session ----");

        if (currentMember == null) {
            System.out.println("Session invalid or user not verified.");
            return;
        }

        // List available classes and select one
        System.out.println("Select a class from the following options:");
        List<Classes> classesList = classesDAO.listAllClasses(); // Assume this method exists and lists all available classes
        for (int i = 0; i < classesList.size(); i++) {
            Classes cls = classesList.get(i);
            System.out.println((i + 1) + ". " + cls.getClassType() + " at " + cls.getTime() + " on " + cls.getDate());
        }
        System.out.print("Enter class number: ");
        int classChoice = scanner.nextInt() - 1;

        if (classChoice < 0 || classChoice >= classesList.size()) {
            System.out.println("Invalid class selection.");
            return;
        }

        Classes selectedClass = classesList.get(classChoice);

        // Check if the class is available
        if (!trainerAvailabilityDAO.isTrainerAvailable(selectedClass.getTrainerId(), selectedClass.getDate(), selectedClass.getTime(), selectedClass.getTime().plusHours(1))) {
            System.out.println("This class is not available. Please choose a different class.");
            return;
        }

        // Fetch an administrative staff to handle the payment
        AdministrativeStaff adminForPayment = administrativeStaffDAO.getAnyAdministrativeStaff();
        if (adminForPayment == null) {
            System.out.println("No administrative staff available to process the payment.");
            return;
        }

        // Process payment
        BigDecimal cost = BigDecimal.valueOf(selectedClass.getCost());
        System.out.println("The cost for this class is $" + cost + ". Please proceed with payment.");
        int paymentId = processPayment(adminForPayment);
        if (paymentId != -1) {
            System.out.println("Payment successful and class booked.");

            // Link the payment to the booking
            boolean linkPaymentSuccess = paymentDAO.linkPaymentToMember(paymentId, currentMember.getMemberId());
            if (!linkPaymentSuccess) {
                System.out.println("Failed to link payment to booking.");
            }

            // Register the member for the class
            boolean registerSuccess = classesDAO.registerMemberForClass(currentMember.getMemberId(), selectedClass.getClassId(), LocalDate.now());
            if (registerSuccess) {
                System.out.println("Registration for class successful.");
            } else {
                System.out.println("Failed to register for the class.");
            }

            // Update member's trainerId to the trainerId of the booked class
            boolean updateTrainerSuccess = memberDAO.updateMemberTrainerId(currentMember.getMemberId(), selectedClass.getTrainerId());
            if (updateTrainerSuccess) {
                System.out.println("Your trainer has been updated based on the booked class.");
            } else {
                System.out.println("Failed to update your trainer.");
            }
        } else {
            System.out.println("Payment failed. Could not book class.");
        }
    }



    private static void setTrainerAvailability(Trainer trainer) {
        System.out.println("---- Set Trainer Availability ----");


        if (trainer == null) {
            System.out.println("Trainer verification failed. Cannot set availability.");
            return;
        }

        System.out.print("Enter date for availability (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter start time (HH:MM): ");
        LocalTime startTime = LocalTime.parse(scanner.nextLine());

        System.out.print("Enter end time (HH:MM): ");
        LocalTime endTime = LocalTime.parse(scanner.nextLine());

        // Instantiate a TrainerAvailability object with collected data
        TrainerAvailability availability = new TrainerAvailability();
        availability.setTrainerID(trainer.getTrainerId());
        availability.setAvailableDate(date);
        availability.setStartTime(startTime);
        availability.setEndTime(endTime);

        // Save this availability to the database
        boolean success = trainerAvailabilityDAO.addTrainerAvailability(availability);

        if (success) {
            System.out.println("Trainer availability set successfully for " + date);
        } else {
            System.out.println("Failed to set trainer availability.");
        }
    }

    private static void viewMemberProfile(Trainer trainer) {
        System.out.println("---- View Member Profile ----");


        if (trainer == null) {
            System.out.println("Trainer verification failed. Cannot view member profiles.");
            return;
        }

        // List all members trained by this trainer
        System.out.println("Select a member to view their profile:");
        List<Member> members = memberDAO.findMembersByTrainerId(trainer.getTrainerId());
        if (members.isEmpty()) {
            System.out.println("You do not have any members assigned.");
            return;
        }
        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            System.out.println((i + 1) + ". " + member.getFirstName() + " " + member.getLastName() + " (ID: " + member.getMemberId() + ")");
        }

        System.out.print("Enter member number: ");
        int memberChoice = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline
        if (memberChoice < 0 || memberChoice >= members.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        Member selectedMember = members.get(memberChoice);

        // Display selected member information
        System.out.println("Member ID: " + selectedMember.getMemberId());
        System.out.println("Name: " + selectedMember.getFirstName() + " " + selectedMember.getLastName());
        // Potentially display more details...

        // Fetch and display workouts for the selected member
        List<Workout> workouts = workoutDAO.findWorkoutsByMemberId(selectedMember.getMemberId());
        System.out.println("Workouts:");
        workouts.forEach(workout -> System.out.println("- " + workout.getWorkoutType()));

        // Fetch and display nutrition records for the selected member
        List<Nutrition> nutritions = nutritionDAO.findNutritionsByMemberId(selectedMember.getMemberId()); // Assuming implementation
        System.out.println("Nutrition Records:");
        nutritions.forEach(nutrition -> System.out.println("- " + nutrition.getNutritionType()));
    }



    private static void roomBookingManagement( AdministrativeStaff admin ) {
        System.out.println("---- Room Booking Management ----");


        if (admin == null) {
            System.out.println("Administrative staff verification failed. Access denied.");
            return;
        }

        RoomDAO roomDAO = new RoomDAO();
        boolean running = true;
        while (running) {
            System.out.println("Choose an option:");
            System.out.println("1. View Room Bookings");
            System.out.println("2. Add New Room Booking");
            System.out.println("3. Update Room Booking");
            System.out.println("4. Delete Room Booking");
            System.out.println("5. Exit Room Booking Management");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewRoomBookings(roomDAO);
                    break;
                case 2:
                    addRoomBooking(roomDAO);
                    break;
                case 3:
                    updateRoomBooking(roomDAO);
                    break;
                case 4:
                    deleteRoomBooking(roomDAO);
                    break;
                case 5:
                    System.out.println("Exiting Room Booking Management...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }



    private static void viewRoomBookings(RoomDAO roomDAO) {
        List<Rooms> rooms = roomDAO.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms currently available.");
            return;
        }
        rooms.forEach(room -> System.out.println(room.toString()));
    }


    private static void addRoomBooking(RoomDAO roomDAO) {
        System.out.println("Adding a new room booking.");

        System.out.print("Enter room condition: ");
        String condition = scanner.nextLine();

        System.out.print("Enter room size: ");
        int roomSize = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter room type: ");
        String roomType = scanner.nextLine();

        Rooms newRoom = new Rooms(condition, roomSize, roomType); // Assuming roomId is auto-generated
        boolean success = roomDAO.addRoom(newRoom);

        if (success) {
            System.out.println("Room successfully added.");
        } else {
            System.out.println("Failed to add the room.");
        }
    }


    private static void updateRoomBooking(RoomDAO roomDAO) {
        System.out.println("---- Updating an Existing Room Booking ----");

        // List all rooms
        List<Rooms> rooms = roomDAO.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms available to update.");
            return;
        }

        System.out.println("Available Rooms:");
        for (Rooms room : rooms) {
            System.out.println("Room ID: " + room.getRoomId() + ", Type: " + room.getRoomType() + ", Size: " + room.getRoomSize() + ", Condition: " + room.getCondition());
        }

        // Prompt for the Room ID
        System.out.print("Enter the Room ID of the room you wish to update: ");
        int roomId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Rooms selectedRoom = roomDAO.getRoomById(roomId);
        if (selectedRoom == null) {
            System.out.println("No room found with the specified ID: " + roomId);
            return;
        }

        System.out.print("Enter new room condition: ");
        String condition = scanner.nextLine();
        System.out.print("Enter new room size: ");
        int roomSize = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new room type: ");
        String roomType = scanner.nextLine();

        Rooms updatedRoom = new Rooms(roomId, condition, roomSize, roomType);
        boolean success = roomDAO.updateRoom(updatedRoom);
        if (success) {
            System.out.println("Room successfully updated.");
        } else {
            System.out.println("Failed to update the room.");
        }
    }



    private static void deleteRoomBooking(RoomDAO roomDAO) {
        System.out.println("Deleting an existing room booking.");

        System.out.print("Enter room ID to delete: ");
        int roomId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        boolean success = roomDAO.deleteRoom(roomId);

        if (success) {
            System.out.println("Room successfully deleted.");
        } else {
            System.out.println("Failed to delete the room.");
        }
    }



    private static void logEquipmentMaintenance(AdministrativeStaff admin) {
        System.out.println("---- Equipment Maintenance Log ----");


        if (admin == null) {
            System.out.println("Administrative staff verification failed. Access denied.");
            return;
        }

        MaintenanceLogDAO maintenanceLogDAO = new MaintenanceLogDAO();
        boolean running = true;
        Scanner scanner = new Scanner(System.in);

        while (running) {
            System.out.println("Choose an option:");
            System.out.println("1. View All Maintenance Logs");
            System.out.println("2. Add New Maintenance Log");
            System.out.println("3. Update Existing Maintenance Log");
            System.out.println("4. Delete Maintenance Log");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline left-over

            switch (choice) {
                case 1:
                    viewAllMaintenanceLogs(maintenanceLogDAO);
                    break;
                case 2:
                    addNewMaintenanceLog(maintenanceLogDAO, scanner);
                    break;
                case 3:
                    updateMaintenanceLog(maintenanceLogDAO, scanner);
                    break;
                case 4:
                    deleteMaintenanceLog(maintenanceLogDAO, scanner);
                    break;
                case 5:
                    System.out.println("Exiting Equipment Maintenance Log Management...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
        scanner.close();
    }




    private static void viewAllMaintenanceLogs(MaintenanceLogDAO maintenanceLogDAO) {
        List<MaintenanceLog> logs = maintenanceLogDAO.getAllMaintenanceLogs();
        if (logs.isEmpty()) {
            System.out.println("No maintenance logs found.");
        } else {
            logs.forEach(log -> System.out.println(log.toString()));
        }
    }

    private static void addNewMaintenanceLog(MaintenanceLogDAO maintenanceLogDAO, Scanner scanner) {
        System.out.print("Enter Equipment ID: ");
        int equipmentID = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter Maintenance Date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateStr);

        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        System.out.print("Enter Purchase Date (YYYY-MM-DD): ");
        String purchaseDateStr = scanner.nextLine();
        LocalDate purchaseDate = LocalDate.parse(purchaseDateStr);

        MaintenanceLog log = new MaintenanceLog(equipmentID, date, description, purchaseDate);
        boolean success = maintenanceLogDAO.addMaintenanceLog(log);

        if (success) {
            System.out.println("Maintenance log added successfully and linked to equipment.");
        } else {
            System.out.println("Failed to add or link maintenance log.");
        }
    }

    private static void updateMaintenanceLog(MaintenanceLogDAO maintenanceLogDAO, Scanner scanner) {
        System.out.print("Enter Log ID to update: ");
        int logID = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new Equipment ID: ");
        int equipmentID = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new Maintenance Date (YYYY-MM-DD): ");
        LocalDate newDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter new Description: ");
        String newDescription = scanner.nextLine();

        System.out.print("Enter new Purchase Date (YYYY-MM-DD): ");
        LocalDate newPurchaseDate = LocalDate.parse(scanner.nextLine());

        MaintenanceLog updatedLog = new MaintenanceLog(equipmentID, newDate, newDescription, newPurchaseDate);
        updatedLog.setLogID(logID);
        boolean success = maintenanceLogDAO.updateMaintenanceLog(updatedLog);

        if (success) {
            System.out.println("Maintenance log updated successfully.");
        } else {
            System.out.println("Failed to update maintenance log.");
        }
    }

    private static void deleteMaintenanceLog(MaintenanceLogDAO maintenanceLogDAO, Scanner scanner) {
        System.out.print("Enter Log ID to delete: ");
        int logID = scanner.nextInt();
        scanner.nextLine(); // consume newline

        boolean success = maintenanceLogDAO.deleteMaintenanceLog(logID);

        if (success) {
            System.out.println("Maintenance log deleted successfully.");
        } else {
            System.out.println("Failed to delete maintenance log.");
        }
    }


    private static void deleteManager(Manager currentManager) {
        System.out.println("---- Delete Manager Profile ----");

        // Confirm deletion with an extra step
        System.out.println("Are you sure you want to delete your manager profile? This action cannot be undone. Enter 'yes' to confirm:");
        String confirmation = scanner.nextLine().trim();
        if (!confirmation.equalsIgnoreCase("yes")) {
            System.out.println("Deletion cancelled.");
            return;
        }

        if (currentManager == null) {
            System.out.println("Invalid session or user data.");
            return;
        }

        // Attempt to find another manager to reassign trainers
        Manager newManager = managerDAO.findAvailableManager(currentManager.getManagerId());
        if (newManager == null) {
            System.out.println("No available manager to reassign trainers. Consider handling this case differently.");
            return;
        }

        // Update all trainers managed by the current manager to the new manager
        boolean reassignmentSuccess = trainerDAO.reassignTrainersToNewManager(currentManager.getManagerId(), newManager.getManagerId());
        if (!reassignmentSuccess) {
            System.out.println("Failed to reassign trainers to another manager.");
            return;
        }

        // Proceed with deletion
        boolean deletionSuccess = managerDAO.deleteManager(currentManager.getManagerId());
        if (deletionSuccess) {
            System.out.println("Your manager profile has been successfully deleted.");
            // Log out after deletion
            logout();
        } else {
            System.out.println("Failed to delete your manager profile.");
        }
    }







    private static void updateClassSchedule(AdministrativeStaff admin) {
        System.out.println("---- Update Class Schedule ----");

        if (admin == null) {
            System.out.println("Administrative staff verification failed. Access denied.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        // Display all classes to choose from
        List<Classes> classesList = classesDAO.listAllClasses();
        if (classesList.isEmpty()) {
            System.out.println("No classes available to update.");
            return;
        }

        System.out.println("Select a class to update:");
        for (int i = 0; i < classesList.size(); i++) {
            Classes cls = classesList.get(i);
            System.out.println((i + 1) + ". " + cls.getClassType() + " at " + cls.getTime() + " on " + cls.getDate() + " - ID: " + cls.getClassId());
        }
        System.out.print("Enter class number: ");
        int classChoice = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline

        if (classChoice < 0 || classChoice >= classesList.size()) {
            System.out.println("Invalid class selection.");
            return;
        }

        Classes selectedClass = classesList.get(classChoice);

        // Get new details for the class
        System.out.println("Updating class: " + selectedClass.getClassType());
        System.out.print("Enter new cost: ");
        selectedClass.setCost(scanner.nextDouble());
        scanner.nextLine(); // consume newline

        System.out.print("Enter new time (HH:MM): ");
        selectedClass.setTime(LocalTime.parse(scanner.nextLine()));

        System.out.print("Enter new date (YYYY-MM-DD): ");
        selectedClass.setDate(LocalDate.parse(scanner.nextLine()));

        System.out.print("Enter new class size: ");
        selectedClass.setClassSize(scanner.nextInt());
        scanner.nextLine(); // consume newline

        System.out.print("Enter new trainer ID: ");
        selectedClass.setTrainerId(scanner.nextInt());
        scanner.nextLine(); // consume newline

        // Update the class using ClassesDAO
        boolean success = classesDAO.updateClasses(selectedClass);
        if (success) {
            System.out.println("Class updated successfully.");
        } else {
            System.out.println("Failed to update class. Please check for conflicts or database issues.");
        }
    }
    private static int processPayment(AdministrativeStaff admin) {
        System.out.println("---- Process Payment ----");

        if (admin == null) {
            System.out.println("Verification failed. Access denied.");
            return -1;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter payment description (e.g., 'Class Booking', 'Membership Fee'): ");
        String paymentFor = scanner.nextLine();

        System.out.print("Enter payment amount (e.g., 100.00): ");
        BigDecimal amount = scanner.nextBigDecimal();
        scanner.nextLine(); // consume the leftover newline

        System.out.print("Enter payment method (e.g., 'Credit Card', 'PayPal'): ");
        String paymentMethod = scanner.nextLine();

        LocalDate paymentDate = LocalDate.now(); // Automatically set the payment date to the current date

        Payment payment = new Payment(paymentFor, paymentDate, amount, paymentMethod, "Pending");
        PaymentDAO paymentDAO = new PaymentDAO();
        int paymentId = paymentDAO.addPayment(payment);
        if (paymentId != -1) {
            System.out.println("Payment processed successfully. Payment ID: " + paymentId);
        } else {
            System.out.println("Failed to process payment.");
        }
        return paymentId;
    }


    private static int selectMembership() {
        System.out.println("Please select a membership plan:");
        List<Membership> memberships = MembershipDAO.listMemberships();
        if (memberships.isEmpty()) {
            System.out.println("No membership plans available at the moment.");
            return -1; // Indicative of no available memberships to choose from
        }

        for (int i = 0; i < memberships.size(); i++) {
            Membership m = memberships.get(i);
            System.out.println((i + 1) + ". Duration: " + m.getDuration() + " months, Rate: $" + m.getRate() + ", Description: " + m.getDescription());
        }
        System.out.print("Select a plan (1-" + memberships.size() + "): ");
        int membershipChoice = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume the newline character

        if (membershipChoice >= 0 && membershipChoice < memberships.size()) {
            return memberships.get(membershipChoice).getMembershipId();
        } else {
            System.out.println("Invalid membership selection. Please select a valid plan.");
            return selectMembership(); // Recursively prompt again for a valid selection
        }
    }


    private static Member verifyMember() {
        System.out.print("Enter Member ID: ");
        int memberId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter your first name for verification: ");
        String firstNameForVerification = scanner.nextLine();

        System.out.print("Enter password for verification: ");
        String passwordForVerification = scanner.nextLine();

        Member member = memberDAO.getMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found.");
            return null;
        }

        // Verify the first name and password
        if (!member.getFirstName().equalsIgnoreCase(firstNameForVerification) ||
                !member.getPassword().equals(passwordForVerification)) { // Assuming password is plaintext; hash in a real application
            System.out.println("Verification failed. Please check your credentials.");
            return null;
        }

        return member;
    }

    private static Trainer verifyTrainer() {
        System.out.print("Enter Trainer ID: ");
        int trainerId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter your first name for verification: ");
        String firstNameForVerification = scanner.nextLine();

        System.out.print("Enter password for verification: ");
        String passwordForVerification = scanner.nextLine();

        Trainer trainer = trainerDAO.getTrainerById(trainerId);
        if (trainer == null || !trainer.getFirstName().equalsIgnoreCase(firstNameForVerification)
                || !trainer.getPassword().equals(passwordForVerification)) {
            System.out.println("Verification failed. Please check your credentials.");
            return null;
        }

        return trainer;
    }

    private static AdministrativeStaff verifyAdministrativeStaff() {
        System.out.print("Enter Staff ID: ");
        int staffId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter your first name for verification: ");
        String firstNameForVerification = scanner.nextLine();

        System.out.print("Enter password for verification: ");
        String passwordForVerification = scanner.nextLine();


        AdministrativeStaff staff = administrativeStaffDAO.getStaffById(staffId);
        if (staff == null || !staff.getFirstName().equalsIgnoreCase(firstNameForVerification)
                || !staff.getPassword().equals(passwordForVerification)) {
            System.out.println("Verification failed. Please check your credentials.");
            return null;
        }

        return staff;
    }


    private static Manager verifyManager() {
        System.out.print("Enter Manager ID: ");
        int managerId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter your first name for verification: ");
        String firstNameForVerification = scanner.nextLine();

        System.out.print("Enter password for verification: ");
        String passwordForVerification = scanner.nextLine();

        // Assuming you have a ManagerDAO with a method getManagerById similar to AdministrativeStaffDAO.getStaffById
        Manager manager = managerDAO.getManagerById(managerId);
        if (manager == null || !manager.getFirstName().equalsIgnoreCase(firstNameForVerification)
                || !manager.getPassword().equals(passwordForVerification)) {
            System.out.println("Verification failed. Please check your credentials.");
            return null;
        }

        return manager;
    }





}
