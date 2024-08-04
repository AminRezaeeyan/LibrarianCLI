# LibrarianCLI: A Simple Library Management System

LibrarianCLI is an offline console-based library management system written in Java. It allows librarians to manage users, resources, libraries, categories, and handle borrowing, returning, reading and purchasing of resources. The system is designed with simplicity, ease of use, and extensibility in mind.

## Features

- **Four Layers of Implementation:**
    - **Models (Entities):** Define the data structures (e.g., `User`, `Book`, `Library`, `Category`).
    - **DAO (Data Access Object):** Responsible for CRUD operations on the database.
    - **Service Layer:** Bridges the gap between the command line interface (CLI) and the DAO.
    - **CLI:** Reads commands from the terminal, dispatches them to the appropriate service methods, and displays responses.
  
- **Design Patterns:**
    - DAO (Data Access Object) design pattern
    - Singleton design pattern
- **Colorful UI:** Utilizes ANSI escape codes for a visually appealing console interface.

- **Configuration:**
    - `app.properties`: Customize app settings.
    - `libraryData.db`: SQLite database file containing library data.

- **Database Setup:**
    - `databaseSetup.sql`: Contains SQL scripts to initialize the database schema (In case you want to reset the database or use other database). Note that libraryData.db is already set up.

- **Comprehensive Documentation:**
    - Check out the `manual.md` for detailed instructions on using LibrarianCLI.

## Getting Started

- Make sure that you have `Maven` installed on your system

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/AminRezaeeyan/LibrarianCLI.git
   cd LibrarianCLI
   ```
2. **Build and Run:**
   ```bash
   mvn clean install
   java -jar target/LibrarianCLI-1.0.0-jar-with-dependencies.jar
   ```
   
## Usage:

- Run the CLI and follow the prompts to manage users, books, libraries, and categories.
- Use the commands described in the manual.

## Contribution
Contributions are welcome! Feel free to open issues or submit pull requests.

