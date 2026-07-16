# 💬 Java Client-Server Messenger

A desktop-based **Client-Server Chat Application** developed in **Java** using **Socket Programming**, **Java Swing**, and **MySQL**. The project demonstrates real-time communication between a client and server with a graphical user interface while storing chat history in a MySQL database.

---

## 📖 Overview

This project implements a simple messaging system where:

* A **Server** listens for client connections on a specified port.
* A **Client** connects to the server and sends messages.
* Every client message is displayed on the server GUI.
* Messages are stored permanently in a **MySQL database**.
* Previous chat history is automatically loaded when the server starts.
* The server sends an acknowledgement back to the client for every received message.

This project was developed to strengthen concepts of:

* Java Socket Programming
* Java Swing GUI Development
* JDBC (Java Database Connectivity)
* Multithreading
* Client-Server Architecture

---

## 🚀 Features

* ✅ Java Swing graphical user interface
* ✅ Client-Server communication using TCP sockets
* ✅ Real-time message transmission
* ✅ MySQL database integration
* ✅ Persistent chat history
* ✅ Automatic timestamps for messages
* ✅ Multithreaded server
* ✅ Start and Stop server functionality
* ✅ Simple and user-friendly interface

---

## 🛠️ Technologies Used

| Technology                         | Purpose                     |
| ---------------------------------- | --------------------------- |
| Java                               | Programming Language        |
| Java Swing                         | GUI Development             |
| Java Socket Programming            | Client-Server Communication |
| JDBC                               | Database Connectivity       |
| MySQL                              | Message Storage             |
| Multithreading                     | Concurrent Server Execution |
| IntelliJ IDEA / Eclipse / NetBeans | Development IDE             |

---

## 📂 Project Structure

```text
Java-Client-Server-Messenger/
│
├── Server.java
├── Client.java
├── image2icon.png
├── README.md
└── Database/
    └── messages.sql
```

---

## ⚙️ How It Works

```text
              Client
                 │
                 │
         TCP Socket (Port 7777)
                 │
                 ▼
             Server
                 │
      Receives Client Message
                 │
                 ▼
         Stores in MySQL
                 │
                 ▼
       Displays on Server GUI
                 │
                 ▼
 Sends Acknowledgement to Client
```

---

## 🗄️ Database

Create a MySQL database named:

```sql
CREATE DATABASE messages;
```

Create the table:

```sql
CREATE TABLE message (
    id INT AUTO_INCREMENT PRIMARY KEY,
    messages VARCHAR(255),
    TIME VARCHAR(20)
);
```

Update the database credentials inside `Server.java`:

```java
DriverManager.getConnection(
    "jdbc:mysql://localhost/messages",
    "YOUR_USERNAME",
    "YOUR_PASSWORD"
);
```

---

## ▶️ Running the Project

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/Java-Client-Server-Messenger.git
```

### 2. Import into your IDE

Open the project using:

* IntelliJ IDEA
* Eclipse
* NetBeans

### 3. Configure MySQL

* Install MySQL Server.
* Create the required database and table.
* Update your JDBC credentials.

### 4. Run the Server

Start:

```text
Server.java
```

Click the **Start** button.

### 5. Run the Client

Execute:

```text
Client.java
```

You can now exchange messages.

---

## 📸 Screenshots

Add screenshots here after uploading images.

```text
screenshots/
    server.png
    client.png
```

Example:

```
![Server](screenshots/server.png)

![Client](screenshots/client.png)
```

---

## 🧠 Concepts Demonstrated

* Client-Server Architecture
* TCP Socket Programming
* Java Swing Components
* Event Handling
* Multithreading
* JDBC Connectivity
* PreparedStatement
* MySQL Integration
* GUI Design
* Exception Handling

---

## 🔮 Future Improvements

* User authentication
* Multiple client support
* Broadcast messaging
* Private messaging
* Online/offline status
* File sharing
* Image sharing
* Message encryption
* Group chat
* Emoji support
* Better UI/UX
* Cloud database integration

---

## 🎓 Learning Outcomes

This project helped in understanding:

* How TCP communication works.
* Building desktop applications with Java Swing.
* Using JDBC for database operations.
* Managing multiple threads in Java.
* Implementing persistent message storage.
* Designing a complete client-server application.

---

## 👩‍💻 Author

**Areesha Raheel**

BS Information Technology
Riphah International University

GitHub: https://github.com/Areesha-123

---

## 📜 License

This project is developed for educational and learning purposes.

Feel free to fork, modify, and improve it.
