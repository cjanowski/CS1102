import java.util.Scanner;
/**
 * Main class that serves as the entry point for the quiz application.
 * Creates and starts a new quiz instance.
 */
public class w1a {
 public static void main(String[] args) {
 Quiz quiz = new Quiz();
 quiz.start();
 }
}
/**
 * Quiz class that handles the core functionality of the multiple-choice quiz.
 * Manages questions, tracks score, and handles user input.
 */
class Quiz {
 private Question[] questions; // Array to store all quiz questions
 private int score; // Tracks the user's current score
 private Scanner scanner; // Scanner object for reading user input
 /**
 * Constructor initializes the quiz by:
 * 1. Creating a new Scanner for user input
 * 2. Setting up an array of predened questions
 * Each question includes the question text, multiple choice options,
 * and the index of the correct answer (0-based)
 */
 public Quiz() {
 scanner = new Scanner(System.in);
 // Initialize quiz questions with various topics including
 // Java history, development tools, platform compatibility,
 // current events, and mathematical calculations
 questions = new Question[] {
 new Question(
 "What is the rst name of the person who created Java?",
 new String[] {"James", "Mike", "Brad", "Chris"},
 0 // James Gosling is the creator of Java
 ),
 new Question(
 "What is another common Java IDE?",
 new String[] {"SimpleJs", "coee express", "IntelliJ", "Oce"},
 2 // IntelliJ is a popular Java IDE
 ),
 new Question(
 "What OS does Java not work on?",
 new String[] {"Windows", "macOS", "Linux", "Works on all three"},
 3 // Java's platform independence is a key feature
 ),
 new Question(
 "Who won the most recent US election?",
 new String[] {"Donald Trump", "Bill Clinton", "Joe Biden", "Elon Musk"},
 0
 ),
 new Question(
 "What is 3677 divided by 53?",
 new String[] {"69.37", "46.54", "22", "73.54"},
 0 // Mathematical calculation question
 )
 };
 }
 /**
 * Starts the quiz by:
 * 1. Displaying a welcome message
 * 2. Iterating through all questions
 * 3. Tracking correct answers
 * 4. Showing nal results
 * 5. Closing the scanner when nished
 */
 public void start() {
 System.out.println("Welcome to the Multiple Choice Quiz!");
 System.out.println("There are " + questions.length + " questions. Good luck!\n");
 // Iterate through each question, tracking the score
 for (int i = 0; i < questions.length; i++) {
 if (askQuestion(questions[i], i + 1)) {
 score++; // Increment score for correct answers
 }
 System.out.println(); // Add a blank line between questions for readability
 }
 showResults();
 scanner.close(); // Clean up system resources
 }
 /**
 * Presents a single question to the user and processes their answer.
 *
 * @param question The Question object containing the question data
 * @param questionNumber The current question number (1-based)
 * @return boolean indicating if the answer was correct
 */
 private boolean askQuestion(Question question, int questionNumber) {
 // Display the question and its number
 System.out.println("Question " + questionNumber + ": " + question.getQuestion());
 // Display all answer choices with numbers
 String[] choices = question.getChoices();
 for (int i = 0; i < choices.length; i++) {
 System.out.println((i + 1) + ". " + choices[i]);
 }
 System.out.print("Your answer (1-" + choices.length + "): ");
 // Input validation loop ensures a valid numeric response
 int userAnswer;
 while (true) {
 try {
 userAnswer = Integer.parseInt(scanner.nextLine());
 // Check if the answer is within valid range
 if (userAnswer >= 1 && userAnswer <= choices.length) {
 break;
 } else {
 System.out.print("Please enter a number between 1 and " + choices.length + ": ");
 }
 } catch (NumberFormatException e) {
 // Handle non-numeric input
 System.out.print("Invalid input. Please enter a number between 1 and " +
choices.length + ": ");
 }
 }
 // Check if answer is correct and provide feedback
 boolean correct = (userAnswer - 1) == question.getCorrectAnswer();
 System.out.println(correct ? "Correct!" : "Incorrect. The correct answer was: " +
 choices[question.getCorrectAnswer()]);
 return correct;
 }
 /**
 * Displays the nal quiz results including:
 * 1. Total score
 * 2. Score as a percentage
 * 3. Personalized feedback based on performance
 */
 private void showResults() {
 System.out.println("\nQuiz completed!");
 System.out.println("Your score: " + score + " out of " + questions.length);
 // Calculate and display percentage score
 double percentage = (double) score / questions.length * 100;
 System.out.printf("Percentage: %.1f%%\n", percentage);
 // Provide encouraging feedback based on performance thresholds
 if (percentage == 100) {
 System.out.println("Perfect score! Excellent work!");
 } else if (percentage >= 80) {
 System.out.println("Great job!");
 } else if (percentage >= 60) {
 System.out.println("Good eort!");
 } else {
 System.out.println("Keep practicing!");
 }
 }
}
/**
 * Question class represents a single multiple-choice question.
 * Stores the question text, answer choices, and the correct answer.
 */
class Question {
 private String question; // The question text
 private String[] choices; // Array of possible answers
 private int correctAnswer; // Index of the correct answer (0-based)
 /**
 * Constructor for creating a new question
 *
 * @param question The question text
 * @param choices Array of possible answers
 * @param correctAnswer Index of the correct answer (0-based)
 */
 public Question(String question, String[] choices, int correctAnswer) {
 this.question = question;
 this.choices = choices;
 this.correctAnswer = correctAnswer;
 }
 // Getter methods
 public String getQuestion() {
 return question;
 }
 public String[] getChoices() {
 return choices;
 }
 public int getCorrectAnswer() {
 return correctAnswer;
 }
}