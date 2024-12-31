# Student Management System

A Java Swing-based desktop application for managing students, courses, enrollments, and grades in an educational institution.

## Table of Contents
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Development](#development)
- [Troubleshooting](#troubleshooting)

## Features

### Core Functionality
- Student Management (Add, View, Track)
- Course Management (Add, Monitor)
- Grade Assignment
- Enrollment System

### Interface Components
- Interactive GUI using Java Swing
- Real-time table updates
- Form validation
- Error handling

## Installation

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Java Runtime Environment (JRE)

### Setup
1. Clone or download the project files
2. Navigate to the project directory:
   ```bash
   cd CS2Java/w7
   ```
3. Compile the Java files:
   ```bash
   javac w7/*.java
   ```
4. Run the application:
   ```bash
   java w7.Main
   ```

## Usage

### Adding a Student
1. Navigate: Student → Add Student
2. Enter required information:
   - Student ID
   - Name
3. Click "Save"

### Adding a Course
1. Navigate: Course → Add Course
2. Enter required information:
   - Course ID
   - Course Name
   - Maximum Students
3. Click "Save"

### Enrolling Students
1. Select a student from the dropdown
2. Select a course from the dropdown
3. Click "Enroll"

### Assigning Grades
1. Select a student
2. Click "Assign Grade"
3. Select the course
4. Choose grade (A-F)
5. Click "Save"

### Viewing Student Details
1. Select a student
2. Click "View Details"
3. View comprehensive student information:
   - Personal details
   - Enrolled courses
   - Grades

## Project Structure
