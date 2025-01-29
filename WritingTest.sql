DROP DATABASE IF EXISTS WritingTest;
CREATE DATABASE WritingTest;
USE WritingTest;

-- Table for user
CREATE TABLE User (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,  -- User's email address
    password_hash VARCHAR(255) NOT NULL,  -- For storing hashed passwords
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	role VARCHAR(5) NOT NULL
);

-- Table for daily goal
CREATE TABLE DailyGoal (
    goal_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    category ENUM('product', 'process', 'self-care') NOT NULL,
    goal_value INT,  -- Goal value in minutes
    day_of_week ENUM('Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'),
    goal_time TIME,  -- Optional time of day to complete the goal
    status BOOLEAN NOT NULL DEFAULT 1,  -- Tracks if the goal is active (1) or inactive (0)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE
);

-- Table for daily activity
CREATE TABLE DailyActivity (
    activity_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    category ENUM('product', 'process', 'self-care') NOT NULL,
    name VARCHAR(100) NOT NULL,  -- Name of the activity
    activity_description VARCHAR(255),  -- Optional description of the activity
    duration INT,  -- Duration in minutes
    date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE
);

-- Table for grab bag activity
CREATE TABLE GrabBagActivity (
    activity_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    category ENUM('product', 'process', 'self-care') NOT NULL,
    name VARCHAR(100) NOT NULL,  -- Name of the activity
    activity_description VARCHAR(255),  -- Optional description of the activity
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE
);

-- Table for reward
CREATE TABLE Reward (
    reward_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    reward_type ENUM('small', 'medium', 'big') NOT NULL,
    name VARCHAR(100) NOT NULL, -- Name of the reward
    reward_description VARCHAR(255),  -- Description of the reward
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE
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
    INSERT INTO User (username, email, password_hash, created_at, role) VALUES
        ('testuser1', 'testuser1@example.com', '$2a$10$U/pSFUb4EctwgiPfh7oaau3O5v8g1e7OPq3cjrP7C.yVO9QpNm2Em', CURDATE(), "ADMIN"),
        ('testuser2', 'testuser2@example.com', '$2a$10$U/pSFUb4EctwgiPfh7oaau3O5v8g1e7OPq3cjrP7C.yVO9QpNm2Em', CURDATE(), "USER"),
        ('testuser3', 'testuser3@example.com', '$2a$10$U/pSFUb4EctwgiPfh7oaau3O5v8g1e7OPq3cjrP7C.yVO9QpNm2Em', CURDATE(), "USER");

    -- Insert daily goals for the first user
    INSERT INTO DailyGoal (user_id, category, goal_value, day_of_week, goal_time, status) VALUES
        (1, 'PRODUCT', 120, 'MONDAY', '09:00:00', 1),
        (1, 'PROCESS', 60, 'TUESDAY', '10:30:00', 1),
        (1, 'SELF-CARE', 30, 'WEDNESDAY', NULL, 1),
        (1, 'PRODUCT', 90, 'THURSDAY', '08:00:00', 1),
        (1, 'SELF-CARE', 45, 'FRIDAY', '17:00:00', 1);

    -- Insert daily activities for the first user
    INSERT INTO DailyActivity (user_id, category, name, activity_description, duration, date) VALUES
        (1, 'product', 'Write Chapter 1', 'Drafting the first chapter of the book.', 90, CURDATE()),
        (1, 'process', 'Sketch Ideas', 'Sketching ideas for upcoming scenes.', 30, CURDATE()),
        (1, 'self-care', 'Yoga', 'Morning yoga session.', 20, CURDATE()),
        (1, 'self-care', 'Meditation', 'Meditation for clarity.', 15, CURDATE());

    -- Insert grab bag activities for the first user
    INSERT INTO GrabBagActivity (user_id, category, name, activity_description) VALUES
        (1, 'process', 'Doodle', 'Free drawing to inspire creativity.'),
        (1, 'self-care', 'Take a Walk', 'Short walk to clear the mind.'),
        (1, 'self-care', 'Listen to Music', 'Listening to calming music.'),
        (1, 'product', 'Brainstorm', 'Brainstorming ideas for characters.');

    -- Insert rewards for the first user
    INSERT INTO Reward (user_id, reward_type, name, reward_description) VALUES
        (1, 'small', 'Coffee Break', 'Enjoy a coffee break after completing a daily goal.'),
        (1, 'medium', 'Dinner Out', 'Dinner at a favorite restaurant after finishing a chapter.'),
        (1, 'big', 'Weekend Trip', 'A weekend trip for completing the first draft.');

END $$

DELIMITER ;

-- Call the procedure to reset the database to a known good state
CALL reset_database_state();