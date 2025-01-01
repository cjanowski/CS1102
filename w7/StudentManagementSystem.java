package w7;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * StudentManagementSystem is the main GUI application class for managing students and courses.
 * It provides a graphical interface for adding/viewing students, managing courses,
 * handling enrollments, and assigning grades.
 * 
 * @author Cory Janowski
 * @version 1.0
 */
@SuppressWarnings("unused")  // Suppress warnings for unused lambda parameters
public class StudentManagementSystem extends JFrame {
    /** List of all students in the system */
    private final java.util.List<Student> students = new ArrayList<>();
    
    /** List of all available courses */
    private final java.util.List<Course> courses = new ArrayList<>();
    
    /** Table components for displaying student and course data */
    private JTable studentTable;
    private JTable courseTable;
    
    /** Table models for managing student and course data display */
    private final DefaultTableModel studentTableModel = new DefaultTableModel(
        new String[]{"ID", "Name", "Courses Enrolled"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }
    };
    
    private final DefaultTableModel courseTableModel = new DefaultTableModel(
        new String[]{"ID", "Name", "Enrolled/Max"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }
    };
    
    /** Dropdown components for student and course selection */
    private JComboBox<Student> studentComboBox;
    private JComboBox<Course> courseComboBox;
    
    /** Main panel containing all GUI components */
    private JPanel mainPanel;

    /**
     * Constructs the main application window and initializes all components.
     * Sets up the menu bar, creates the layout, and adds sample data.
     */
    public StudentManagementSystem() {
        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        createMenuBar();
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        createTopPanel();
        createCenterPanel();
        add(mainPanel);
        addSampleData();
    }

    /**
     * Adds sample courses to the system for testing purposes.
     */
    private void addSampleData() {
        courses.add(new Course("CS1102", "Programming I", 30));
        courses.add(new Course("CS1103", "Programming II", 25));
        courses.add(new Course("CS1104", "Database I", 20));
        updateCourseComboBox();
    }

    /**
     * Creates and configures the main menu bar with File, Student, and Course menus.
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu studentMenu = new JMenu("Student");
        JMenu courseMenu = new JMenu("Course");

        // File menu items
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        // Student menu items
        JMenuItem addStudentItem = new JMenuItem("Add Student");
        JMenuItem enrollStudentItem = new JMenuItem("Enroll Student");
        
        addStudentItem.addActionListener(e -> showAddStudentDialog());
        enrollStudentItem.addActionListener(e -> showEnrollStudentDialog());

        studentMenu.add(addStudentItem);
        studentMenu.add(enrollStudentItem);

        // Course menu items
        JMenuItem addCourseItem = new JMenuItem("Add Course");
        JMenuItem manageCourseItem = new JMenuItem("Manage Course");
        
        addCourseItem.addActionListener(e -> showAddCourseDialog());
        manageCourseItem.addActionListener(e -> showManageCourseDialog());

        courseMenu.add(addCourseItem);
        courseMenu.add(manageCourseItem);

        menuBar.add(fileMenu);
        menuBar.add(studentMenu);
        menuBar.add(courseMenu);
        setJMenuBar(menuBar);
    }

    /**
     * Creates the top panel containing student/course selection and action buttons.
     */
    private void createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        studentComboBox = new JComboBox<>();
        courseComboBox = new JComboBox<>();
        
        JButton enrollButton = new JButton("Enroll");
        JButton assignGradeButton = new JButton("Assign Grade");
        JButton viewDetailsButton = new JButton("View Details");

        enrollButton.addActionListener(e -> showEnrollStudentDialog());
        assignGradeButton.addActionListener(e -> showAssignGradeDialog());
        viewDetailsButton.addActionListener(e -> showStudentDetailsDialog());

        topPanel.add(new JLabel("Student: "));
        topPanel.add(studentComboBox);
        topPanel.add(new JLabel("Course: "));
        topPanel.add(courseComboBox);
        topPanel.add(enrollButton);
        topPanel.add(assignGradeButton);
        topPanel.add(viewDetailsButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);
    }

    /**
     * Creates the center panel with student and course tables.
     */
    private void createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        // Students table
        studentTable = new JTable(studentTableModel);
        JScrollPane studentScrollPane = new JScrollPane(studentTable);
        studentScrollPane.setBorder(BorderFactory.createTitledBorder("Students"));

        // Courses table
        courseTable = new JTable(courseTableModel);
        JScrollPane courseScrollPane = new JScrollPane(courseTable);
        courseScrollPane.setBorder(BorderFactory.createTitledBorder("Courses"));

        centerPanel.add(studentScrollPane);
        centerPanel.add(courseScrollPane);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Displays dialog for adding a new student to the system.
     * Validates input and updates relevant components upon successful addition.
     */
    private void showAddStudentDialog() {
        JDialog dialog = new JDialog(this, "Add Student", true);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(this);

        // Create text fields with columns specified
        JTextField idField = new JTextField(20);
        JTextField nameField = new JTextField(20);

        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Add ID label and field
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Student ID:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(idField, gbc);
        
        // Add Name label and field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Name:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(nameField, gbc);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            if (idField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, 
                    "Please fill in all fields", 
                    "Validation Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                if (validateStudentInputs(idField.getText(), nameField.getText())) {
                    Student student = new Student(idField.getText(), nameField.getText());
                    students.add(student);
                    updateStudentComboBox();
                    refreshTables();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this, "Student added successfully!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error adding student: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Add panels to dialog
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setVisible(true);
    }

    private void showAddCourseDialog() {
        JDialog dialog = new JDialog(this, "Add Course", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);

        JTextField idField = new JTextField(20);
        JTextField nameField = new JTextField(20);
        JSpinner maxStudentsSpinner = new JSpinner(new SpinnerNumberModel(30, 1, 100, 1));

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        addFormField(dialog, "Course ID:", idField, gbc, 0);
        addFormField(dialog, "Name:", nameField, gbc, 1);
        addFormField(dialog, "Max Students:", maxStudentsSpinner, gbc, 2);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            try {
                if (validateCourseInputs(idField.getText(), nameField.getText())) {
                    Course course = new Course(idField.getText(), nameField.getText(), 
                        (Integer) maxStudentsSpinner.getValue());
                    courses.add(course);
                    updateCourseComboBox();
                    refreshTables();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this, "Course added successfully!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding course: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(buttonPanel, gbc);

        dialog.setVisible(true);
    }

    private void showEnrollStudentDialog() {
        if (studentComboBox.getSelectedItem() == null || courseComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select both a student and a course.");
            return;
        }

        Student student = (Student) studentComboBox.getSelectedItem();
        Course course = (Course) courseComboBox.getSelectedItem();

        try {
            if (course.enrollStudent(student)) {
                student.enrollInCourse(course);
                refreshTables();
                JOptionPane.showMessageDialog(this, "Student enrolled successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Course is full or student already enrolled.",
                    "Enrollment Failed", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error enrolling student: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAssignGradeDialog() {
        if (studentComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a student.");
            return;
        }

        Student student = (Student) studentComboBox.getSelectedItem();
        if (student.getCourseGrades().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Student is not enrolled in any courses.");
            return;
        }

        JDialog dialog = new JDialog(this, "Assign Grade", true);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(this);

        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Course selection
        JComboBox<Course> courseSelect = new JComboBox<>(
            student.getCourseGrades().keySet().toArray(new Course[0]));
        JComboBox<String> gradeSelect = new JComboBox<>(new String[]{"A", "B", "C", "D", "F"});

        // Add components to form
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Course:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(courseSelect, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Grade:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(gradeSelect, gbc);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            try {
                Course course = (Course) courseSelect.getSelectedItem();
                String grade = (String) gradeSelect.getSelectedItem();
                if (validateGradeInputs(course, grade)) {
                    student.assignGrade(course, grade);
                    refreshTables();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this, "Grade assigned successfully!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error assigning grade: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Add panels to dialog
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setVisible(true);
    }

    private void showStudentDetailsDialog() {
        if (studentComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a student.");
            return;
        }

        Student student = (Student) studentComboBox.getSelectedItem();
        JDialog dialog = new JDialog(this, "Student Details", true);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(this);

        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create and populate text fields
        JTextField idField = new JTextField(student.getId());
        JTextField nameField = new JTextField(student.getName());
        JTextArea coursesArea = new JTextArea(5, 20);
        
        // Make fields read-only
        idField.setEditable(false);
        nameField.setEditable(false);
        coursesArea.setEditable(false);
        
        // Populate courses and grades
        StringBuilder courseDetails = new StringBuilder();
        for (Map.Entry<Course, String> entry : student.getCourseGrades().entrySet()) {
            courseDetails.append(entry.getKey().getName())
                        .append(": ")
                        .append(entry.getValue().isEmpty() ? "Not graded" : entry.getValue())
                        .append("\n");
        }
        coursesArea.setText(courseDetails.toString());
        
        // Add components to form
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Student ID:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(idField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Name:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(nameField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Courses & Grades:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JScrollPane scrollPane = new JScrollPane(coursesArea);
        formPanel.add(scrollPane, gbc);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(closeButton);

        // Add panels to dialog
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setVisible(true);
    }

    private void showManageCourseDialog() {
        // Implementation for managing courses
    }

    /**
     * Updates the tables with current student and course data.
     * Clears existing data and repopulates with current information.
     */
    private void refreshTables() {
        studentTableModel.setRowCount(0);
        courseTableModel.setRowCount(0);

        for (Student student : students) {
            studentTableModel.addRow(new Object[]{
                student.getId(), 
                student.getName(), 
                String.valueOf(student.getCoursesEnrolled().size())
            });
        }

        for (Course course : courses) {
            courseTableModel.addRow(new Object[]{
                course.getId(), 
                course.getName(), 
                course.getEnrolledStudents().size() + "/" + course.getMaxStudents()
            });
        }
    }

    /**
     * Updates the student selection dropdown with current student list.
     */
    private void updateStudentComboBox() {
        studentComboBox.removeAllItems();
        for (Student student : students) {
            studentComboBox.addItem(student);
        }
    }

    /**
     * Updates the course selection dropdown with current course list.
     */
    private void updateCourseComboBox() {
        courseComboBox.removeAllItems();
        for (Course course : courses) {
            courseComboBox.addItem(course);
        }
    }

    private void addFormField(JDialog dialog, String label, JComponent component, GridBagConstraints gbc, int x) {
        gbc.gridx = x;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        dialog.add(new JLabel(label), gbc);

        gbc.gridx = x;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(component, gbc);
    }

    private boolean validateStudentInputs(String id, String name) {
        // Implementation of validateStudentInputs method
        return true;
    }

    private boolean validateCourseInputs(String id, String name) {
        // Implementation of validateCourseInputs method
        return true;
    }

    private boolean validateGradeInputs(Course course, String grade) {
        // Implementation of validateGradeInputs method
        return true;
    }
}