// Required imports for the application
import java.util.ArrayList;  // For dynamic list operations
import java.util.List;      // For list interface
import java.util.Scanner;   // For user input

/**
 * StockAnalyzerApp - A console-based application for analyzing stock prices
 * This class provides functionality to input and analyze stock price data
 * through various statistical operations.
 */
public class w4a {
    // Class-level Scanner object for handling all user input throughout the application
    // Static because we only need one instance shared across all method calls
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Displays the main menu interface to the user
     * This method prints all available options for the stock price analyzer
     * The menu is displayed in a numbered format for easy selection
     * Options range from 1-7, covering all major functionalities of the application
     */
    public static void displayMenu() {
        // Print a blank line for better visual separation between operations
        System.out.println("\nStock Price Analyzer");
        // Display all available options with corresponding numbers
        System.out.println("1. Enter new stock prices");    // Data input option
        System.out.println("2. View current data");         // Data display option
        System.out.println("3. Calculate average price");   // Statistical analysis
        System.out.println("4. Find maximum price");        // Maximum value finder
        System.out.println("5. Count occurrences of a price"); // Frequency counter
        System.out.println("6. View cumulative sums");      // Running total display
        System.out.println("7. Exit");                      // Program termination
        System.out.print("Enter your choice (1-7): ");     // Prompt for user input
    }

    /**
     * Collects stock prices from user input
     * This method continuously prompts for prices until a non-numeric input is received
     * @return double[] An array containing all entered stock prices
     */
    public static double[] getStockPrices() {
        // ArrayList for dynamic size management during input collection
        List<Double> pricesList = new ArrayList<>();
        System.out.println("\nEnter stock prices (enter a non-number to finish):");
        
        // Infinite loop for continuous price input
        while (true) {
            try {
                System.out.print("Enter price: ");
                String input = scanner.nextLine();        // Get user input as string
                double price = Double.parseDouble(input); // Convert string to double
                pricesList.add(price);                   // Add valid price to list
            } catch (NumberFormatException e) {
                // Exit loop when non-numeric input is detected
                break;
            }
        }

        // Convert ArrayList<Double> to primitive double array for compatibility
        double[] prices = new double[pricesList.size()];
        for (int i = 0; i < pricesList.size(); i++) {
            prices[i] = pricesList.get(i);
        }
        return prices;
    }

    /**
     * Calculates the arithmetic mean of all stock prices
     * Handles edge cases such as null or empty arrays
     * @param prices Array of stock prices to analyze
     * @return double The average price, or 0 if array is empty/null
     */
    public static double calculateAveragePrice(double[] prices) {
        // Input validation - return 0 for null or empty arrays
        if (prices == null || prices.length == 0) {
            return 0;
        }
        
        // Calculate sum using running total
        double sum = 0;
        for (double price : prices) {
            sum += price;
        }
        // Return average by dividing sum by count of prices
        return sum / prices.length;
    }

    /**
     * Determines the highest price in the array
     * Handles edge cases such as null or empty arrays
     * @param prices Array of stock prices to analyze
     * @return double The maximum price, or Double.MIN_VALUE if array is empty/null
     */
    public static double findMaximumPrice(double[] prices) {
        // Input validation
        if (prices == null || prices.length == 0) {
            return Double.MIN_VALUE;  // Return smallest possible double value for empty/null arrays
        }
        
        // Start with first price as maximum
        double maxPrice = prices[0];
        // Compare each price against current maximum
        for (double price : prices) {
            if (price > maxPrice) {
                maxPrice = price;
            }
        }
        return maxPrice;
    }

    /**
     * Counts how many times a specific price appears in the array
     * Uses epsilon comparison for floating-point equality
     * @param prices Array of stock prices to search through
     * @param targetPrice The specific price to count
     * @return int Number of times the target price appears
     */
    public static int countOccurrences(double[] prices, double targetPrice) {
        // Input validation
        if (prices == null) {
            return 0;
        }
        
        int count = 0;
        for (double price : prices) {
            // Use epsilon comparison for floating-point equality
            // This accounts for small rounding errors in floating-point arithmetic
            if (Math.abs(price - targetPrice) < 0.000001) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates running total of prices
     * Each element in returned list represents sum of all prices up to that point
     * @param prices Array of stock prices
     * @return List<Double> List of cumulative sums
     */
    public static List<Double> computeCumulativeSum(double[] prices) {
        List<Double> cumulativeSum = new ArrayList<>();
        // Input validation
        if (prices == null || prices.length == 0) {
            return cumulativeSum;  // Return empty list for null/empty input
        }

        double sum = 0;
        // Calculate running total and add each sum to list
        for (double price : prices) {
            sum += price;
            cumulativeSum.add(sum);
        }
        return cumulativeSum;
    }

    /**
     * Formats and displays all current stock prices
     * Shows prices by day number for better context
     * @param prices Array of stock prices to display
     */
    public static void displayCurrentData(double[] prices) {
        // Input validation with user feedback
        if (prices == null || prices.length == 0) {
            System.out.println("No data available.");
            return;
        }

        // Display prices with day numbers
        System.out.println("\nCurrent Stock Prices:");
        for (int i = 0; i < prices.length; i++) {
            // Format output to 2 decimal places with dollar sign
            System.out.printf("Day %d: $%.2f%n", i + 1, prices[i]);
        }
    }

    /**
     * Main program loop
     * Handles menu selection and program flow
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Initialize empty array for storing prices
        double[] prices = new double[0];
        
        // Main program loop
        while (true) {
            displayMenu();
            String choice = scanner.nextLine();  // Get user menu selection

            // Process user selection
            switch (choice) {
                case "1":  // Enter new prices
                    prices = getStockPrices();
                    System.out.println("Stock prices updated.");
                    break;

                case "2":  // Display current data
                    displayCurrentData(prices);
                    break;

                case "3":  // Calculate and display average
                    double average = calculateAveragePrice(prices);
                    System.out.printf("Average price: $%.2f%n", average);
                    break;

                case "4":  // Find and display maximum price
                    double max = findMaximumPrice(prices);
                    if (max != Double.MIN_VALUE) {
                        System.out.printf("Maximum price: $%.2f%n", max);
                    } else {
                        System.out.println("No data available.");
                    }
                    break;

                case "5":  // Count occurrences of a specific price
                    System.out.print("Enter price to count: ");
                    try {
                        double target = Double.parseDouble(scanner.nextLine());
                        int occurrences = countOccurrences(prices, target);
                        System.out.printf("Price $%.2f occurs %d times%n", target, occurrences);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid price entered.");
                    }
                    break;

                case "6":  // Display cumulative sums
                    List<Double> cumulativeSums = computeCumulativeSum(prices);
                    if (!cumulativeSums.isEmpty()) {
                        System.out.println("\nCumulative Sums:");
                        for (int i = 0; i < cumulativeSums.size(); i++) {
                            System.out.printf("Day %d: $%.2f%n", i + 1, cumulativeSums.get(i));
                        }
                    } else {
                        System.out.println("No data available.");
                    }
                    break;

                case "7":  // Exit program
                    System.out.println("Thank you for using Stock Price Analyzer!");
                    scanner.close();  // Clean up resources
                    return;

                default:  // Invalid input handling
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        }
    }
}