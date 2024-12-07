import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Student class represents a single student entity.
 * This class follows the encapsulation principle by keeping all fields private
 * and providing access through getter and setter methods.
 */
class Student {
    private int id;            // Unique identifier for each student
    private String name;       // Student's full name
    private int age;          // Student's age
    private double grade;     // Student's grade (0-100)

    /**
     * Constructor to create a new Student object with all required fields.
     * @param id Unique identifier for the student
     * @param name Student's name
     * @param age Student's age
     * @param grade Student's grade
     */
    public Student(int id, String name, int age, double grade) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    // Getter methods
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getGrade() { return grade; }

    // Setter methods
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setGrade(double grade) { this.grade = grade; }

    /**
     * Provides a string representation of the Student object.
     * @return Formatted string containing all student information
     */
    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Age: %d, Grade: %.2f", id, name, age, grade);
    }
}

/**
 * StudentRepository handles data storage and basic CRUD operations.
 * This class implements the Repository pattern to abstract the data storage mechanism.
 */
class StudentRepository {
    private Map<Integer, Student> students;    // Storage for student records
    private int nextId;                        // Auto-increment counter for student IDs

    /**
     * Constructor initializes the storage and adds a default student.
     */
    public StudentRepository() {
        this.students = new HashMap<>();
        this.nextId = 2;  // Start at 2 since we have a default student
        
        // Add default student (Cory)
        students.put(1, new Student(1, "Cory Janowski", 38, 4.0));
    }

    /**
     * Adds a new student to the repository.
     * @param name Student's name
     * @param age Student's age
     * @param grade Student's grade
     * @return The ID of the newly created student
     */
    public int add(String name, int age, double grade) {
        int id = nextId++;
        students.put(id, new Student(id, name, age, grade));
        return id;
    }

    /**
     * Removes a student from the repository.
     * @param id ID of the student to remove
     * @return true if student was removed, false if student wasn't found
     */
    public boolean remove(int id) {
        return students.remove(id) != null;
    }

    /**
     * Retrieves a student by their ID.
     * @param id ID of the student to retrieve
     * @return Student object if found, null otherwise
     */
    public Student get(int id) {
        return students.get(id);
    }

    /**
     * Retrieves all students in the repository.
     * @return ArrayList containing all students
     */
    public ArrayList<Student> getAll() {
        return new ArrayList<>(students.values());
    }

    /**
     * Updates an existing student's information.
     * @param id ID of the student to update
     * @param updatedStudent New student information
     * @return true if update was successful, false if student wasn't found
     */
    public boolean update(int id, Student updatedStudent) {
        if (!students.containsKey(id)) return false;
        students.put(id, updatedStudent);
        return true;
    }
}

/**
 * StudentService provides business logic and validation.
 * This class acts as an intermediary between the UI and data layers,
 * implementing the Service layer pattern.
 */
class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    /**
     * Adds a new student after validating the input data.
     * @throws IllegalArgumentException if any input data is invalid
     */
    public int addStudent(String name, int age, double grade) {
        validateStudentData(name, age, grade);
        return repository.add(name, age, grade);
    }

    /**
     * Updates an existing student's information after validation.
     * @return true if update was successful, false if student wasn't found
     * @throws IllegalArgumentException if any input data is invalid
     */
    public boolean updateStudent(int id, String name, int age, double grade) {
        validateStudentData(name, age, grade);
        Student student = repository.get(id);
        if (student == null) return false;
        
        student.setName(name);
        student.setAge(age);
        student.setGrade(grade);
        return repository.update(id, student);
    }

    // Other service methods
    public boolean removeStudent(int id) {
        return repository.remove(id);
    }

    public Student getStudent(int id) {
        return repository.get(id);
    }

    public ArrayList<Student> getAllStudents() {
        return repository.getAll();
    }

    /**
     * Searches for students whose names contain the given search term (case-insensitive).
     * @param name Search term to look for in student names
     * @return ArrayList of matching students
     */
    public ArrayList<Student> searchByName(String name) {
        ArrayList<Student> results = new ArrayList<>();
        if (name == null || name.trim().isEmpty()) return results;
        
        String searchTerm = name.toLowerCase();
        for (Student student : repository.getAll()) {
            if (student.getName().toLowerCase().contains(searchTerm)) {
                results.add(student);
            }
        }
        return results;
    }

    /**
     * Calculates the average grade of all students.
     * @return Average grade, or 0.0 if no students exist
     */
    public double getAverageGrade() {
        ArrayList<Student> students = repository.getAll();
        if (students.isEmpty()) return 0.0;
        
        double sum = 0.0;
        for (Student student : students) {
            sum += student.getGrade();
        }
        return sum / students.size();
    }

    /**
     * Validates student data before operations.
     * @throws IllegalArgumentException if any validation fails
     */
    private void validateStudentData(String name, int age, double grade) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        if (grade < 0.0 || grade > 100.0) {
            throw new IllegalArgumentException("Grade must be between 0 and 100");
        }
    }
}

/**
 * StudentView handles the user interface components and user interactions.
 * This class implements the View part of the MVC pattern.
 */
class StudentView extends JPanel {
    // UI Components
    private final JTextField nameField, ageField, gradeField, idField, searchField;
    private final JTextArea displayArea;
    private final StudentService service;

    /**
     * Constructor sets up the UI components and layouts.
     * @param service StudentService instance for handling business logic
     */
    public StudentView(StudentService service) {
        this.service = service;
        setLayout(new BorderLayout(10, 10));

        // Initialize components
        nameField = new JTextField(20);
        ageField = new JTextField(20);
        gradeField = new JTextField(20);
        idField = new JTextField(20);
        searchField = new JTextField(20);
        displayArea = new JTextArea(10, 40);
        displayArea.setEditable(false);

        setupUI();
        displayAllStudents(); // Show initial data including default student
    }

    /**
     * Sets up the UI layout and components.
     */
    private void setupUI() {
        // Input Panel setup
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Age:"));
        inputPanel.add(ageField);
        inputPanel.add(new JLabel("Grade:"));
        inputPanel.add(gradeField);
        inputPanel.add(new JLabel("ID (for update/delete):"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Search by name:"));
        inputPanel.add(searchField);

        // Button Panel setup
        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton(buttonPanel, "Add Student", _ -> addStudent());
        addButton(buttonPanel, "Update Student", _ -> updateStudent());
        addButton(buttonPanel, "Delete Student", _ -> deleteStudent());
        addButton(buttonPanel, "Search", _ -> searchStudents());
        addButton(buttonPanel, "Display All", _ -> displayAllStudents());
        addButton(buttonPanel, "Show Average", _ -> showAverageGrade());

        // Main layout assembly
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    /**
     * Helper method to create and add buttons with action listeners.
     */
    private void addButton(JPanel panel, String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        panel.add(button);
    }

    // UI Action Methods

    /**
     * Handles the add student action.
     * Validates input and displays appropriate success/error messages.
     */
    private void addStudent() {
        try {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            double grade = Double.parseDouble(gradeField.getText());
            
            int id = service.addStudent(name, age, grade);
            displayArea.setText("Student added successfully with ID: " + id);
            clearInputFields();
            displayAllStudents();
        } catch (NumberFormatException ex) {
            displayArea.setText("Error: Please enter valid numbers for age and grade");
        } catch (IllegalArgumentException ex) {
            displayArea.setText("Error: " + ex.getMessage());
        }
    }

    /**
     * Handles the update student action.
     * Validates input and displays appropriate success/error messages.
     */
    private void updateStudent() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            double grade = Double.parseDouble(gradeField.getText());
            
            boolean success = service.updateStudent(id, name, age, grade);
            if (success) {
                displayArea.setText("Student updated successfully");
                displayAllStudents();
            } else {
                displayArea.setText("Error: Student not found");
            }
            clearInputFields();
        } catch (NumberFormatException ex) {
            displayArea.setText("Error: Please enter valid numbers");
        }
    }

    /**
     * Handles the delete student action.
     * Validates input and displays appropriate success/error messages.
     */
    private void deleteStudent() {
        try {
            int id = Integer.parseInt(idField.getText());
            boolean success = service.removeStudent(id);
            if (success) {
                displayArea.setText("Student deleted successfully");
                displayAllStudents();
            } else {
                displayArea.setText("Error: Student not found");
            }
            clearInputFields();
        } catch (NumberFormatException ex) {
            displayArea.setText("Error: Please enter a valid ID");
        }
    }

    /**
     * Handles the search action.
     * Displays students matching the search criteria.
     */
    private void searchStudents() {
        String searchTerm = searchField.getText();
        ArrayList<Student> results = service.searchByName(searchTerm);
        displayResults(results);
    }

    /**
     * Displays all students in the system.
     */
    private void displayAllStudents() {
        ArrayList<Student> students = service.getAllStudents();
        displayResults(students);
    }

    /**
     * Helper method to display a list of students in the display area.
     */
    private void displayResults(ArrayList<Student> students) {
        if (students.isEmpty()) {
            displayArea.setText("No students found");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Student student : students) {
            sb.append(student.toString()).append("\n");
        }
        displayArea.setText(sb.toString());
    }

    /**
     * Displays the average grade of all students.
     */
    private void showAverageGrade() {
        double average = service.getAverageGrade();
        displayArea.setText(String.format("Average grade: %.2f", average));
    }

    /**
     * Clears all input fields.
     */
    private void clearInputFields() {
        nameField.setText("");
        ageField.setText("");
        gradeField.setText("");
        idField.setText("");
    }
}

/**
 * Main application class that sets up the window and initializes the system.
 * This class serves as the application entry point and main window.
 */
public class w3a extends JFrame {
    /**
     * Constructor sets up the main application window and initializes all components.
     */
    public w3a() {
        super("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Initialize system components with dependency injection
        StudentRepository repository = new StudentRepository();
        StudentService service = new StudentService(repository);
        StudentView view = new StudentView(service);
        add(view);
    }

    /**
     * Application entry point.
     * Creates and displays the main application window on the Event Dispatch Thread.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new w3a().setVisible(true);
        });
    }
}