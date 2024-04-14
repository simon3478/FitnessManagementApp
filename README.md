

**FitnessApp** is a comprehensive fitness management system designed to help users maintain and track their fitness journey. 

## Features

- **Member Management**: Register as a member, manage profiles, and keep track of fitness goals.
- **Membership Subscriptions**: Purchase and renew membership plans with varying durations and benefits.
- **Class Scheduling**: Book fitness classes online. View available slots and reserve your spot in classes that fit your schedule.
- **Trainer Sessions**: Book sessions with certified trainers to get personalized training according to your fitness goals.
- **Progress Tracking**: Track your fitness progress with detailed reports on your workouts, weight, and body measurements over time.
- **Nutritional Guidance**: Access nutritional plans tailored to your fitness goals and dietary preferences.
- **Equipment Management**: View available gym equipment and their maintenance schedules.

## Getting Started

### Prerequisites

- Java 11 or higher
- PostgreSQL 12 or higher
- Apache Maven 3.6 or higher (for building the project)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourgithub/fitnessapp.git
   cd fitnessapp
   ```
2. Set up the PostgreSQL database:
   - Create a new database named `fitnessdb`.
   - Import the provided SQL schema file to set up the tables.
   ```bash
   psql -U username -d fitnessdb -f setup.sql
   ```

3. Configure your database connection:
   - Modify the `DBConnection.java` file to include your database credentials.

4. Build the project:
   ```bash
   mvn clean install
   ```

5. Run the application:
   ```bash
   java -jar target/fitnessapp-1.0.jar
   ```

## Usage

After launching the app, you will be prompted to log in or register. Follow the on-screen instructions to navigate through the different features.





