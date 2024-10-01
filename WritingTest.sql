DROP DATABASE IF EXISTS WritingTest;
CREATE DATABASE WritingTest;
USE WritingTest;

-- Table for users
CREATE TABLE User (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,  -- User's email address
    password_hash VARCHAR(255) NOT NULL,  -- For storing hashed passwords
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table for daily goals
CREATE TABLE DailyGoal (
    goal_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    category ENUM('product_time', 'process_time', 'self_care_time') NOT NULL,
    goal_value INT,  -- Goal value in minutes
    day_of_week ENUM('Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'),
    goal_time TIME,  -- Optional time of day to complete the goal
    status BOOLEAN NOT NULL DEFAULT 1,  -- Tracks if the goal is active (1) or inactive (0)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);

-- Table for daily activities
CREATE TABLE DailyActivity (
    activity_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    category ENUM('product_time', 'process_time', 'self_care_time') NOT NULL,
    name VARCHAR(100) NOT NULL,  -- Name of the activity
    activity_description VARCHAR(255),  -- Optional description of the activity
    duration INT,  -- Duration in minutes
    date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);

-- Table for process and self-care activities
CREATE TABLE GrabBagActivity (
    activity_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    category ENUM('product_time', 'process_time', 'self_care_time') NOT NULL,
    name VARCHAR(100) NOT NULL,  -- Name of the activity
    activity_description VARCHAR(255),  -- Optional description of the activity
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);

-- Table for rewards
CREATE TABLE Reward (
    reward_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    reward_type ENUM('small', 'medium', 'big') NOT NULL,
    name VARCHAR(100) NOT NULL, -- Name of the reward
    reward_description VARCHAR(255),  -- Description of the reward
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);

DELIMITER $$

DROP PROCEDURE IF EXISTS reset_database_state $$

-- Create procedure to reset the database to a known good state with sample data
CREATE PROCEDURE reset_database_state()
BEGIN
    -- Disable foreign key checks to avoid constraint issues
    SET FOREIGN_KEY_CHECKS = 0;

    -- Truncate all tables
    TRUNCATE TABLE DailyActivity;
    TRUNCATE TABLE GrabBagActivity;
    TRUNCATE TABLE Reward;
    TRUNCATE TABLE DailyGoal;
    TRUNCATE TABLE User;

    -- Re-enable foreign key checks
    SET FOREIGN_KEY_CHECKS = 1;

    -- Insert sample data
    -- Insert test users
    INSERT INTO User (username, email, password_hash) VALUES 
        ('testuser1', 'testuser1@example.com', 'hashed_password_1'),
        ('testuser2', 'testuser2@example.com', 'hashed_password_2'),
        ('testuser3', 'testuser3@example.com', 'hashed_password_3');

    -- Insert daily goals for the first user
    INSERT INTO DailyGoal (user_id, category, goal_value, day_of_week, goal_time, status) VALUES 
        (1, 'product_time', 120, 'Monday', '09:00:00', 1),
        (1, 'process_time', 60, 'Tuesday', '10:30:00', 1),
        (1, 'self_care_time', 30, 'Wednesday', NULL, 1),
        (1, 'product_time', 90, 'Thursday', '08:00:00', 1),
        (1, 'self_care_time', 45, 'Friday', '17:00:00', 1);

    -- Insert daily activities for the first user
    INSERT INTO DailyActivity (user_id, category, name, activity_description, duration, date) VALUES 
        (1, 'product_time', 'Write Chapter 1', 'Drafting the first chapter of the book.', 90, CURDATE()),
        (1, 'process_time', 'Sketch Ideas', 'Sketching ideas for upcoming scenes.', 30, CURDATE()),
        (1, 'self_care_time', 'Yoga', 'Morning yoga session.', 20, CURDATE()),
        (1, 'self_care_time', 'Meditation', 'Meditation for clarity.', 15, CURDATE());

    -- Insert grab bag activities for the first user
    INSERT INTO GrabBagActivity (user_id, category, name, activity_description) VALUES 
        (1, 'process_time', 'Doodle', 'Free drawing to inspire creativity.'),
        (1, 'self_care_time', 'Take a Walk', 'Short walk to clear the mind.'),
        (1, 'self_care_time', 'Listen to Music', 'Listening to calming music.'),
        (1, 'product_time', 'Brainstorm', 'Brainstorming ideas for characters.');

    -- Insert rewards for the first user
    INSERT INTO Reward (user_id, reward_type, name, reward_description) VALUES 
        (1, 'small', 'Coffee Break', 'Enjoy a coffee break after completing a daily goal.'),
        (1, 'medium', 'Dinner Out', 'Dinner at a favorite restaurant after finishing a chapter.'),
        (1, 'big', 'Weekend Trip', 'A weekend trip for completing the first draft.');

    -- Optionally, you can insert more sample data for other users here if needed

END $$

DELIMITER ;

-- Call the procedure to reset the database to a known good state
CALL reset_database_state();