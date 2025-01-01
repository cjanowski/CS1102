package w7;

import javax.swing.SwingUtilities;

/**
 * Main entry point for the Student Management System application.
 * This class initializes and launches the GUI application.
 * @author Cory Janowski
 * @version 1.0
 */
public class Main {
    /**
     * Main method that starts the application.
     * Uses SwingUtilities.invokeLater to ensure thread safety for Swing components.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Launch the GUI in the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            StudentManagementSystem sms = new StudentManagementSystem();
            sms.setVisible(true);
        });
    }
} 