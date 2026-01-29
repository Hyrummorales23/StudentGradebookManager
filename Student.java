import java.io.Serializable;

/**
 * Represents a student with their grades and personal information.
 * This class implements Serializable to allow object serialization for file
 * storage.
 */
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String studentId;
    private double[] assignmentScores;
    private int assignmentCount;
    private static final int MAX_ASSIGNMENTS = 10;

    /**
     * Constructor to create a new Student object.
     * 
     * @param name      The student's full name
     * @param studentId The student's unique ID
     */
    public Student(String name, String studentId) {
        this.name = name;
        this.studentId = studentId;
        this.assignmentScores = new double[MAX_ASSIGNMENTS];
        this.assignmentCount = 0;
    }

    /**
     * Adds an assignment score for the student.
     * 
     * @param score The score to add (0-100)
     * @return true if score was added successfully, false if max assignments
     *         reached
     */
    public boolean addAssignmentScore(double score) {
        if (assignmentCount >= MAX_ASSIGNMENTS) {
            return false;
        }
        assignmentScores[assignmentCount] = score;
        assignmentCount++;
        return true;
    }

    /**
     * Calculates the average score of all assignments.
     * 
     * @return The average score, or 0.0 if no assignments exist
     */
    public double calculateAverage() {
        if (assignmentCount == 0) {
            return 0.0;
        }

        double total = 0;
        for (int i = 0; i < assignmentCount; i++) {
            total += assignmentScores[i];
        }
        return total / assignmentCount;
    }

    /**
     * Determines the letter grade based on the average score.
     * 
     * @return The letter grade (A, B, C, D, or F)
     */
    public String getLetterGrade() {
        double average = calculateAverage();

        if (average >= 90)
            return "A";
        if (average >= 80)
            return "B";
        if (average >= 70)
            return "C";
        if (average >= 60)
            return "D";
        return "F";
    }

    /**
     * Updates an existing assignment score.
     * 
     * @param index    The assignment index (0-based)
     * @param newScore The new score to set
     * @return true if update was successful, false if index is invalid
     */
    public boolean updateAssignmentScore(int index, double newScore) {
        if (index < 0 || index >= assignmentCount) {
            return false;
        }
        assignmentScores[index] = newScore;
        return true;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    public int getAssignmentCount() {
        return assignmentCount;
    }

    /**
     * Gets a specific assignment score.
     * 
     * @param index The assignment index
     * @return The score, or -1 if index is invalid
     */
    public double getAssignmentScore(int index) {
        if (index < 0 || index >= assignmentCount) {
            return -1;
        }
        return assignmentScores[index];
    }

    /**
     * Provides a formatted string representation of the student.
     * 
     * @return Formatted student information
     */
    @Override
    public String toString() {
        return String.format("ID: %-10s Name: %-20s Avg: %.2f Grade: %s",
                studentId, name, calculateAverage(), getLetterGrade());
    }
}