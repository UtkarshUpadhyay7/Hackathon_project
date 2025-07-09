# 🧊 DoomShelf

> AI-Powered Expiry Tracker & Inventory Management System  
>   Built for Hackathon 2025

---

## 🚀 Overview

**DoomShelf** is a smart inventory and expiry tracking desktop app built with modern technologies like **Java**, **SpringBoot**, and **MySql**. Designed for individuals, small businesses, and retailers, DoomShelf makes it easy to manage stock, get expiry alerts, and track products using barcodes or manual entry.

It features real-time updates, clean UI/UX, and supports OAuth login for secure access.

---

## 🧠 Key Features

- 📦 **Add Products via Barcode or Manual Entry**
- ⏰ **Expiry Date Tracking & Alerts**
- 🧮 **Real-time Inventory Overview**
- 🔍 **Search, Filter, and Sort Produts
  

---

## 🛠️ Tech Stack

| Layer         | Tech Used                          |
|---------------|------------------------------------|
| **Frontend**  | HTML, Tailwind CSS, JavaScript |
| **Backend**   | Node.js or Java (Spring Boot)      |
| **Database**  |  (MySQL)              |
| **Other**     | GitHub Actions,                    |

---

## 📁 Folder Structure


expiry-shelf-guardian-backend/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── expiryguardian/
│       │           ├── controller/
│       │           │   ├── ProductController.java
│       │           │   └── AlertController.java
│       │           │
│       │           ├── model/
│       │           │   ├── Product.java
│       │           │   └── Alert.java
│       │           │
│       │           ├── repository/
│       │           │   ├── ProductRepository.java
│       │           │   └── AlertRepository.java
│       │           │
│       │           ├── service/
│       │           │   ├── ProductService.java
│       │           │   └── AlertService.java
│       │           │
│       │           └── ExpiryShelfGuardianApplication.java
│       │
│       └── resources/
│           ├── application.properties
│           └── data.sql (optional test data)
│
├── .gitignore
├── pom.xml
└── README.md


---

### 📂 Key File Purposes

| File                     | Purpose                                      |
| ------------------------ | -------------------------------------------- |
| Product.java           | Entity class for products                    |
| Alert.java             | Entity for expiry alerts                     |
| ProductRepository.java | Interface for CRUD operations                |
| AlertRepository.java   | Interface for alert handling                 |
| ProductService.java    | Business logic: saving, checking expiry      |
| AlertService.java      | Handles creation of alerts                   |
| ProductController.java | APIs: /api/products, /uploadQR           |
| AlertController.java   | Optional: for displaying alerts              |
| application.properties | DB config, port, JPA settings                |
| pom.xml                | Dependencies (Spring Web, JPA, MySQL, etc.)  |
| README.md              | Project summary, tech used, run instructions |

----


