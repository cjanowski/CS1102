package w7;

import java.util.*;

/**
 * Represents a course in the Student Management System.
 * Contains information about the course and manages enrolled students.
 */
public class Course {
    private String id;              // Unique identifier for the course
    private String name;            // Name of the course
    private int maxStudents;        // Maximum number of students allowed
    private List<Student> enrolledStudents;  // List of currently enrolled students

    /**
     * Constructs a new Course with specified details.
     * 
     * @param id Unique identifier for the course
     * @param name Name of the course
     * @param maxStudents Maximum number of students that can enroll
     */
    public Course(String id, String name, int maxStudents) {
        this.id = id;
        this.name = name;
        this.maxStudents = maxStudents;
        this.enrolledStudents = new ArrayList<>();
    }

    // Getter methods
    public String getId() { return id; }
    public String getName() { return name; }
    public int getMaxStudents() { return maxStudents; }
    public List<Student> getEnrolledStudents() { return enrolledStudents; }

    /**
     * Attempts to enroll a student in this course.
     * 
     * @param student The student to enroll
     * @return true if enrollment successful, false if course is full or student already enrolled
     */
    public boolean enrollStudent(Student student) {
        if (enrolledStudents.size() < maxStudents && !enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
            return true;
        }
        return false;
    }

    /**
     * Removes a student from this course.
     * 
     * @param student The student to remove
     */
    public void dropStudent(Student student) {
        enrolledStudents.remove(student);
    }

    /**
     * Returns a string representation of the course.
     * 
     * @return String in format "id - name"
     */
    @Override
    public String toString() {
        return id + " - " + name;
    }
}