USE cookbook;

DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS recipes;
DROP TABLE IF EXISTS steps;

CREATE TABLE IF NOT EXISTS users(
	user_id INT AUTO_INCREMENT PRIMARY KEY,
	user_name VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100),
    first_name VARCHAR(100),
    last_name VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS roles (
	role_id INT AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS user_role(
	user_id INT,
    role_id INT,
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS recipes (
	recipe_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    category VARCHAR(100),
    difficulty INT,
    image VARCHAR(200),
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS steps (
	step_id INT AUTO_INCREMENT PRIMARY KEY,
    step VARCHAR(300),
    recipe_id INT,
    FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id)
);
