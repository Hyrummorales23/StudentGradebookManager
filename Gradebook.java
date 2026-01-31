import java.io.*;
import java.util.ArrayList;

/**
 * Manages a collection of Student objects and provides file I/O operations.
 * Demonstrates the use of ArrayList from Java Collection Framework.
 */
public class Gradebook {
    private ArrayList<Student> students;
    private static final String DATA_FILE = "gradebook.dat";

    /**
     * Constructor initializes an empty gradebook.
     */
    public Gradebook() {
        students = new ArrayList<>();
    }

    /**
     * Adds a new student to the gradebook.
     * 
     * @param name      The student's name
     * @param studentId The student's ID
     * @return The created Student object
     */
    public Student addStudent(String name, String studentId) {
        Student student = new Student(name, studentId);
        students.add(student);
        return student;
    }

    /**
     * Displays all students in the gradebook.
     */
    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("\nNo students in gradebook.");
            return;
        }

        System.out.println("\n=== STUDENT GRADEBOOK ===");
        System.out.println("Total Students: " + students.size());
        System.out.println("-------------------------");

        for (int i = 0; i < students.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, students.get(i));
        }
    }

    /**
     * Finds a student by their ID.
     * 
     * @param studentId The ID to search for
     * @return The Student object, or null if not found
     */
    public Student findStudentById(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    /**
     * Calculates class statistics.
     */
    public void displayClassStatistics() {
        if (students.isEmpty()) {
            System.out.println("\nNo students to calculate statistics.");
            return;
        }

        double totalAverage = 0;
        int[] gradeDistribution = new int[5]; // A, B, C, D, F

        for (Student student : students) {
            double avg = student.calculateAverage();
            totalAverage += avg;

            String grade = student.getLetterGrade();
            switch (grade) {
                case "A":
                    gradeDistribution[0]++;
                    break;
                case "B":
                    gradeDistribution[1]++;
                    break;
                case "C":
                    gradeDistribution[2]++;
                    break;
                case "D":
                    gradeDistribution[3]++;
                    break;
                case "F":
                    gradeDistribution[4]++;
                    break;
            }
        }

        double classAverage = totalAverage / students.size();

        System.out.println("\n=== CLASS STATISTICS ===");
        System.out.printf("Class Average: %.2f\n", classAverage);
        System.out.println("Grade Distribution:");
        System.out.printf("  A: %d students\n", gradeDistribution[0]);
        System.out.printf("  B: %d students\n", gradeDistribution[1]);
        System.out.printf("  C: %d students\n", gradeDistribution[2]);
        System.out.printf("  D: %d students\n", gradeDistribution[3]);
        System.out.printf("  F: %d students\n", gradeDistribution[4]);
    }

    /**
     * Saves the gradebook data to a file using serialization.
     * 
     * @return true if save was successful, false otherwise
     */
    public boolean saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(DATA_FILE))) {
            oos.writeObject(students);
            System.out.println("\nGradebook saved successfully to " + DATA_FILE);
            return true;
        } catch (IOException e) {
            System.out.println("\nError saving gradebook: " + e.getMessage());
            return false;
        }
    }

    /**
     * Loads the gradebook data from a file using deserialization.
     * 
     * @return true if load was successful, false otherwise
     */
    @SuppressWarnings("unchecked")
    public boolean loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(DATA_FILE))) {
            students = (ArrayList<Student>) ois.readObject();
            System.out.println("\nGradebook loaded successfully from " + DATA_FILE);
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("\nNo saved gradebook found. Starting fresh.");
            return false;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("\nError loading gradebook: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets the number of students in the gradebook.
     * 
     * @return The student count
     */
    public int getStudentCount() {
        return students.size();
    }
}
