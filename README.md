# 💳 Optimised Banking System

A console-based banking system developed in **Java** using **Hibernate ORM** and **MySQL**, built with industry standards in mind. This application allows users to register, log in, manage accounts, and perform secure transactions.

---

## 🚀 Features

- ✅ User Registration & Login
- 🏦 Create Bank Account
- 💰 Deposit / Withdraw Money
- 🔁 Transfer Money Between Accounts
- 📊 Check Account Balance
- 🔐 Hibernate-based ORM for data persistence
- 🗃️ MySQL as the backend database

---

## 🛠️ Technologies Used

| Tech            | Purpose                          |
|----------------|----------------------------------|
| Java (JDK 21)   | Core backend logic               |
| Hibernate ORM  | Object-Relational Mapping (ORM)  |
| MySQL          | Database                         |
| Maven          | Project management & dependencies|
| IntelliJ IDEA  | Development IDE                  |


## 📊 JDBC vs Hibernate – Code Difference Table

| Feature / Code Area         | **JDBC Project** (`Banking_management_system`)          | **Hibernate Project** (`Optimised_banking_system`)          |
|-----------------------------|----------------------------------------------------------|-------------------------------------------------------------|
| **Database Connection**     | Manual via `DriverManager.getConnection()`               | Auto via `SessionFactory` in `hibernate.cfg.xml`            |
| **SQL Queries**             | Written manually in Java (e.g., `INSERT INTO ...`)       | Not needed — uses annotations or HQL                        |
| **Data Access Layer**       | Repeated code for `Connection`, `Statement`, `ResultSet` | Uses `Session.save()`, `Session.get()`, etc.                |
| **Object Mapping**          | Not present — manual field mapping                       | Done via annotations like `@Entity`, `@Column`, `@Id`       |
| **Transaction Management**  | Manual commit/rollback via `Connection` object           | Managed via `Transaction tx = session.beginTransaction()`   |
| **Error Handling**          | Try-catch blocks for each DB call                        | Centralized exception handling via Hibernate APIs           |
| **Code Readability**        | More verbose and repetitive                              | Clean, concise, and easier to manage                        |
| **Boilerplate Code**        | High — setup repeated in every DAO class                 | Low — Hibernate manages most behind the scenes              |
| **Scalability**             | Harder to scale due to tight DB coupling                 | Easy to scale with entity relationships & lazy loading      |

---

## ⚙️ How to Run

### Prerequisites

- Java 17+ installed
- MySQL Server running
- IntelliJ IDEA or any IDE
- Maven installed



