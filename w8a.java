/**
 * Employee Management System
 * 
 * This program processes employee data from a CSV file or uses sample data if no file is provided.
 * 
 * USAGE:
 * 1. Compile: javac w8a.java
 * 2. Run with file: java w8a employees.csv
 * 3. Run without file: java w8a (uses sample data)
 * 
 * FILE FORMAT:
 * - CSV file with optional header row
 * - Each line should contain: name,age,department,salary
 * - Example: John Doe,30,Engineering,75000
 * - Fields may be quoted: "John Doe",30,"Engineering",75000
 * 
 * DATA REQUIREMENTS:
 * - name: non-empty string
 * - age: positive integer
 * - department: non-empty string
 * - salary: positive number
 * 
 * FEATURES:
 * - Robust CSV parsing with error handling
 * - Supports quoted values
 * - Skips invalid records with error messages
 * - Calculates various salary statistics
 * - Uses Java streams for data processing
 */

/*
 * Example csv format should be
 * John Doe,30,Engineering,75000
 * Jane Smith,25,Marketing,65000
 * Place data in a file and pass the file name as an argument
 * If no file is provided, sample data will be used
 * java Main employees.csv
 */


 import java.io.BufferedReader;
 import java.io.FileReader;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.function.Function;
 import java.util.function.Predicate;
 import java.util.stream.Collectors;
 
 /**
  * Represents an employee with basic information.
  * Immutable class storing employee details.
  */
 class Employee {
     private String name;
     private int age;
     private String department;
     private double salary;
 
     public Employee(String name, int age, String department, double salary) {
         this.name = name;
         this.age = age;
         this.department = department;
         this.salary = salary;
     }
 
     // Getters
     public String getName() { return name; }
     public int getAge() { return age; }
     public String getDepartment() { return department; }
     public double getSalary() { return salary; }
 
     @Override
     public String toString() {
         return String.format("%s (Age: %d, Dept: %s, Salary: $%.2f)", 
             name, age, department, salary);
     }
 }
 
 /**
  * Handles employee data processing and analysis.
  * Provides functionality for loading, filtering, and analyzing employee data.
  */
 class EmployeeProcessor {
     private List<Employee> employees;
 
     public EmployeeProcessor() {
         this.employees = new ArrayList<>();
     }
 
     /**
      * Loads employee data from a CSV file.
      * 
      * @param filename Path to the CSV file
      * @throws IOException If file reading errors occur
      * 
      * Features:
      * - Skips empty lines
      * - Detects and skips header row
      * - Validates data format and values
      * - Provides detailed error messages for invalid records
      * - Handles quoted values in CSV
      */
     public void loadFromFile(String filename) throws IOException {
         try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
             String line;
             boolean isFirstLine = true;
             int lineNumber = 0;
 
             while ((line = reader.readLine()) != null) {
                 lineNumber++;
                 
                 // Skip empty lines
                 if (line.trim().isEmpty()) {
                     continue;
                 }
 
                 // Skip header row
                 if (isFirstLine) {
                     isFirstLine = false;
                     if (line.toLowerCase().contains("name") && 
                         line.toLowerCase().contains("age") && 
                         line.toLowerCase().contains("department") && 
                         line.toLowerCase().contains("salary")) {
                         continue;
                     }
                 }
 
                 try {
                     // Split on comma, handling possible quoted values
                     String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                     
                     if (parts.length != 4) {
                         System.err.printf("Line %d: Expected 4 values, found %d values. Skipping line: %s%n", 
                             lineNumber, parts.length, line);
                         continue;
                     }
 
                     // Remove quotes and trim
                     String name = parts[0].replaceAll("\"", "").trim();
                     String ageStr = parts[1].replaceAll("\"", "").trim();
                     String department = parts[2].replaceAll("\"", "").trim();
                     String salaryStr = parts[3].replaceAll("\"", "").trim();
 
                     // Validate data
                     if (name.isEmpty() || department.isEmpty()) {
                         System.err.printf("Line %d: Name and department cannot be empty. Skipping line: %s%n", 
                             lineNumber, line);
                         continue;
                     }
 
                     int age = Integer.parseInt(ageStr);
                     double salary = Double.parseDouble(salaryStr);
 
                     if (age < 0 || salary < 0) {
                         System.err.printf("Line %d: Age and salary must be positive. Skipping line: %s%n", 
                             lineNumber, line);
                         continue;
                     }
 
                     Employee emp = new Employee(name, age, department, salary);
                     addEmployee(emp);
                     System.out.printf("Successfully loaded employee from line %d: %s%n", lineNumber, emp);
 
                 } catch (NumberFormatException e) {
                     System.err.printf("Line %d: Error parsing numbers in line: %s%n", lineNumber, line);
                 } catch (Exception e) {
                     System.err.printf("Line %d: Unexpected error processing line: %s%nError: %s%n", 
                         lineNumber, line, e.getMessage());
                 }
             }
             
             System.out.printf("%nSuccessfully loaded %d employees from file%n", employees.size());
         }
     }
 
     /**
      * Adds a single employee to the processor.
      * @param employee Employee object to add
      */
     public void addEmployee(Employee employee) {
         employees.add(employee);
     }
 
     /**
      * Function to combine employee name and department.
      * Used in stream operations for employee info display.
      */
     public Function<Employee, String> concatenateNameAndDepartment = 
         employee -> employee.getName() + " - " + employee.getDepartment();
 
     /**
      * Returns a list of employee information strings.
      * Format: "name - department"
      * @return List of formatted employee information
      */
     public List<String> getEmployeeInfo() {
         return employees.stream()
                        .map(concatenateNameAndDepartment)
                        .collect(Collectors.toList());
     }
 
     /**
      * Calculates average salary for employees above specified age.
      * @param ageThreshold Minimum age to include in calculation
      * @return Average salary or 0.0 if no matching employees
      */
     public double getAverageSalaryAboveAge(int ageThreshold) {
         return employees.stream()
                        .filter(emp -> emp.getAge() > ageThreshold)
                        .mapToDouble(Employee::getSalary)
                        .average()
                        .orElse(0.0);
     }
 
     /**
      * Calculates average salary based on custom filter.
      * @param filter Predicate to filter employees
      * @return Average salary of filtered employees or 0.0 if none match
      */
     public double getAverageSalaryWithFilter(Predicate<Employee> filter) {
         return employees.stream()
                        .filter(filter)
                        .mapToDouble(Employee::getSalary)
                        .average()
                        .orElse(0.0);
     }
 }
 
 /**
  * Main class demonstrating employee data processing functionality.
  * 
  * Example Usage:
  * 1. Process file: java w8a path/to/employees.csv
  * 2. Use sample data: java w8a
  * 
  * Output includes:
  * - List of all employees (name - department)
  * - Average salary for employees over 30
  * - Average salary for Engineering department
  * - Average salary for engineers over 30
  */
 public class w8a {
     public static void main(String[] args) {
         EmployeeProcessor processor = new EmployeeProcessor();
 
         if (args.length > 0) {
             try {
                 System.out.println("Attempting to load employees from file: " + args[0]);
                 processor.loadFromFile(args[0]);
             } catch (IOException e) {
                 System.err.println("Error reading file: " + e.getMessage());
                 System.err.println("Please ensure the file exists and is in CSV format with columns: name,age,department,salary");
                 return;
             }
         } else {
             System.out.println("No input file provided. Using sample data...\n");
             processor.addEmployee(new Employee("John Doe", 30, "Engineering", 75000));
             processor.addEmployee(new Employee("Jane Smith", 25, "Marketing", 65000));
             processor.addEmployee(new Employee("Bob Johnson", 45, "Engineering", 95000));
             processor.addEmployee(new Employee("Alice Brown", 35, "HR", 70000));
         }
 
         // Only proceed with analysis if we have employees
         if (processor.getEmployeeInfo().isEmpty()) {
             System.err.println("No valid employees loaded. Please check your input file format.");
             System.err.println("Expected CSV format: name,age,department,salary");
             System.err.println("Example: John Doe,30,Engineering,75000");
             return;
         }
 
         System.out.println("\nEmployee Information:");
         List<String> employeeInfo = processor.getEmployeeInfo();
         employeeInfo.forEach(System.out::println);
 
         System.out.println("\nAverage salary for employees above 30:");
         System.out.printf("$%.2f%n", processor.getAverageSalaryAboveAge(30));
 
         System.out.println("\nAverage salary for Engineering department:");
         double avgEngineeringSalary = processor.getAverageSalaryWithFilter(
             emp -> emp.getDepartment().equals("Engineering")
         );
         System.out.printf("$%.2f%n", avgEngineeringSalary);
 
         System.out.println("\nAverage salary for employees above 30 in Engineering:");
         double avgSalaryFiltered = processor.getAverageSalaryWithFilter(
             emp -> emp.getAge() > 30 && emp.getDepartment().equals("Engineering")
         );
         System.out.printf("$%.2f%n", avgSalaryFiltered);
     }
 }