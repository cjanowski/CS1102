import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Note: Non-public classes are used to allow all code to run in a single file
// For production, each class would typically be in its own file

// Base interface for all vehicles - defines common properties and behaviors
// that all vehicle types must implement
interface Vehicle {
    // Basic getters and setters for common vehicle properties
    String getMake();      // Manufacturer of the vehicle
    void setMake(String make);
    String getModel();     // Model name of the vehicle
    void setModel(String model);
    int getYear();        // Manufacturing year
    void setYear(int year);
    double getRentalRate(); // Daily rental cost
    void setRentalRate(double rate);
}

// Specific interface for car-specific properties and behaviors
// Implements additional features unique to cars
interface CarVehicle {
    int getNumberOfDoors();    // Number of doors (2-door, 4-door, etc.)
    void setNumberOfDoors(int doors);
    String getFuelType();      // Type of fuel (petrol/diesel/electric)
    void setFuelType(String fuelType);
}

// Specific interface for motorcycle-specific properties
// Defines characteristics unique to motorcycles
interface MotorVehicle {
    int getNumberOfWheels();    // Usually 2 or 3 for motorcycles
    void setNumberOfWheels(int wheels);
    String getMotorcycleType(); // Style of motorcycle (sport/cruiser/off-road)
    void setMotorcycleType(String type);
}

// Specific interface for truck-specific properties
// Defines characteristics unique to trucks
interface TruckVehicle {
    double getCargoCapacity();    // Cargo capacity in tons
    void setCargoCapacity(double capacity);
    String getTransmissionType(); // Transmission type (manual/automatic)
    void setTransmissionType(String transmission);
}

// Car class implementing both Vehicle and CarVehicle interfaces
// Represents a car in the rental system with all its specific properties
class Car implements Vehicle, CarVehicle {
    // Private fields to encapsulate car data
    private String make;          // Manufacturer (e.g., Toyota, Ford)
    private String model;         // Model name (e.g., Camry, Focus)
    private int year;            // Manufacturing year
    private int numberOfDoors;   // Number of doors
    private String fuelType;     // Fuel type
    private double rentalRate;   // Daily rental rate

    // Constructor to initialize all car properties
    public Car(String make, String model, int year, int numberOfDoors, String fuelType, double rentalRate) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.numberOfDoors = numberOfDoors;
        this.fuelType = fuelType;
        this.rentalRate = rentalRate;
    }

    // Implementation of all required interface methods
    // Each getter/setter provides access to private fields
    @Override
    public String getMake() { return make; }

    @Override
    public void setMake(String make) { this.make = make; }

    @Override
    public String getModel() { return model; }

    @Override
    public void setModel(String model) { this.model = model; }

    @Override
    public int getYear() { return year; }

    @Override
    public void setYear(int year) { this.year = year; }

    @Override
    public int getNumberOfDoors() { return numberOfDoors; }

    @Override
    public void setNumberOfDoors(int doors) { this.numberOfDoors = doors; }

    @Override
    public String getFuelType() { return fuelType; }

    @Override
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }

    @Override
    public double getRentalRate() { return rentalRate; }

    @Override
    public void setRentalRate(double rate) { this.rentalRate = rate; }

    // Override toString() to provide a formatted string representation of the car
    @Override
    public String toString() {
        return String.format("Car: %s %s (%d) - %d doors, %s fuel, $%.2f per day", 
            make, model, year, numberOfDoors, fuelType, rentalRate);
    }
}

class Motorcycle implements Vehicle, MotorVehicle {
    private String make;
    private String model;
    private int year;
    private int numberOfWheels;
    private String motorcycleType;
    private double rentalRate;

    public Motorcycle(String make, String model, int year, int numberOfWheels, 
                     String motorcycleType, double rentalRate) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.numberOfWheels = numberOfWheels;
        this.motorcycleType = motorcycleType;
        this.rentalRate = rentalRate;
    }

    @Override
    public String getMake() { return make; }

    @Override
    public void setMake(String make) { this.make = make; }

    @Override
    public String getModel() { return model; }

    @Override
    public void setModel(String model) { this.model = model; }

    @Override
    public int getYear() { return year; }

    @Override
    public void setYear(int year) { this.year = year; }

    @Override
    public int getNumberOfWheels() { return numberOfWheels; }

    @Override
    public void setNumberOfWheels(int wheels) { this.numberOfWheels = wheels; }

    @Override
    public String getMotorcycleType() { return motorcycleType; }

    @Override
    public void setMotorcycleType(String type) { this.motorcycleType = type; }

    @Override
    public double getRentalRate() { return rentalRate; }

    @Override
    public void setRentalRate(double rate) { this.rentalRate = rate; }

    @Override
    public String toString() {
        return String.format("Motorcycle: %s %s (%d) - %d wheels, %s type, $%.2f per day", 
            make, model, year, numberOfWheels, motorcycleType, rentalRate);
    }
}

class Truck implements Vehicle, TruckVehicle {
    private String make;
    private String model;
    private int year;
    private double cargoCapacity;
    private String transmissionType;
    private double rentalRate;

    public Truck(String make, String model, int year, double cargoCapacity, 
                String transmissionType, double rentalRate) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.cargoCapacity = cargoCapacity;
        this.transmissionType = transmissionType;
        this.rentalRate = rentalRate;
    }

    @Override
    public String getMake() { return make; }

    @Override
    public void setMake(String make) { this.make = make; }

    @Override
    public String getModel() { return model; }

    @Override
    public void setModel(String model) { this.model = model; }

    @Override
    public int getYear() { return year; }

    @Override
    public void setYear(int year) { this.year = year; }

    @Override
    public double getCargoCapacity() { return cargoCapacity; }

    @Override
    public void setCargoCapacity(double capacity) { this.cargoCapacity = capacity; }

    @Override
    public String getTransmissionType() { return transmissionType; }

    @Override
    public void setTransmissionType(String transmission) { this.transmissionType = transmission; }

    @Override
    public double getRentalRate() { return rentalRate; }

    @Override
    public void setRentalRate(double rate) { this.rentalRate = rate; }

    @Override
    public String toString() {
        return String.format("Truck: %s %s (%d) - %.1f tons capacity, %s transmission, $%.2f per day", 
            make, model, year, cargoCapacity, transmissionType, rentalRate);
    }
}

public class w6a {
    // Static list to store all vehicles in the rental system
    // Using List interface for flexibility in implementation
    private static List<Vehicle> vehicles = new ArrayList<>();
    
    // Single Scanner instance for all user input to prevent resource leaks
    // Shared across all methods that need user input
    private static Scanner scanner = new Scanner(System.in);

    // Main program entry point
    public static void main(String[] args) {
        while (true) {
            // Display main menu options
            System.out.println("\nCar Rental System");
            System.out.println("1. Add Vehicle");
            System.out.println("2. List All Vehicles");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                // Attempt to read numeric input for menu choice
                // Scanner.nextInt() can throw InputMismatchException
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the leftover newline character
            } catch (Exception e) {
                // Handle any non-numeric input gracefully
                // Prevents program crash on invalid input
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input from scanner
                continue; // Restart the loop to show menu again
            }

            // Process user's choice through switch statement
            switch (choice) {
                case 1:
                    addVehicle();  // Add a new vehicle to the system
                    break;
                case 2:
                    listVehicles(); // Display all vehicles in the system
                    break;
                case 3:
                    System.out.println("Thank you for using the Car Rental System!");
                    return;        // Exit the program
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Method to handle adding a new vehicle to the system
    private static void addVehicle() {
        // Display vehicle type selection menu
        System.out.println("\nAdd Vehicle");
        System.out.println("1. Car");
        System.out.println("2. Motorcycle");
        System.out.println("3. Truck");
        System.out.print("Choose vehicle type: ");

        int type;
        try {
            // Safely read vehicle type selection
            type = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Clear invalid input
            return; // Exit method on invalid input
        }

        // Collect common vehicle information
        System.out.print("Enter make: ");
        String make = scanner.nextLine();
        System.out.print("Enter model: ");
        String model = scanner.nextLine();
        
        int year;
        double rentalRate;
        
        try {
            // Safely read numeric vehicle details
            System.out.print("Enter year: ");
            year = scanner.nextInt();
            System.out.print("Enter rental rate per day: ");
            rentalRate = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
        } catch (Exception e) {
            // Handle invalid numeric input for year or rental rate
            System.out.println("Invalid input. Year and rental rate must be numbers.");
            scanner.nextLine(); // Clear invalid input
            return;
        }

        // Call appropriate method based on vehicle type
        switch (type) {
            case 1:
                addCar(make, model, year, rentalRate);
                break;
            case 2:
                addMotorcycle(make, model, year, rentalRate);
                break;
            case 3:
                addTruck(make, model, year, rentalRate);
                break;
            default:
                System.out.println("Invalid vehicle type.");
        }
    }

    // Helper method to add a car with specific properties
    private static void addCar(String make, String model, int year, double rentalRate) {
        int doors;
        try {
            // Safely read number of doors
            System.out.print("Enter number of doors: ");
            doors = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } catch (Exception e) {
            // Handle invalid door number input
            System.out.println("Invalid input. Number of doors must be a number.");
            scanner.nextLine(); // Clear invalid input
            return;
        }

        // Get fuel type (no need for try-catch as we're reading a String)
        System.out.print("Enter fuel type (petrol/diesel/electric): ");
        String fuelType = scanner.nextLine();

        // Create and add new Car object to vehicles list
        vehicles.add(new Car(make, model, year, doors, fuelType, rentalRate));
        System.out.println("Car added successfully!");
    }

    // Helper method to add a motorcycle with specific properties
    private static void addMotorcycle(String make, String model, int year, double rentalRate) {
        int wheels;
        try {
            // Safely read number of wheels
            System.out.print("Enter number of wheels: ");
            wheels = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } catch (Exception e) {
            // Handle invalid wheel number input
            System.out.println("Invalid input. Number of wheels must be a number.");
            scanner.nextLine(); // Clear invalid input
            return;
        }

        // Get motorcycle type (no need for try-catch as we're reading a String)
        System.out.print("Enter motorcycle type (sport/cruiser/off-road): ");
        String motorcycleType = scanner.nextLine();

        // Create and add new Motorcycle object to vehicles list
        vehicles.add(new Motorcycle(make, model, year, wheels, motorcycleType, rentalRate));
        System.out.println("Motorcycle added successfully!");
    }

    // Helper method to add a truck with specific properties
    private static void addTruck(String make, String model, int year, double rentalRate) {
        double capacity;
        try {
            // Safely read cargo capacity
            System.out.print("Enter cargo capacity (tons): ");
            capacity = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
        } catch (Exception e) {
            // Handle invalid capacity input
            System.out.println("Invalid input. Cargo capacity must be a number.");
            scanner.nextLine(); // Clear invalid input
            return;
        }

        // Get transmission type (no need for try-catch as we're reading a String)
        System.out.print("Enter transmission type (manual/automatic): ");
        String transmission = scanner.nextLine();

        // Create and add new Truck object to vehicles list
        vehicles.add(new Truck(make, model, year, capacity, transmission, rentalRate));
        System.out.println("Truck added successfully!");
    }

    // Method to display all vehicles currently in the system
    private static void listVehicles() {
        // Check if there are any vehicles to display
        if (vehicles.isEmpty()) {
            System.out.println("\nNo vehicles in the system.");
            return;
        }

        // Display all vehicles with numbered listing
        System.out.println("\nAll Vehicles:");
        for (int i = 0; i < vehicles.size(); i++) {
            // Use toString() method of each vehicle to get its string representation
            System.out.printf("%d. %s%n", i + 1, vehicles.get(i).toString());
        }
    }
}