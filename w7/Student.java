package w7;

import java.util.*;

/*
 * Student class represents a student in the Student Management System.
 * Each student has a unique ID, name, and can be enrolled in multiple courses.
 * The class manages the student's course enrollments and grades.
 * 
 * @author Cory Janowski
 * @version 1.0
 */
public class Student {
    /** Unique identifier for the student */
    private String id;
    
    /** Student's full name */
    private String name;
    
    /** 
     * Maps Course objects to grade strings.
     * Empty string represents no grade assigned.
     */
    private Map<Course, String> courseGrades;

    /**
     * Creates a new Student with specified ID and name.
     * Initializes an empty course-grade mapping.
     *
     * @param id The unique identifier for the student
     * @param name The student's full name
     * @throws IllegalArgumentException if id or name is null or empty
     */
    public Student(String id, String name) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be null or empty");
        }
        this.id = id.trim();
        this.name = name.trim();
        this.courseGrades = new HashMap<>();
    }

    /**
     * Gets the student's ID.
     * @return The student's unique identifier
     */
    public String getId() { 
        return id; 
    }

    /**
     * Gets the student's name.
     * @return The student's full name
     */
    public String getName() { 
        return name; 
    }

    /**
     * Gets the mapping of courses to grades.
     * @return Unmodifiable map of courses to grades
     */
    public Map<Course, String> getCourseGrades() { 
        return Collections.unmodifiableMap(courseGrades); 
    }

    /**
     * Enrolls the student in a specified course.
     * Initially assigns an empty string as grade.
     *
     * @param course The course to enroll in
     * @throws IllegalArgumentException if course is null
     */
    public void enrollInCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        courseGrades.put(course, "");
    }

    /**
     * Assigns a grade to a course the student is enrolled in.
     *
     * @param course The course to grade
     * @param grade The grade to assign
     * @throws IllegalArgumentException if course is null or student isn't enrolled
     */
    public void assignGrade(Course course, String grade) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (!courseGrades.containsKey(course)) {
            throw new IllegalArgumentException("Student is not enrolled in this course");
        }
        courseGrades.put(course, grade != null ? grade : "");
    }

    /**
     * Removes the student from a specified course.
     *
     * @param course The course to drop
     * @throws IllegalArgumentException if course is null
     */
    public void dropCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        courseGrades.remove(course);
    }

    /**
     * Gets the set of courses the student is enrolled in.
     *
     * @return Unmodifiable set of enrolled courses
     */
    public Set<Course> getCoursesEnrolled() {
        return Collections.unmodifiableSet(courseGrades.keySet());
    }

    /**
     * Returns a string representation of the student.
     *
     * @return String in format "id - name"
     */
    @Override
    public String toString() {
        return id + " - " + name;
    }
}