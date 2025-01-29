DROP DATABASE IF EXISTS WritingProd;
CREATE DATABASE WritingProd;
USE WritingProd;

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
    goal_value BIGINT,  -- Goal value in minutes
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
    duration BIGINT,  -- Duration in minutes
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