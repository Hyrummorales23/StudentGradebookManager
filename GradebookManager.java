import java.util.Scanner;

/**
 * Main application class for the Student Gradebook Manager.
 * Provides a console-based menu interface for managing student grades.
 */
public class GradebookManager {
    private static Gradebook gradebook;
    private static Scanner scanner;

    /**
     * Main entry point of the application.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        initializeSystem();
        displayWelcomeMessage();
        runMainMenu();
        shutdownSystem();
    }

    /**
     * Initializes the gradebook and scanner objects.
     */
    private static void initializeSystem() {
        gradebook = new Gradebook();
        scanner = new Scanner(System.in);
        gradebook.loadFromFile(); // Try to load existing data
    }

    /**
     * Displays a welcome message to the user.
     */
    private static void displayWelcomeMessage() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║     STUDENT GRADEBOOK MANAGER v1.0           ║");
        System.out.println("║     Developed in Java                        ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println("\nWelcome! Data is automatically saved to file.");
    }

    /**
     * Main menu loop that handles user interaction.
     */
    private static void runMainMenu() {
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = getMenuChoice();

            switch (choice) {
                case 1 -> addNewStudent();
                case 2 -> addAssignmentScore();
                case 3 -> viewAllStudents();
                case 4 -> updateStudentGrade();
                case 5 -> viewClassStatistics();
                case 6 -> running = false;
                default -> System.out.println("\nInvalid choice. Please try again.");
            }

            if (running && choice >= 1 && choice <= 5) {
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
    }

    /**
     * Displays the main menu options.
     */
    private static void displayMenu() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║           MAIN MENU                  ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ 1. Add New Student                   ║");
        System.out.println("║ 2. Add Assignment Score              ║");
        System.out.println("║ 3. View All Students                 ║");
        System.out.println("║ 4. Update Student Grade              ║");
        System.out.println("║ 5. View Class Statistics             ║");
        System.out.println("║ 6. Exit                              ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("\nEnter your choice (1-6): ");
    }

    /**
     * Gets a valid menu choice from the user.
     * 
     * @return The user's choice as an integer
     */
    private static int getMenuChoice() {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= 6) {
                    return choice;
                }
            } catch (NumberFormatException e) {
                // Continue to retry
            }
            System.out.print("Please enter a number between 1 and 6: ");
        }
    }

    /**
     * Handles adding a new student to the gradebook.
     */
    private static void addNewStudent() {
        System.out.println("\n--- ADD NEW STUDENT ---");

        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine().trim();

        if (gradebook.findStudentById(studentId) != null) {
            System.out.println("Student with ID " + studentId + " already exists!");
            return;
        }

        Student student = gradebook.addStudent(name, studentId);
        System.out.println("\nStudent added successfully!");
        System.out.println("Student: " + student);
    }

    /**
     * Handles adding an assignment score for a student.
     */
    private static void addAssignmentScore() {
        System.out.println("\n--- ADD ASSIGNMENT SCORE ---");

        if (gradebook.getStudentCount() == 0) {
            System.out.println("No students in gradebook. Please add a student first.");
            return;
        }

        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine().trim();

        Student student = gradebook.findStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.print("Enter assignment score (0-100): ");
        try {
            double score = Double.parseDouble(scanner.nextLine());

            if (score < 0 || score > 100) {
                System.out.println("Score must be between 0 and 100!");
                return;
            }

            if (student.addAssignmentScore(score)) {
                System.out.printf("\nScore %.2f added successfully for %s\n",
                        score, student.getName());
                System.out.printf("Current average: %.2f (%s)\n",
                        student.calculateAverage(), student.getLetterGrade());
            } else {
                System.out.println("Cannot add more assignments (max 10 per student)");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format!");
        }
    }

    /**
     * Displays all students in the gradebook.
     */
    private static void viewAllStudents() {
        gradebook.displayAllStudents();
    }

    /**
     * Handles updating an existing assignment score.
     */
    private static void updateStudentGrade() {
        System.out.println("\n--- UPDATE ASSIGNMENT SCORE ---");

        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine().trim();

        Student student = gradebook.findStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        if (student.getAssignmentCount() == 0) {
            System.out.println("This student has no assignments to update.");
            return;
        }

        System.out.printf("\n%s has %d assignments:\n",
                student.getName(), student.getAssignmentCount());
        for (int i = 0; i < student.getAssignmentCount(); i++) {
            System.out.printf("  %d. %.2f\n", i + 1, student.getAssignmentScore(i));
        }

        System.out.print("\nEnter assignment number to update: ");
        try {
            int assignmentNum = Integer.parseInt(scanner.nextLine());

            if (assignmentNum < 1 || assignmentNum > student.getAssignmentCount()) {
                System.out.println("Invalid assignment number!");
                return;
            }

            System.out.print("Enter new score (0-100): ");
            double newScore = Double.parseDouble(scanner.nextLine());

            if (newScore < 0 || newScore > 100) {
                System.out.println("Score must be between 0 and 100!");
                return;
            }

            if (student.updateAssignmentScore(assignmentNum - 1, newScore)) {
                System.out.println("\nScore updated successfully!");
                System.out.printf("New average: %.2f (%s)\n",
                        student.calculateAverage(), student.getLetterGrade());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format!");
        }
    }

    /**
     * Displays class statistics.
     */
    private static void viewClassStatistics() {
        gradebook.displayClassStatistics();
    }

    /**
     * Performs cleanup operations before shutting down.
     */
    private static void shutdownSystem() {
        System.out.println("\nSaving gradebook data...");
        gradebook.saveToFile();

        System.out.println("\nThank you for using Student Gradebook Manager!");
        System.out.println("Goodbye!");

        scanner.close();
    }
}