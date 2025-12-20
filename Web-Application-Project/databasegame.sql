-- ===========================
-- DROP & CREATE DATABASE
-- ===========================
DROP DATABASE IF EXISTS typing_game_db;
CREATE DATABASE typing_game_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
USE typing_game_db;

-- ===========================
-- USERS
-- ===========================
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    full_name VARCHAR(255) NOT NULL,
    `role` ENUM('USER','ADMIN') NOT NULL
);

-- ===========================
-- CATEGORY
-- ===========================
CREATE TABLE category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL UNIQUE,
    `description` TEXT
);

-- ===========================
-- WORDS
-- ===========================
CREATE TABLE words (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    word VARCHAR(255) NOT NULL,
    category_id BIGINT,
    CONSTRAINT fk_words_category
        FOREIGN KEY (category_id)
        REFERENCES category(id)
        ON DELETE SET NULL
);

-- ===========================
-- USER_WORD
-- ===========================
CREATE TABLE user_word (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    word_id BIGINT NOT NULL,
    mistake_count INT NOT NULL,
    UNIQUE (user_id, word_id),
    CONSTRAINT fk_user_word_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_user_word_word
        FOREIGN KEY (word_id)
        REFERENCES words(id)
        ON DELETE CASCADE
);

-- ===========================
-- ACHIEVEMENT
-- ===========================
CREATE TABLE achievement (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    achievement_code VARCHAR(255) NOT NULL UNIQUE,
    `name` VARCHAR(255) NOT NULL,
    `description` TEXT,
    `condition` VARCHAR(255) NOT NULL
);

-- ===========================
-- USER_ACHIEVEMENT
-- ===========================
CREATE TABLE user_achievement (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    achievement_id BIGINT NOT NULL,
    unlocked_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id, achievement_id),
    CONSTRAINT fk_user_achievement_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_user_achievement_achievement
        FOREIGN KEY (achievement_id)
        REFERENCES achievement(id)
        ON DELETE CASCADE
);

-- ===========================
-- USER_STATS
-- ===========================
CREATE TABLE user_stats (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL UNIQUE,
    total_words_typed INT NOT NULL,
    best_wpm DOUBLE NOT NULL,
    average_accuracy DOUBLE NOT NULL,
    CONSTRAINT fk_user_stats_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

-- ===========================
-- GAME_SESSION
-- ===========================
CREATE TABLE game_session (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    duration INT NOT NULL,
    words_typed INT NOT NULL,
    correct_words INT NOT NULL,
    incorrect_words INT NOT NULL,
    raw_wpm DOUBLE NOT NULL,
    wpm DOUBLE NOT NULL,
    accuracy DOUBLE NOT NULL,
    `mode` ENUM('NORMAL','TIME','QUOTE','CUSTOM') NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_game_session_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE SET NULL
);

-- ===========================
-- SCORES
-- ===========================
CREATE TABLE scores (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
   score DECIMAL(5,2) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_scores_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE SET NULL
);




