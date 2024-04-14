-- Manager Table
CREATE TABLE IF NOT EXISTS Manager (
    ManagerID serial PRIMARY KEY,
    FirstName varchar(255) NOT NULL,
    LastName varchar(255) NOT NULL,
    Password varchar(255) NOT NULL
);

-- Membership Table
CREATE TABLE IF NOT EXISTS Membership (
    MembershipID SERIAL PRIMARY KEY,
    MembershipDuration INT NOT NULL,
    MembershipRate DECIMAL(10, 2) NOT NULL
);

-- Rooms Table
CREATE TABLE IF NOT EXISTS Rooms (
    RoomID SERIAL PRIMARY KEY,
    Condition VARCHAR(255) NOT NULL,
    RoomSize INT NOT NULL,
    RoomType VARCHAR(255) NOT NULL
);

-- Equipment Table
CREATE TABLE IF NOT EXISTS Equipment (
    EquipmentID serial PRIMARY KEY,
    Description varchar(255) NOT NULL,
    Status varchar(255) NOT NULL,
    RoomID int NOT NULL,
    FOREIGN KEY (RoomID) REFERENCES Rooms(RoomID)
);

--maintenancelog table
CREATE TABLE IF NOT EXISTS MaintenanceLog (
    LogID serial PRIMARY KEY,
    MaintenanceDate date NOT NULL,
    Description varchar(255) NOT NULL,
    PurchaseDate date NOT NULL
);


CREATE TABLE IF NOT EXISTS MaintenanceLog_Equipment (
    EquipmentID int NOT NULL,
    LogID int NOT NULL,
    PRIMARY KEY (EquipmentID, LogID),
    FOREIGN KEY (EquipmentID) REFERENCES Equipment(EquipmentID),
    FOREIGN KEY (LogID) REFERENCES MaintenanceLog(LogID) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS AdministrativeStaff (
    StaffID serial PRIMARY KEY,
    FirstName varchar(255) NOT NULL,
    LastName varchar(255) NOT NULL,
    Email varchar(255) NOT NULL,
    Password varchar(255) NOT NULL,
    Role varchar(255) NOT NULL,
    MobilePhone varchar(20) NOT NULL,
    HireDate date NOT NULL,
    ManagerID int NOT NULL,
    FOREIGN KEY (ManagerID) REFERENCES Manager(ManagerID)
);
CREATE TABLE IF NOT EXISTS Administrative_Maintenance (
    StaffID int NOT NULL,
    LogID int NOT NULL,
    FOREIGN KEY (StaffID) REFERENCES AdministrativeStaff(StaffID),
    FOREIGN KEY (LogID) REFERENCES MaintenanceLog(LogID),
    PRIMARY KEY (StaffID, LogID)
);

-- Membership Subscriptions Table
CREATE TABLE IF NOT EXISTS MembershipSubscriptions (
    MembershipSubscriptionID SERIAL PRIMARY KEY,
    MembershipID INT NOT NULL,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    FOREIGN KEY (MembershipID) REFERENCES Membership(MembershipID)
);

-- Member Table
CREATE TABLE IF NOT EXISTS Member (
    MemberID SERIAL PRIMARY KEY,
    FirstName VARCHAR(255),
    LastName VARCHAR(255),
    Age INT,
    Email VARCHAR(255) UNIQUE NOT NULL,
    Password VARCHAR(255),
    City VARCHAR(255),
    StreetNo INT,
    StreetName VARCHAR(255),
    MembershipSubscriptionID INT,
    FitnessGoalType VARCHAR(255),
    FitnessGoalTarget VARCHAR(255)
    -- TrainerID is added after table creation
);

CREATE TABLE IF NOT EXISTS PhoneMember (
    MemberID int NOT NULL,
    PhoneNo VARCHAR(255) NOT NULL,
    PRIMARY KEY (MemberID, PhoneNo),
    FOREIGN KEY (MemberID) REFERENCES Member(MemberID)
);


-- Workout Table
CREATE TABLE IF NOT EXISTS Workout (
    WorkoutID SERIAL PRIMARY KEY,
    WorkoutType VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS MemberWorkout (
    MemberID INT NOT NULL,
    WorkoutID INT NOT NULL,
    PRIMARY KEY (MemberID, WorkoutID),
    FOREIGN KEY (MemberID) REFERENCES Member(MemberID),
    FOREIGN KEY (WorkoutID) REFERENCES Workout(WorkoutID)
);


-- Trainer Table
CREATE TABLE IF NOT EXISTS Trainer (
    TrainerID serial PRIMARY KEY,
    FirstName varchar(255) NOT NULL,
    LastName varchar(255) NOT NULL,
    Password varchar(255) NOT NULL,
    Age int NOT NULL,
    City varchar(255) NOT NULL,
    StreetNo varchar(255) NOT NULL,
    StreetName varchar(255) NOT NULL,
    ManagerID int NOT NULL,
    FOREIGN KEY (ManagerID) REFERENCES Manager(ManagerID)
);

CREATE TABLE IF NOT EXISTS TrainerWorkout (
    TrainerID int NOT NULL,
    WorkoutID int NOT NULL,
    FOREIGN KEY (TrainerID) REFERENCES Trainer(TrainerID),
    FOREIGN KEY (WorkoutID) REFERENCES Workout(WorkoutID),
    PRIMARY KEY (TrainerID, WorkoutID)
);


CREATE TABLE IF NOT EXISTS PhoneTrainer (
    TrainerID int NOT NULL,
    PhoneNo VARCHAR(255) NOT NULL,
    PRIMARY KEY (TrainerID, PhoneNo),
    FOREIGN KEY (TrainerID) REFERENCES Trainer(TrainerID)
);


-- Trainer Availability Table
CREATE TABLE IF NOT EXISTS TrainerAvailability (
    AvailabilityID serial PRIMARY KEY,
    TrainerID int NOT NULL,
    AvailableDate date NOT NULL,
    StartTime time NOT NULL,
    EndTime time NOT NULL,
    FOREIGN KEY (TrainerID) REFERENCES Trainer(TrainerID)
);

-- Nutrition Table
CREATE TABLE IF NOT EXISTS Nutrition (
    NutritionID serial PRIMARY KEY,
    NutritionType varchar(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS TrainerNutrition (
    TrainerID int NOT NULL,
    NutritionID int NOT NULL,
    PRIMARY KEY (TrainerID, NutritionID),
    FOREIGN KEY (TrainerID) REFERENCES Trainer(TrainerID),
    FOREIGN KEY (NutritionID) REFERENCES Nutrition(NutritionID)
);

CREATE TABLE IF NOT EXISTS MemberNutrition (
    MemberID int NOT NULL,
    NutritionID int NOT NULL,
    PRIMARY KEY (MemberID, NutritionID),
    FOREIGN KEY (MemberID) REFERENCES Member(MemberID),
    FOREIGN KEY (NutritionID) REFERENCES Nutrition(NutritionID)
);


CREATE TABLE IF NOT EXISTS Classes (
    ClassID serial PRIMARY KEY,
    Cost decimal(10,2) NOT NULL,
    Time time NOT NULL,
    Date date NOT NULL,
    ClassType varchar(255) NOT NULL,
    ClassSize int NOT NULL,
    TrainerID int NOT NULL,
    FOREIGN KEY (TrainerID) REFERENCES Trainer(TrainerID)
);


CREATE TABLE IF NOT EXISTS ClassesMembers (
    ClassID int NOT NULL,
    MemberID int NOT NULL,
    AttendaceDate date NOT NULL,
    PRIMARY KEY (ClassID, MemberID),
    FOREIGN KEY (ClassID) REFERENCES Classes(ClassID),
    FOREIGN KEY (MemberID) REFERENCES Member(MemberID)
);


CREATE TABLE IF NOT EXISTS Booking (
    BookingID SERIAL PRIMARY KEY,
    BookingDate DATE NOT NULL,
    StartTime TIME NOT NULL,
    EndTime TIME NOT NULL,
    Status VARCHAR(255) NOT NULL,
    MemberID INT NOT NULL,
    ClassID INT NOT NULL,
    RoomID INT NOT NULL,
    FOREIGN KEY (MemberID) REFERENCES Member (MemberID),
    FOREIGN KEY (ClassID) REFERENCES Classes (ClassID),
    FOREIGN KEY (RoomID) REFERENCES Rooms (RoomID)
);




CREATE TABLE IF NOT EXISTS Payment (
    PaymentID serial PRIMARY KEY,
    PaymentFor varchar(255) NOT NULL,
    PaymentDate date NOT NULL,
    Amount decimal(10,2) NOT NULL,
    PaymentMethod varchar(255) NOT NULL,
    PaymentStatus varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS PaymentMember (
    PaymentID int NOT NULL,
    MemberID int NOT NULL,
    FOREIGN KEY (PaymentID) REFERENCES Payment(PaymentID),
    FOREIGN KEY (MemberID) REFERENCES Member(MemberID),
    PRIMARY KEY (PaymentID, MemberID)
);

CREATE TABLE IF NOT EXISTS Progress (
    ProgressID SERIAL PRIMARY KEY,
    MemberID INT NOT NULL,
    Date DATE NOT NULL,
    Weight DECIMAL(10, 2) NOT NULL,
    BodyFatPercentage DECIMAL(10, 2) NOT NULL,
    PersonalRecords TEXT,
    ClassAttendance INT,
    Notes TEXT,
    FOREIGN KEY (MemberID) REFERENCES Member(MemberID)
);

-- Alter Member Table to Add TrainerID
ALTER TABLE Member ADD COLUMN TrainerID INT;
ALTER TABLE Member ADD CONSTRAINT fk_trainer FOREIGN KEY (TrainerID) REFERENCES Trainer(TrainerID);

