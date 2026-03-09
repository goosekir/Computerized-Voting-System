# Computerized-Voting-System

## Technologies Used

- Java
- JavaFX
- MongoDB
- Maven
- Docker

A voting system which can utilize the speed and accuracy of computers to have nearly instantaneous, error free results and provide an auditable trail for recounts when disputes arise over the outcome of an election all while preserving the anonymity of the voter. The goal is to ensure redundancy, reliability, and a verifiable and trusted system.

## Running the Project

### Requirements

Before running the project, make sure the following are installed:

- Java 11 or later  
- Maven  
- MongoDB  
- MongoDB Compass  

---

### 1. Start MongoDB

Start your local MongoDB server.

If you are using **MongoDB Compass**, open the application and connect to your local MongoDB instance:

mongodb://localhost:27017

---

### 2. Initialize the Database

Navigate to the database setup scripts located in:

voting-system-db/scripts/

Run the following scripts using **MongoDB Compass**:

1. `init.js`
2. `seed.js`

These scripts will:

- Create the required database and collections  
- Insert initial data  
- Create a preconfigured election used for demonstration  

---

### 3. Run the Application

Navigate to the application folder:

demo/

Then run the JavaFX application using Maven:

mvn javafx:run

Alternatively, open the `demo` folder in **VS Code** or your preferred IDE and run the `App.java` file.

---

### 4. Demonstration Flow

Once the application launches:

1. Open the existing election created by the seed script.  
2. Generate or enter a voter token.  
3. Cast a vote for a candidate.  
4. View the live election results.

---

### Project Structure


Computerized-Voting-System
│
├── demo # JavaFX application
├── voting-system-db # MongoDB schema and setup scripts
├── README.md # Project documentation
└── plan.md # Project planning notes

By,

Nicholas Pellegrino

Zakir Rizvi
