-- Enable foreign key constraints in sqlite
PRAGMA foreign_keys = ON;

-- Create the 'users' table using TPH (Table Per Hierarchy) inheritance
-- Single table for all types of the users
CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(50) PRIMARY KEY,
    user_type VARCHAR(30),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    national_id VARCHAR(20),
    birth_year VARCHAR(10),
    address VARCHAR(100),
    borrow_count INT,
    penalty INT,
    advisor_name VARCHAR(50),
    department VARCHAR(50),
    role VARCHAR(50)
    );

-- Create the 'libraries' table
CREATE TABLE IF NOT EXISTS libraries (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50),
    address VARCHAR(100),
    establish_year VARCHAR(10),
    table_count INT
    );

-- Create the 'categories' table
CREATE TABLE IF NOT EXISTS categories (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50),
    parent_category_id VARCHAR(50)
    );

-- Insert a default category record
INSERT INTO categories (id, name, parent_category_id) VALUES ("null", "null", null);

-- Create the 'resources' table using TPT (Table-Per-Type) inheritance
CREATE TABLE IF NOT EXISTS resources (
    id VARCHAR(50),
    library_id VARCHAR(50),
    category_id VARCHAR(50) DEFAULT "null",
    title VARCHAR(255),
    author VARCHAR(255),
    resource_type VARCHAR(30),
    PRIMARY KEY (id, library_id),
    FOREIGN KEY (library_id) REFERENCES libraries(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET DEFAULT
    );

-- Create specific resource tables (treasure_books, borrowable_books, theses, purchasable_books)
-- Each of these tables inherits from the 'resources' table

-- Create the 'treasure_books' table
CREATE TABLE IF NOT EXISTS treasure_books (
    id VARCHAR(50),
    library_id VARCHAR(50),
    publisher VARCHAR(50),
    publication_year VARCHAR(4),
    donor VARCHAR(50),
    FOREIGN KEY (id, library_id) REFERENCES resources(id, library_id) ON DELETE CASCADE
    );

-- Create the 'borrowable_books' table
CREATE TABLE IF NOT EXISTS borrowable_books (
    id VARCHAR(50),
    library_id VARCHAR(50),
    publisher VARCHAR(50),
    publication_year VARCHAR(4),
    copies_count INT,
    borrowed_copies_count INT,
    total_borrows_count INT,
    total_borrows_duration INT,
    FOREIGN KEY (id, library_id) REFERENCES resources(id, library_id) ON DELETE CASCADE
    );

-- Create the 'theses' table
CREATE TABLE IF NOT EXISTS theses (
    id VARCHAR(50),
    library_id VARCHAR(50),
    advisor_name VARCHAR(50),
    defense_year VARCHAR(4),
    copies_count INT,
    borrowed_copies_count INT,
    total_borrows_count INT,
    total_borrows_duration INT,
    FOREIGN KEY (id, library_id) REFERENCES resources(id, library_id) ON DELETE CASCADE
    );

-- Create the 'purchasable_books' table
CREATE TABLE IF NOT EXISTS purchasable_books (
    id VARCHAR(50),
    library_id VARCHAR(50),
    publisher VARCHAR(50),
    publication_year VARCHAR(4),
    copies_count INT,
    purchased_copies_count INT,
    price INT,
    discount_percentage INT,
    total_purchase_profit INT,
    FOREIGN KEY (id, library_id) REFERENCES resources(id, library_id) ON DELETE CASCADE
    );

-- Create the 'read_records' table
CREATE TABLE IF NOT EXISTS read_records (
    resource_id VARCHAR(30),
    library_id VARCHAR(30),
    start_time TEXT,
    end_time TEXT,
    FOREIGN KEY (resource_id, library_id) REFERENCES resources(id, library_id) ON DELETE CASCADE
    );

-- Create the 'borrow_records' table
CREATE TABLE IF NOT EXISTS borrow_records (
    resource_id VARCHAR(30),
    library_id VARCHAR(30),
    user_id VARCHAR(30),
    borrow_time TEXT,
    return_time TEXT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (resource_id, library_id) REFERENCES resources(id, library_id) ON DELETE CASCADE
    );
