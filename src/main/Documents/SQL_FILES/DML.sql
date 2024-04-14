


-- Insert into Manager tabel
INSERT INTO Manager (FirstName, LastName, Password) VALUES ('John', 'Doe', 'pass123');
INSERT INTO Manager (FirstName, LastName, Password) VALUES ('Jane', 'Smith', 'pass456');

-- Inserts
INSERT INTO TrainerAvailability (TrainerID, AvailableDate, StartTime, EndTime) VALUES (1, '2024-04-14', '09:00', '12:00');
INSERT INTO TrainerAvailability (TrainerID, AvailableDate, StartTime, EndTime) VALUES (2, '2024-04-15', '10:00', '13:00');

-- Inserts
INSERT INTO Membership (MembershipDuration, MembershipRate, MembershipStartDate, MembershipEndDate) VALUES (12, 99.99, '2024-01-01', '2024-12-31');
INSERT INTO Membership (MembershipDuration, MembershipRate, MembershipStartDate, MembershipEndDate) VALUES (6, 59.99, '2024-01-01', '2024-06-30');

-- Inserts
INSERT INTO Rooms (Condition, RoomSize, RoomType) VALUES ('Good', 500, 'Yoga Studio');
INSERT INTO Rooms (Condition, RoomSize, RoomType) VALUES ('Excellent', 700, 'Gym');


-- Inserts
INSERT INTO Member (FirstName, LastName, Age, Email, Password, City, StreetNo, StreetName, MembershipID, FitnessGoalType, FitnessGoalTarget, TrainerID) VALUES ('Alice', 'Wonderland', 30, 'alice@example.com', 'secure123', 'Wonder City', 101, 'Mystic Ave', 1, 'Weight Loss', 'Lose 10 kg', 1);
INSERT INTO Member (FirstName, LastName, Age, Email, Password, City, StreetNo, StreetName, MembershipID, FitnessGoalType, FitnessGoalTarget, TrainerID) VALUES ('Bob', 'Builder', 25, 'bob@example.com', 'build123', 'Construct Town', 202, 'Builder Blvd', 2, 'Muscle Gain', 'Gain 5 kg muscle', 2);

-- Inserts
INSERT INTO PhoneMember (MemberID, PhoneNo) VALUES (1, '123-456-7890');
INSERT INTO PhoneMember (MemberID, PhoneNo) VALUES (2, '098-765-4321');

-- Inserts
INSERT INTO Equipment (Description, Status, RoomID) VALUES ('Treadmill', 'Operational', 1);
INSERT INTO Equipment (Description, Status, RoomID) VALUES ('Elliptical', 'Needs Repair', 2);

-- Inserts
INSERT INTO Workout (WorkoutType) VALUES ('Cardio');
INSERT INTO Workout (WorkoutType) VALUES ('Strength Training');

-- Inserts
INSERT INTO MemberWorkout (MemberID, WorkoutID) VALUES (1, 1);
INSERT INTO MemberWorkout (MemberID, WorkoutID) VALUES (2, 2);

-- Inserts
INSERT INTO Trainer (FirstName, LastName, Password, Age, City, StreetNo, StreetName, ManagerID) VALUES ('Trainer', 'One', 'pass789', 35, 'Fitness City', 303, 'Gym Street', 1);
INSERT INTO Trainer (FirstName, LastName, Password, Age, City, StreetNo, StreetName, ManagerID) VALUES ('Trainer', 'Two', 'pass101', 28, 'Health City', 404, 'Fitness Street', 2);

-- Inserts
INSERT INTO TrainerWorkout (TrainerID, WorkoutID) VALUES (1, 1);
INSERT INTO TrainerWorkout (TrainerID, WorkoutID) VALUES (2, 2);

-- Inserts
INSERT INTO PhoneTrainer (TrainerID, PhoneNo) VALUES (1, '222-333-4444');
INSERT INTO PhoneTrainer (TrainerID, PhoneNo) VALUES (2, '555-666-7777');

-- Inserts
INSERT INTO MaintenanceLog (MaintenanceDate, Description, PurchaseDate) VALUES ('2024-04-15', 'Annual check-up', '2023-04-15');
INSERT INTO MaintenanceLog (MaintenanceDate, Description, PurchaseDate) VALUES ('2024-05-01', 'Replace parts', '2023-05-01');




-- Insert into MaintenanceLog_Equipment
INSERT INTO MaintenanceLog_Equipment (EquipmentID, LogID) VALUES (1, 1);
INSERT INTO MaintenanceLog_Equipment (EquipmentID, LogID) VALUES (2, 2);

-- Insert into AdministrativeStaff
INSERT INTO AdministrativeStaff (FirstName, LastName, Email, Password, Role, MobilePhone, HireDate, ManagerID) VALUES ('Admin', 'One', 'admin1@example.com', 'adminpass1', 'Manager', '900-800-7000', '2024-01-01', 1);
INSERT INTO AdministrativeStaff (FirstName, LastName, Email, Password, Role, MobilePhone, HireDate, ManagerID) VALUES ('Admin', 'Two', 'admin2@example.com', 'adminpass2', 'Reception', '800-700-6000', '2024-01-02', 2);

-- Insert into Administrative_Maintenance
INSERT INTO Administrative_Maintenance (StaffID, LogID) VALUES (1, 1);
INSERT INTO Administrative_Maintenance (StaffID, LogID) VALUES (2, 2);

-- Insert into Nutrition
INSERT INTO Nutrition (NutritionType) VALUES ('Low Carb');
INSERT INTO Nutrition (NutritionType) VALUES ('High Protein');

-- Insert into TrainerNutrition
INSERT INTO TrainerNutrition (TrainerID, NutritionID) VALUES (1, 1);
INSERT INTO TrainerNutrition (TrainerID, NutritionID) VALUES (2, 2);

-- Insert into MemberNutrition
INSERT INTO MemberNutrition (MemberID, NutritionID) VALUES (1, 1);
INSERT INTO MemberNutrition (MemberID, NutritionID) VALUES (2, 2);

-- Insert into Classes
INSERT INTO Classes (Cost, Time, Date, ClassType, ClassSize, TrainerID) VALUES (10.00, '09:00:00', '2024-04-20', 'Yoga', 15, 1);
INSERT INTO Classes (Cost, Time, Date, ClassType, ClassSize, TrainerID) VALUES (15.00, '10:00:00', '2024-04-21', 'Pilates', 10, 2);

-- Insert into ClassesMembers
INSERT INTO ClassesMembers (ClassID, MemberID, AttendaceDate) VALUES (1, 1, '2024-04-20');
INSERT INTO ClassesMembers (ClassID, MemberID, AttendaceDate) VALUES (2, 2, '2024-04-21');

-- Insert into Booking
INSERT INTO Booking (BookingDate, StartTime, EndTime, Status, MemberID, ClassID, RoomID) VALUES ('2024-04-15', '09:00', '11:00', 'Confirmed', 1, 1, 1);
INSERT INTO Booking (BookingDate, StartTime, EndTime, Status, MemberID, ClassID, RoomID) VALUES ('2024-04-16', '10:00', '12:00', 'Confirmed', 2, 2, 2);

-- Insert into Payment
INSERT INTO Payment (PaymentFor, PaymentDate, Amount, PaymentMethod, PaymentStatus) VALUES ('Membership Fee', '2024-04-15', 100.00, 'Credit Card', 'Completed');
INSERT INTO Payment (PaymentFor, PaymentDate, Amount, PaymentMethod, PaymentStatus) VALUES ('Class Fee', '2024-04-16', 15.00, 'PayPal', 'Pending');

-- Insert into PaymentMember
INSERT INTO PaymentMember (PaymentID, MemberID) VALUES (1, 1);
INSERT INTO PaymentMember (PaymentID, MemberID) VALUES (2, 2);

-- Insert into Progress
INSERT INTO Progress (MemberID, Date, Weight, BodyFatPercentage, PersonalRecords, ClassAttendance, Notes) VALUES (1, '2024-04-15', 70.0, 15.0, 'Bench Press: 100kg', 5, 'Doing great!');
INSERT INTO Progress (MemberID, Date, Weight, BodyFatPercentage, PersonalRecords, ClassAttendance, Notes) VALUES (2, '2024-04-16', 80.0, 10.0, 'Squats: 140kg', 3, 'Needs improvement.');



--Update statemtnts

-- Update
UPDATE Manager SET FirstName = 'John Updated' WHERE ManagerID = 1;
UPDATE Manager SET LastName = 'Smith Updated' WHERE ManagerID = 2;

-- Update
UPDATE TrainerAvailability SET AvailableDate = '2024-04-16' WHERE AvailabilityID = 1;
UPDATE TrainerAvailability SET StartTime = '08:00' WHERE AvailabilityID = 2;

-- Update
UPDATE Membership SET MembershipRate = 109.99 WHERE MembershipID = 1;
UPDATE Membership SET MembershipDuration = 3 WHERE MembershipID = 2;

-- Update
UPDATE Rooms SET Condition = 'Excellent' WHERE RoomID = 1;
UPDATE Rooms SET RoomType = 'Spinning Studio' WHERE RoomID = 2;

-- Update
UPDATE Member SET Email = 'alice_updated@example.com' WHERE MemberID = 1;
UPDATE Member SET City = 'Updated City' WHERE MemberID = 2;

-- Update
UPDATE PhoneMember SET PhoneNo = '111-222-3333' WHERE MemberID = 1 AND PhoneNo = '123-456-7890';
UPDATE PhoneMember SET PhoneNo = '444-555-6666' WHERE MemberID = 2 AND PhoneNo = '098-765-4321';

-- Update
UPDATE Equipment SET Status = 'Under Maintenance' WHERE EquipmentID = 1;
UPDATE Equipment SET Description = 'Updated Elliptical' WHERE EquipmentID = 2;

-- Update
UPDATE Workout SET WorkoutType = 'HIIT' WHERE WorkoutID = 1;
UPDATE Workout SET WorkoutType = 'Yoga' WHERE WorkoutID = 2;

-- Update
UPDATE MemberWorkout SET WorkoutID = 2 WHERE MemberID = 1 AND WorkoutID = 1;
UPDATE MemberWorkout SET MemberID = 1 WHERE MemberID = 2 AND WorkoutID = 2;

-- Update
UPDATE Trainer SET Age = 36 WHERE TrainerID = 1;
UPDATE Trainer SET City = 'New Fitness City' WHERE TrainerID = 2;

-- Update
UPDATE TrainerWorkout SET WorkoutID = 2 WHERE TrainerID = 1;
UPDATE TrainerWorkout SET TrainerID = 1 WHERE TrainerID = 2;

-- Update
UPDATE PhoneTrainer SET PhoneNo = '888-999-0000' WHERE TrainerID = 1 AND PhoneNo = '222-333-4444';
UPDATE PhoneTrainer SET PhoneNo = '111-222-3333' WHERE TrainerID = 2 AND PhoneNo = '555-666-7777';

-- Update
UPDATE MaintenanceLog SET Description = 'Bi-annual check-up' WHERE LogID = 1;
UPDATE MaintenanceLog SET PurchaseDate = '2023-06-01' WHERE LogID = 2;









-- Update MaintenanceLog_Equipment
UPDATE MaintenanceLog_Equipment SET LogID = 2 WHERE EquipmentID = 1;
UPDATE MaintenanceLog_Equipment SET EquipmentID = 1 WHERE EquipmentID = 2;

-- Update AdministrativeStaff
UPDATE AdministrativeStaff SET Email = 'updated_admin1@example.com' WHERE StaffID = 1;
UPDATE AdministrativeStaff SET Role = 'Lead' WHERE StaffID = 2;

-- Update Administrative_Maintenance
UPDATE Administrative_Maintenance SET LogID = 2 WHERE StaffID = 1;
UPDATE Administrative_Maintenance SET StaffID = 2 WHERE StaffID = 2;

-- Update Nutrition
UPDATE Nutrition SET NutritionType = 'Keto' WHERE NutritionID = 1;
UPDATE Nutrition SET NutritionType = 'Vegan' WHERE NutritionID = 2;

-- Update TrainerNutrition
UPDATE TrainerNutrition SET NutritionID = 2 WHERE TrainerID = 1;
UPDATE TrainerNutrition SET TrainerID = 1 WHERE TrainerID = 2;

-- Update MemberNutrition
UPDATE MemberNutrition SET NutritionID = 2 WHERE MemberID = 1;
UPDATE MemberNutrition SET MemberID = 1 WHERE MemberID = 2;

-- Update Classes
UPDATE Classes SET ClassSize = 20 WHERE ClassID = 1;
UPDATE Classes SET Time = '08:00:00' WHERE ClassID = 2;

-- Update ClassesMembers
UPDATE ClassesMembers SET AttendaceDate = '2024-05-01' WHERE ClassID = 1 AND MemberID = 1;
UPDATE ClassesMembers SET MemberID = 1 WHERE ClassID = 2 AND MemberID = 2;

-- Update Booking
UPDATE Booking SET Status = 'Cancelled' WHERE BookingID = 1;
UPDATE Booking SET StartTime = '11:00' WHERE BookingID = 2;

-- Update Payment
UPDATE Payment SET PaymentStatus = 'Refunded' WHERE PaymentID = 1;
UPDATE Payment SET Amount = 10.00 WHERE PaymentID = 2;

-- Update PaymentMember
UPDATE PaymentMember SET MemberID = 2 WHERE PaymentID = 1 AND MemberID = 1;
UPDATE PaymentMember SET PaymentID = 1 WHERE PaymentID = 2 AND MemberID = 2;

-- Update Progress
UPDATE Progress SET Notes = 'Improved Bench Press' WHERE ProgressID = 1;
UPDATE Progress SET Weight = 75.0 WHERE ProgressID = 2;





--Delet statemtn


-- Delete from Progress
DELETE FROM Progress WHERE ProgressID = 1;
DELETE FROM Progress WHERE ProgressID = 2;

-- Delete from PaymentMember
DELETE FROM PaymentMember WHERE PaymentID = 1 AND MemberID = 1;
DELETE FROM PaymentMember WHERE PaymentID = 2 AND MemberID = 2;

-- Delete from Payment
DELETE FROM Payment WHERE PaymentID = 1;
DELETE FROM Payment WHERE PaymentID = 2;

-- Delete from Booking
DELETE FROM Booking WHERE BookingID = 1;
DELETE FROM Booking WHERE BookingID = 2;

-- Delete from ClassesMembers
DELETE FROM ClassesMembers WHERE ClassID = 1 AND MemberID = 1;
DELETE FROM ClassesMembers WHERE ClassID = 2 AND MemberID = 2;

-- Delete from Classes
DELETE FROM Classes WHERE ClassID = 1;
DELETE FROM Classes WHERE ClassID = 2;

-- Delete from MemberNutrition
DELETE FROM MemberNutrition WHERE MemberID = 1 AND NutritionID = 1;
DELETE FROM MemberNutrition WHERE MemberID = 2 AND NutritionID = 2;


-- Delete from TrainerNutrition
DELETE FROM TrainerNutrition WHERE TrainerID = 1 AND NutritionID = 1;
DELETE FROM TrainerNutrition WHERE TrainerID = 2 AND NutritionID = 2;

-- Delete from Nutrition
DELETE FROM Nutrition WHERE NutritionID = 1;
DELETE FROM Nutrition WHERE NutritionID = 2;

-- Delete from Administrative_Maintenance
DELETE FROM Administrative_Maintenance WHERE StaffID = 1 AND LogID = 1;
DELETE FROM Administrative_Maintenance WHERE StaffID = 2 AND LogID = 2;


-- Delete from AdministrativeStaff
DELETE FROM AdministrativeStaff WHERE StaffID = 1;
DELETE FROM AdministrativeStaff WHERE StaffID = 2;

-- Delete from MaintenanceLog_Equipment
DELETE FROM MaintenanceLog_Equipment WHERE EquipmentID = 1 AND LogID = 1;
DELETE FROM MaintenanceLog_Equipment WHERE EquipmentID = 2 AND LogID = 2;


-- Delete from MaintenanceLog
DELETE FROM MaintenanceLog WHERE LogID = 1;
DELETE FROM MaintenanceLog WHERE LogID = 2;

-- Delete from PhoneTrainer
DELETE FROM PhoneTrainer WHERE TrainerID = 1;
DELETE FROM PhoneTrainer WHERE TrainerID = 2;


-- Delete from TrainerWorkout
DELETE FROM TrainerWorkout WHERE TrainerID = 1 AND WorkoutID = 1;
DELETE FROM TrainerWorkout WHERE TrainerID = 2 AND WorkoutID = 2;

-- Delete from Trainer
DELETE FROM Trainer WHERE TrainerID = 1;
DELETE FROM Trainer WHERE TrainerID = 2;

-- Delete from MemberWorkout
DELETE FROM MemberWorkout WHERE MemberID = 1 AND WorkoutID = 1;
DELETE FROM MemberWorkout WHERE MemberID = 2 AND WorkoutID = 2;


-- Delete from Workout
DELETE FROM Workout WHERE WorkoutID = 1;
DELETE FROM Workout WHERE WorkoutID = 2;

-- Delete from Equipment
DELETE FROM Equipment WHERE EquipmentID = 1;
DELETE FROM Equipment WHERE EquipmentID = 2;

-- Delete from PhoneMember
DELETE FROM PhoneMember WHERE MemberID = 1;
DELETE FROM PhoneMember WHERE MemberID = 2;


-- Delete from Member
DELETE FROM Member WHERE MemberID = 1;
DELETE FROM Member WHERE MemberID = 2;

-- Delete from Rooms
DELETE FROM Rooms WHERE RoomID = 1;
DELETE FROM Rooms WHERE RoomID = 2;

-- Delete from Membership
DELETE FROM Membership WHERE MembershipID = 1;
DELETE FROM Membership WHERE MembershipID = 2;

-- Delete from TrainerAvailability
DELETE FROM TrainerAvailability WHERE AvailabilityID = 1;
DELETE FROM TrainerAvailability WHERE AvailabilityID = 2;

-- Delete from Manager
DELETE FROM Manager WHERE ManagerID = 1;
DELETE FROM Manager WHERE ManagerID = 2;
