# 📺 TV Series Management System Backend (Spring Boot)

This is the **backend API** for the **TV Series Management System**, built using **Spring Boot** with **JWT-based authentication** and **role-based access control**. It supports both **Admin** and **Normal User** roles with clearly defined permissions and secure API endpoints. The system is designed using a clean **layered architecture**, follows best practices, and supports **MySQL** as database.

---

## 🔐 Features

### ✅ Authentication & Authorization
- **JWT Authentication** for secure access  
- **Role-based access control**: Admin & Normal User  
- **CORS enabled** for frontend-backend integration  
- **Auth filters** to restrict and secure endpoint access

### 👤 User Roles & Permissions

#### 🔸 Admin User
- Add new TV series  
- View all added TV series  
- Filter/search TV series with multiple filters  
- Update existing TV series data  
- Delete TV series from the system
- **Download TV series data**:
  - Download **all TV series** as **CSV** file  
  - Download **TV series** as **PDF** file  
  - Download **TV series** as **ZIP** file containing CSV and PDF files
- **Receive SMS notifications via Twilio** when user request is added

#### 🔹 Normal User
- View all available TV series  
- Search TV series by title  
- View full details of a selected TV series  
- Receive a list of **10 latest TV series**  
- Get TV series filtered by:
  - Selected **category**
  - Selected **language**
  - Selected **year**
  - Selected **collections**
- Send user requests including file uploads
---

## 🛠️ Tech Stack & Tools

- **Spring Boot**
- **Spring Security + JWT**
- **Lombok** for reducing boilerplate code
- **MySQL** for data persistence
- **DTO & Validation Annotations** for clean request/response handling
- **Exception Handling** using global handler and custom exceptions
- **Pagination & Sorting** for API responses
- **ModelMapper** for DTO/entity conversions
- **Spring Data JPA**
- **Apache POI** for CSV generation  
- **OpenPDF** for PDF generation  
- **Java Zip Libraries** for ZIP file creation
- **Twilio SMS Gateway** for sending notifications to Admin
- **Swagger** for API documentation and testing

---
## 🚀 Getting Started

### 📋 Prerequisites

- Java 17+
- Maven
- MySQL Database
- Spring Boot

### 🔧 Setup Instructions

1. **Clone the repository**

```bash
git clone https://github.com/IT19395906/tv-series-management-application-backend.git
cd tv-series-management-application-backend



