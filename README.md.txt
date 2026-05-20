# Parking Management System

A full-stack application built to manage daily parking lot operations, track vehicle logs, and handle financial transactions. 

> **Note:** This project is currently under active development. The backend is in advanced stages, while the frontend is in its initial setup phase.

## 🛠️ Tech Stack

### Backend
* **Framework:** Spring Boot (Java)
* **Data Access:** Spring Data JPA
* **Database:** H2 Database (Embedded for easy local environment setup)

### Frontend
* **Library:** React.js (JavaScript)
* **Build Tool:** Vite
* **State Management:** Redux Toolkit

---

## 📁 Project Structure & Current Status

* `backend/` *(Advanced Stage)* - Core business logic, REST APIs, database configurations, and global exception handling are fully implemented.
* `frontend/` *(Initial Stage)* - Project structure is set up with initial routing and the Login/Authentication page. Further UI components are under development.

---

## 🚀 Getting Started

Since the project utilizes an embedded database, there is no need for complex database installation or setup.

### Running the Backend
1. Open the `backend` directory in your IDE (e.g., IntelliJ IDEA).
2. Let Maven import the dependencies.
3. Run the application. The H2 database will automatically initialize in-memory.

### Running the Frontend
1. Navigate to the `frontend` directory using your terminal.
2. Run `npm install` to install dependencies.
3. Run `npm run dev` to start the local development server.