/**
 * University Course Management System
 * This system manages students, courses, enrollments, and grades in a university setting.
 * It provides a command-line interface for administrators to perform various operations.
 */
import java.util.*;

/**
 * Main class containing the command-line interface and program entry point.
 * Provides a menu-driven interface for interacting with the CourseManagement system.
 */
public class w5a {
    // Define all classes as static nested classes first
    static class Student {
        // Counter for generating unique student IDs, starts at 1000 and increments for each new student
        private static int nextStudentId = 1000;
        private int studentId;          // Unique identifier for each student
        private String name;            // Student's full name
        // Maps Course objects to their corresponding grades (stored as percentages)
        private Map<Course, Double> enrolledCourses;

        /**
         * Creates a new Student with an auto-generated ID and initializes their course list.
         * @param name The full name of the student
         * @throws IllegalArgumentException if name is empty or only whitespace
         */
        public Student(String name) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Student name cannot be empty");
            }
            this.studentId = nextStudentId++;
            this.name = name.trim();
            this.enrolledCourses = new HashMap<>();
        }

        // Getter methods for accessing private fields
        public int getStudentId() { return studentId; }
        public String getName() { return name; }
        public Map<Course, Double> getEnrolledCourses() { return enrolledCourses; }

        /**
         * Enrolls the student in a new course with an initial grade of 0.0
         * @param course The course to enroll in
         */
        public void enrollCourse(Course course) {
            enrolledCourses.put(course, 0.0);
        }

        /**
         * Updates the student's grade for a specific course
         * @param course The course to update the grade for
         * @param grade The new grade (percentage)
         * @throws IllegalArgumentException if grade is not between 0 and 100
         */
        public void updateGrade(Course course, double grade) {
            if (grade < 0 || grade > 100) {
                throw new IllegalArgumentException("Grade must be between 0 and 100");
            }
            if (enrolledCourses.containsKey(course)) {
                enrolledCourses.put(course, grade);
            }
        }

        /**
         * Retrieves the student's grade for a specific course
         * @param course The course to get the grade for
         * @return The grade as a percentage, or 0.0 if not enrolled
         */
        public double getGrade(Course course) {
            return enrolledCourses.getOrDefault(course, 0.0);
        }
    }

    static class Course {
        // Move Course class implementation here, making it static
        // Add 'static' to the class declaration and keep all the existing code
        private static int nextCourseId = 100;
        private int courseId;           // Unique identifier for each course
        private String courseName;      // Name of the course
        private int maxCapacity;        // Maximum number of students allowed
        private List<Student> enrolledStudents;  // List of currently enrolled students

        /**
         * Creates a new Course with an auto-generated ID and specified capacity
         * @param courseName The name of the course
         * @param maxCapacity Maximum number of students that can enroll
         * @throws IllegalArgumentException if maxCapacity is less than 1 or courseName is empty
         */
        public Course(String courseName, int maxCapacity) {
            if (courseName == null || courseName.trim().isEmpty()) {
                throw new IllegalArgumentException("Course name cannot be empty");
            }
            if (maxCapacity < 1) {
                throw new IllegalArgumentException("Course capacity must be at least 1");
            }
            this.courseId = nextCourseId++;
            this.courseName = courseName.trim();
            this.maxCapacity = maxCapacity;
            this.enrolledStudents = new ArrayList<>();
        }

        // Getter methods for accessing private fields
        public int getCourseId() { return courseId; }
        public String getCourseName() { return courseName; }
        public List<Student> getEnrolledStudents() { return enrolledStudents; }
        
        /**
         * Attempts to enroll a student in the course if there's available capacity
         * @param student The student to enroll
         * @return true if enrollment successful, false if course is full or student already enrolled
         */
        public boolean enrollStudent(Student student) {
            // Check if student is already enrolled
            if (enrolledStudents.contains(student)) {
                System.out.println("Student is already enrolled in this course!");
                return false;
            }
            
            if (enrolledStudents.size() < maxCapacity) {
                enrolledStudents.add(student);
                student.enrollCourse(this);
                return true;
            }
            return false;
        }

        /**
         * Calculates the average grade of all enrolled students in the course
         * @return The average grade as a percentage, or 0.0 if no students enrolled
         */
        public double calculateAverageGrade() {
            if (enrolledStudents.isEmpty()) return 0.0;
            double sum = 0.0;
            for (Student student : enrolledStudents) {
                sum += student.getGrade(this);
            }
            return sum / enrolledStudents.size();
        }
    }

    static interface Administrator {
        void addStudent(String name);
        void addCourse(String courseName, int maxCapacity);
        void enrollStudentInCourse(int studentId, int courseId);
        void updateStudentGrade(int studentId, int courseId, double grade);
        void displayStudentInfo(int studentId);
        void displayCourseInfo(int courseId);
        void displayAllStudents();
        void displayAllCourses();
    }

    static class CourseManagement implements Administrator {
        private static CourseManagement instance;  // Singleton instance
        private Map<Integer, Student> students;    // Repository of all students, keyed by ID
        private Map<Integer, Course> courses;      // Repository of all courses, keyed by ID

        private CourseManagement() {
            students = new HashMap<>();
            courses = new HashMap<>();
        }

        public static CourseManagement getInstance() {
            if (instance == null) {
                instance = new CourseManagement();
            }
            return instance;
        }

        @Override
        public void addStudent(String name) {
            try {
                Student student = new Student(name);
                students.put(student.getStudentId(), student);
                System.out.println("Student added successfully. ID: " + student.getStudentId());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void addCourse(String courseName, int maxCapacity) {
            try {
                Course course = new Course(courseName, maxCapacity);
                courses.put(course.getCourseId(), course);
                System.out.println("Course added successfully. ID: " + course.getCourseId());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void enrollStudentInCourse(int studentId, int courseId) {
            Student student = students.get(studentId);
            Course course = courses.get(courseId);
            
            if (student == null || course == null) {
                System.out.println("Student or course not found!");
                return;
            }

            if (course.enrollStudent(student)) {
                System.out.println("Enrollment successful!");
            } else {
                System.out.println("Course is full or student already enrolled!");
            }
        }

        @Override
        public void updateStudentGrade(int studentId, int courseId, double grade) {
            Student student = students.get(studentId);
            Course course = courses.get(courseId);
            
            if (student == null || course == null) {
                System.out.println("Student or course not found!");
                return;
            }

            try {
                student.updateGrade(course, grade);
                System.out.println("Grade updated successfully!");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void displayStudentInfo(int studentId) {
            Student student = students.get(studentId);
            if (student == null) {
                System.out.println("Student not found!");
                return;
            }

            System.out.println("\nStudent Information:");
            System.out.println("ID: " + student.getStudentId());
            System.out.println("Name: " + student.getName());
            System.out.println("Enrolled Courses and Grades:");
            for (Map.Entry<Course, Double> entry : student.getEnrolledCourses().entrySet()) {
                System.out.printf("%s (ID: %d): %.2f%%\n", 
                    entry.getKey().getCourseName(), 
                    entry.getKey().getCourseId(), 
                    entry.getValue());
            }
        }

        @Override
        public void displayCourseInfo(int courseId) {
            Course course = courses.get(courseId);
            if (course == null) {
                System.out.println("Course not found!");
                return;
            }

            System.out.println("\nCourse Information:");
            System.out.println("ID: " + course.getCourseId());
            System.out.println("Name: " + course.getCourseName());
            System.out.println("Average Grade: " + String.format("%.2f%%", course.calculateAverageGrade()));
            System.out.println("Enrolled Students:");
            for (Student student : course.getEnrolledStudents()) {
                System.out.printf("%s (ID: %d): %.2f%%\n", 
                    student.getName(), 
                    student.getStudentId(), 
                    student.getGrade(course));
            }
        }

        @Override
        public void displayAllStudents() {
            System.out.println("\nAll Students:");
            for (Student student : students.values()) {
                System.out.printf("ID: %d, Name: %s\n", 
                    student.getStudentId(), 
                    student.getName());
            }
        }

        @Override
        public void displayAllCourses() {
            System.out.println("\nAll Courses:");
            for (Course course : courses.values()) {
                System.out.printf("ID: %d, Name: %s, Average Grade: %.2f%%\n", 
                    course.getCourseId(), 
                    course.getCourseName(), 
                    course.calculateAverageGrade());
            }
        }
    }

    // Scanner for reading user input from console
    private static Scanner scanner = new Scanner(System.in);
    // Single instance of the course management system
    private static CourseManagement system = CourseManagement.getInstance();

    /**
     * Main program loop that displays menu and processes user choices
     */
    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:  // Add a new student
                    System.out.print("Enter student name: ");
                    String studentName = scanner.nextLine().trim();
                    if (studentName.isEmpty()) {
                        System.out.println("Student name cannot be empty.");
                        break;
                    }
                    system.addStudent(studentName);
                    break;
                case 2:  // Add a new course
                    System.out.print("Enter course name: ");
                    String courseName = scanner.nextLine().trim();
                    if (courseName.isEmpty()) {
                        System.out.println("Course name cannot be empty.");
                        break;
                    }
                    int maxCapacity = getIntInput("Enter maximum capacity: ");
                    system.addCourse(courseName, maxCapacity);
                    break;
                case 3:  // Enroll student in course
                    int studentId = getIntInput("Enter student ID: ");
                    int courseId = getIntInput("Enter course ID: ");
                    system.enrollStudentInCourse(studentId, courseId);
                    break;
                case 4:  // Update student's grade
                    studentId = getIntInput("Enter student ID: ");
                    courseId = getIntInput("Enter course ID: ");
                    double grade = getDoubleInput("Enter grade (0-100): ");
                    system.updateStudentGrade(studentId, courseId, grade);
                    break;
                case 5:  // Display all students
                    system.displayAllStudents();
                    break;
                case 6:  // Display all courses
                    system.displayAllCourses();
                    break;
                case 7:  // Display specific student info
                    studentId = getIntInput("Enter student ID: ");
                    system.displayStudentInfo(studentId);
                    break;
                case 8:  // Display specific course info
                    courseId = getIntInput("Enter course ID: ");
                    system.displayCourseInfo(courseId);
                    break;
                case 9:  // Exit the program
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Displays the main menu options to the user
     */
    private static void displayMenu() {
        System.out.println("\n=== University Course Management System ===");
        System.out.println("1. Add Student");
        System.out.println("2. Add Course");
        System.out.println("3. Enroll Student in Course");
        System.out.println("4. Update Student Grade");
        System.out.println("5. Display All Students");
        System.out.println("6. Display All Courses");
        System.out.println("7. Display Student Information");
        System.out.println("8. Display Course Information");
        System.out.println("9. Exit");
    }

    /**
     * Safely reads an integer input from the user with error handling
     * For course capacity, ensures the value is positive
     * @param prompt The message to display to the user
     * @return The validated integer input
     */
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                if (prompt.toLowerCase().contains("capacity") && value < 1) {
                    System.out.println("Capacity must be at least 1.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /**
     * Safely reads a double input from the user with error handling
     * @param prompt The message to display to the user
     * @return The validated double input between 0 and 100
     */
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine());
                if (value < 0 || value > 100) {
                    System.out.println("Please enter a number between 0 and 100.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
