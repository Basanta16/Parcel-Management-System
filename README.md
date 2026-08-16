# 📦 Parcel Management System

A web-based parcel management application developed to simplify parcel
handling and collection management in a real-world workplace environment.

## 🚀 Live Demo

[Live Application](https://parcel-management-system.onrender.com/)

## 📌 About the Project

The Parcel Management System was developed to improve the process of
recording, organizing, and tracking customer parcels received from
multiple courier services.

The system currently supports:

- Royal Mail
- Evri
- DPD

Each courier maintains its own serial number allocation from 1–50.

A customer receiving multiple parcels from the same courier keeps the
same serial number, while parcels received from another courier are
assigned a separate serial number.

When all parcels associated with a courier serial number are collected,
the serial number becomes available for reassignment.

## ✨ Features

- Register customer parcels
- Support multiple courier services
- Automatically assign serial numbers from 1–50
- Reuse available serial numbers
- Multiple parcels per customer
- Same serial number for multiple parcels from the same courier
- Different serial numbers across different couriers
- Display parcel count
- Record parcel acceptance date and time
- Search customers by name
- Mark parcels as picked up
- Automatically release serial numbers after collection
- Courier-specific customer lists
- Persistent cloud database
- Responsive web interface

## 🛠️ Technologies

### Backend
- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- REST API
- Maven

### Frontend
- HTML5
- CSS3
- JavaScript

### Database
- MySQL
- Aiven Cloud MySQL

### Deployment
- Docker
- Render

### Version Control
- Git
- GitHub

## 🏗️ Architecture

Frontend
   ↓
Spring Boot REST API
   ↓
Service Layer
   ↓
Repository Layer
   ↓
Hibernate / JPA
   ↓
MySQL

## 📦 Parcel Assignment Example

John receives:

Royal Mail → 2 parcels → Serial #5

Evri → 3 parcels → Serial #8

If John receives another Royal Mail parcel:

Royal Mail → 3 parcels → Serial #5

When John collects all Royal Mail parcels, Serial #5 becomes available
for another customer.

## 🔐 Security

Sensitive database credentials are stored using environment variables
and are not committed to the repository.

DB_URL
DB_USERNAME
DB_PASSWORD

## 👨‍💻 Author

Developed as a personal software project inspired by a real-world parcel
management requirement.
