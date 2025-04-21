# ATM Transaction Log — JavaFX + PostgreSQL CRUD Application

## 👤 Student
**Name:** Shaiakhmetov Amir

---

## 📋 Description
This project is a graphical Java application that simulates an ATM transaction log system. Users can deposit, withdraw, update, and delete transaction records using a modern JavaFX interface. All data is stored in a PostgreSQL database, and logs are written to a file for audit purposes.

---

## 🎯 Objectives
- Build a JavaFX-based desktop GUI for ATM operations
- Allow full CRUD operations for transaction records
- Use PostgreSQL for reliable, persistent data storage
- Support input validation and error handling for better UX
- Maintain transaction logs in an external text file (`activity.log`)
- Provide automatic loading and display of database records on startup
- Practice modular Java programming with reusable components

---

## ✅ Project Requirements
### Functional Requirements
1. Create transactions with unique UUID, type (deposit/withdraw), and amount
2. Read and view all transactions in a JavaFX GUI
3. Update specific transactions by ID/index
4. Delete selected transactions from the list and DB
5. Display current balance calculated in real time
6. Generate financial reports with total deposits/withdrawals
7. Log all operations (deposit, withdraw, update, delete, report) to `activity.log`
8. Validate input amount field (positive number only)
9. Prevent operations when balance is insufficient
10. Load existing transactions from PostgreSQL at app launch

---

## 🧠 Project Documentation
### 📦 Components and Classes
- `Main.java` — JavaFX launcher
- `ATMJavaFX.java` — JavaFX interface class for user interaction
- `ATM.java` — business logic layer (CRUD operations, reporting, balance)
- `Transaction.java` — transaction model with fields `id`, `type`, `amount`
- `Database.java` — PostgreSQL JDBC connection class

### 🛠 Core Functions
- `getTransactions()` — fetches all records from DB
- `addTransaction()` — inserts new record
- `updateTransaction(index, amount)` — updates transaction by UUID
- `deleteTransaction(index)` — deletes transaction by UUID
- `getBalance()` — real-time SUM of all amounts
- `generateReport()` — totals by type, with formatted string output

### 🧠 Data Model (PostgreSQL table `transactions`)
| Column       | Type              | Description                         |
|--------------|-------------------|-------------------------------------|
| id           | UUID              | Primary key                         |
| type         | VARCHAR(20)       | Transaction type (Deposit/Withdraw) |
| amount       | DOUBLE PRECISION  | Transaction amount                  |
| created_at   | TIMESTAMP         | Time of transaction (auto-generated)|

---

## ⚙️ Algorithms and Logic
- CRUD SQL logic using JDBC
- Validation for numeric fields (amount > 0)
- Real-time update of GUI upon DB operation
- Logging using `FileWriter` with `Date`
- UUID generation for unique ID mapping

---

## 🔧 Challenges Faced
- Transitioning from file-based storage to PostgreSQL
- Keeping GUI and DB in sync with real-time updates
- Implementing indexed selection logic for updates/deletes
- Error handling across database and UI layers

---

## 🧪 Test Cases and Expected Outputs
| Operation           | Input                  | Output / GUI Result                    |
|---------------------|------------------------|----------------------------------------|
| Deposit             | 1000.0                 | Внесено: 1000.0                        |
| Withdraw            | 500.0 (sufficient)     | Снято: 500.0                           |
| Withdraw            | 1100.0 (insufficient)  | Недостаточно средств.                  |
| Update Transaction  | Index: 1, Amount: 500  | Обновлено.                             |
| Delete Transaction  | Index: 1               | Удалено.                               |
| View Balance        | -                      | Баланс: 1000.0                         |
| Generate Report     | -                      | Форматированный отчет                  |

![Снимок экрана (44)](https://github.com/user-attachments/assets/93c622d5-b4ac-4f4c-8ffa-c4dac84d8c69)
![Снимок экрана (45)](https://github.com/user-attachments/assets/d022efd9-b2ea-4ac8-b85b-b306f2bf0f2d)
![Снимок экрана (46)](https://github.com/user-attachments/assets/1caea581-c738-4c47-a88d-b4a745765d6f)
![Снимок экрана (47)](https://github.com/user-attachments/assets/b24fc15e-795d-4596-853b-ecf649b5bd8f)
![Снимок экрана (48)](https://github.com/user-attachments/assets/bc7566ea-d0ba-431c-a427-d9bc331df418)
![Снимок экрана (49)](https://github.com/user-attachments/assets/799f9d51-1937-4e56-b640-69b61f05e2cb)

---

## 🧼 Code Quality
- Modular structure with clear separation of UI/logic/data
- Proper error handling for database failures
- Minimal use of comments; code is self-documenting
- Class naming and method naming follow Java conventions

---

## 📁 Files Included
- `Main.java` — launches JavaFX app
- `ATM.java` — business logic
- `ATMJavaFX.java` — GUI code (JavaFX)
- `Transaction.java` — model class
- `Database.java` — PostgreSQL connection logic
- `activity.log` — runtime log of all actions
- PostgreSQL DB: `atm_database`, table: `transactions`

---

## ▶️ How to Compile and Run
### Requirements:
- JDK 11 or higher
- JavaFX SDK 21
- PostgreSQL installed and running
- Table `transactions` created manually (or auto by init script)

### Compile:
```bash
javac --module-path "javafx-sdk-21/lib" --add-modules javafx.controls -cp ".;postgresql-42.7.5.jar" Main.java ATM.java ATMJavaFX.java Transaction.java Database.java
```

### Run:
```bash
java --module-path "javafx-sdk-21/lib" --add-modules javafx.controls -cp ".;postgresql-42.7.5.jar" Main
```

✅ You are now running a fully functional PostgreSQL-backed ATM transaction log system built with JavaFX!

