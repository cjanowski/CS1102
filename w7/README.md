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
   - Student ID (unique identifier)
   - Name
3. Click "Save"
4. System validates input and confirms addition

### Adding a Course
1. Navigate: Course → Add Course
2. Enter required information:
   - Course ID (unique identifier)
   - Course Name
   - Maximum Students (1-100)
3. Click "Save"
4. System validates and adds the course

### Enrolling Students
1. Select a student from the dropdown
2. Select a course from the dropdown
3. Click "Enroll"
4. System checks:
   - Course capacity
   - Existing enrollment
   - Student eligibility

### Assigning Grades
1. Select a student
2. Click "Assign Grade"
3. Select the course
4. Choose grade (A, B, C, D, or F)
5. Click "Save"
6. Grade is recorded and displayed

### Viewing Student Details
1. Select a student
2. Click "View Details"
3. View comprehensive information:
   - Student ID and Name
   - Enrolled courses
   - Current grades
   - Enrollment status

## Project Structure

### File Organization
```
CS2Java/w7/
├── Main.java                       # Application entry point
├── StudentManagementSystem.java    # Main GUI and system logic
├── Student.java                    # Student data model
├── Course.java                     # Course data model
└── README.md                       # Documentation
```

### Class Descriptions
- `Main.java`: Launches application in Event Dispatch Thread
- `StudentManagementSystem.java`: GUI components and business logic
- `Student.java`: Student data and course enrollment management
- `Course.java`: Course information and capacity management

## Development

### Code Style
- Java naming conventions
  - Classes: PascalCase
  - Methods/Variables: camelCase
  - Constants: UPPER_SNAKE_CASE
- Comprehensive documentation
- Input validation
- Error handling

### Building
```bash
# Clean previous build
rm w7/*.class

# Compile with warnings
javac -Xlint:unchecked w7/*.java

# Run application
java w7.Main
```

### Key Components
- JFrame main window
- JDialog for forms
- JTable for data display
- JComboBox for selections
- GridBagLayout for forms
- BorderLayout for main window

## Troubleshooting

### Common Issues
1. Compilation Errors
   - Verify Java version
   - Check package declarations
   - Ensure all files present

2. Runtime Errors
   - Invalid input handling
   - Null pointer checks
   - Type casting validation

3. GUI Issues
   - Layout manager conflicts
   - Component visibility
   - Event handling

### Error Messages
- "Student ID cannot be null or empty"
  - Enter valid student ID
- "Course is full"
  - Check maximum capacity
- "Student already enrolled"
  - Verify enrollment status

## System Requirements

### Minimum Requirements
- JDK 8+
- 256MB RAM
- 50MB disk space
- 800x600 display

### Recommended
- JDK 11+
- 512MB RAM
- 100MB disk space
- 1024x768 display

## Author
Cory Janowski

## Version
1.0 - Initial Release

## License
Educational use - CS2Java coursework

---

Last Updated: December 2024
