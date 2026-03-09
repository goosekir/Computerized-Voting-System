# Computerized Voting System  
**Authors:** Zakir Rizvi, Nicholas Pellegrino  

## 1. Introduction

Elections are a fundamental part of democratic decision-making, but traditional voting systems often suffer from inefficiencies such as slow counting, human error, and limited transparency during recounts. The goal of this project was to design and implement a **computerized voting system** capable of providing fast, accurate, and reliable election results while maintaining voter anonymity and ensuring the integrity of the voting process.

The system was developed as a desktop application using **JavaFX** for the user interface and **MongoDB** for data storage. It allows administrators to manage elections and candidates while enabling voters to securely cast their votes through a controlled token-based mechanism.

---

## 2. Objectives

The primary objectives of the project were:

- Design a secure and efficient voting system.
- Provide near-instantaneous vote counting and result reporting.
- Maintain voter anonymity while preventing duplicate voting.
- Create an intuitive graphical user interface for administrators and voters.
- Implement a database structure capable of storing elections, candidates, tokens, and votes.

---

## 3. System Architecture

The system consists of two main components:

### Application Layer
The application was developed in **Java using JavaFX** to provide a graphical interface for interacting with the voting system. The application handles:

- Election creation and management
- Candidate registration
- Voter token validation
- Vote submission
- Result visualization

### Database Layer
The database layer is implemented using **MongoDB**, a NoSQL document database. MongoDB was chosen because it provides flexible schema design and fast query performance, making it suitable for storing election data.

The database stores:

- Elections
- Candidates
- Voter tokens
- Votes

Scripts were created to initialize and seed the database with a sample election for demonstration purposes.

---

## 4. Key Features

The system implements several important features:

### Token-Based Voting
Each voter receives a unique token that can only be used once. This prevents duplicate voting while preserving anonymity since tokens are not directly tied to personal identities.

### Election Management
Administrators can create and manage elections, add candidates, and control when elections are open or closed.

### Secure Vote Recording
Votes are recorded in the MongoDB database with validation to ensure that each token can only submit one vote.

### Real-Time Results
Election results can be viewed immediately after votes are submitted. The system queries the database and calculates vote totals for each candidate.

### Graphical User Interface
The application uses JavaFX to provide an easy-to-use interface for interacting with the system. This includes screens for logging in, managing elections, casting votes, and viewing results.

---

## 5. Database Design

The MongoDB database is structured using collections representing key entities in the voting system:

- **Elections** – Stores election metadata and status.
- **Candidates** – Stores candidates associated with elections.
- **Votes** – Records individual votes cast during elections.
- **Tokens** – Stores voter tokens used for authentication and vote validation.

Initialization scripts were written to create the database schema and populate it with sample data. This allows the system to be quickly demonstrated without manual setup.

---

## 6. Demonstration Workflow

The typical workflow of the system is as follows:

1. The database is initialized using the provided scripts.
2. An election and candidate list are loaded into the system.
3. A voter receives a token.
4. The voter uses the token to cast a vote for a candidate.
5. The vote is stored in the database.
6. The system displays updated election results.

This workflow demonstrates the full voting lifecycle from election setup to result reporting.

---

## 7. Technologies Used

The following technologies were used in the development of the project:

- **Java** – Core application logic
- **JavaFX** – Graphical user interface
- **MongoDB** – NoSQL database
- **MongoDB Compass** – Database management tool
- **Maven** – Dependency management and project build system
- **Git & GitHub** – Version control and project collaboration

---

## 8. Conclusion

This project demonstrates how modern software technologies can be used to create a fast, reliable, and transparent computerized voting system. By combining a graphical interface with a scalable database backend, the system allows votes to be securely recorded and results to be generated instantly.

While the current implementation is designed primarily as a demonstration system, the architecture could be expanded with additional security measures, authentication mechanisms, and distributed infrastructure to support larger-scale elections.

The project successfully illustrates the potential advantages of computerized voting systems, including improved efficiency, reduced human error, and enhanced auditability.
