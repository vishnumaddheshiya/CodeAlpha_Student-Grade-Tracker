import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Student class to store student's name and their grades
class Student {
    private String name;
    private List<Double> grades;

    // Constructor to initialize a new Student object
    public Student(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }

    // Getter for student's name
    public String getName() {
        return name;
    }

    // Method to add a grade for the student
    public void addGrade(double grade) {
        if (grade >= 0) { // Ensure grade is non-negative
            grades.add(grade);
        } else {
            System.out.println("Warning: Grade cannot be negative. Not added.");
        }
    }

    // Method to calculate the average grade for the student
    public double calculateAverage() {
        if (grades.isEmpty()) {
            return 0.0; // Return 0 if no grades are available
        }
        double sum = 0;
        for (double grade : grades) {
            sum += grade;
        }
        return sum / grades.size();
    }

    // Method to find the highest grade for the student
    public double getHighestGrade() {
        if (grades.isEmpty()) {
            return 0.0; // Return 0 if no grades are available
        }
        double maxGrade = grades.get(0);
        for (double grade : grades) {
            if (grade > maxGrade) {
                maxGrade = grade;
            }
        }
        return maxGrade;
    }

    // Method to find the lowest grade for the student
    public double getLowestGrade() {
        if (grades.isEmpty()) {
            return 0.0; // Return 0 if no grades are available
        }
        double minGrade = grades.get(0);
        for (double grade : grades) {
            if (grade < minGrade) {
                minGrade = grade;
            }
        }
        return minGrade;
    }

    // Getter for the list of grades
    public List<Double> getGrades() {
        return grades;
    }

    // Method to display a summary for this specific student
    public void displayStudentSummary() {
        System.out.printf("  Name: %s%n", name);
        System.out.printf("  Grades: %s%n", grades.isEmpty() ? "No grades entered" : grades.toString());
        System.out.printf("  Average Score: %.2f%n", calculateAverage());
        System.out.printf("  Highest Score: %.2f%n", getHighestGrade());
        System.out.printf("  Lowest Score: %.2f%n", getLowestGrade());
        System.out.println("--------------------");
    }
}

// Main class to manage the grade tracking application
public class GradeTracker {
    private List<Student> students;
    private Scanner scanner;

    // Constructor to initialize the GradeTracker
    public GradeTracker() {
        students = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    // Method to add a new student and their grades
    public void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        Student student = new Student(name);

        int numGrades = -1;
        while (numGrades < 0) {
            try {
                System.out.print("Enter number of grades for " + name + ": ");
                numGrades = scanner.nextInt();
                if (numGrades < 0) {
                    System.out.println("Number of grades cannot be negative. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input
            }
        }
        scanner.nextLine(); // Consume newline left-over

        for (int i = 0; i < numGrades; i++) {
            double grade = -1.0;
            while (grade < 0) {
                try {
                    System.out.print("Enter grade " + (i + 1) + " for " + name + ": ");
                    grade = scanner.nextDouble();
                    if (grade < 0) {
                        System.out.println("Grade cannot be negative. Please enter a valid grade.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number for the grade.");
                    scanner.next(); // Consume the invalid input
                }
            }
            student.addGrade(grade);
        }
        scanner.nextLine(); // Consume newline left-over

        students.add(student);
        System.out.println("Student " + name + " added successfully!");
    }

    // Method to display a summary report of all students
    public void displaySummaryReport() {
        if (students.isEmpty()) {
            System.out.println("\nNo students to display. Please add students first.");
            return;
        }

        System.out.println("\n--- Student Grade Summary Report ---");
        double overallSum = 0;
        int overallGradeCount = 0;
        double overallHighest = 0.0;
        double overallLowest = Double.MAX_VALUE; // Initialize with a very large value

        for (Student student : students) {
            student.displayStudentSummary();

            // Aggregate data for overall statistics
            List<Double> studentGrades = student.getGrades();
            if (!studentGrades.isEmpty()) {
                overallSum += (student.calculateAverage() * studentGrades.size());
                overallGradeCount += studentGrades.size();

                if (student.getHighestGrade() > overallHighest) {
                    overallHighest = student.getHighestGrade();
                }
                if (student.getLowestGrade() < overallLowest) {
                    overallLowest = student.getLowestGrade();
                }
            }
        }

        System.out.println("\n--- Overall Class Statistics ---");
        if (overallGradeCount > 0) {
            System.out.printf("Overall Average Score: %.2f%n", overallSum / overallGradeCount);
            System.out.printf("Overall Highest Score: %.2f%n", overallHighest);
            System.out.printf("Overall Lowest Score: %.2f%n", overallLowest);
        } else {
            System.out.println("No grades entered across all students to calculate overall statistics.");
        }
        System.out.println("----------------------------------");
    }

    // Method to run the main menu of the application
    public void run() {
        int choice = -1;
        while (choice != 0) {
            System.out.println("\n--- Grade Tracker Menu ---");
            System.out.println("1. Add New Student");
            System.out.println("2. Display Summary Report");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        displaySummaryReport();
                        break;
                    case 0:
                        System.out.println("Exiting Grade Tracker. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number corresponding to the menu option.");
                scanner.nextLine(); // Consume the invalid input
                choice = -1; // Reset choice to keep loop running
            }
        }
        scanner.close(); // Close the scanner when done
    }

    // Main method to start the application
    public static void main(String[] args) {
        GradeTracker tracker = new GradeTracker();
        tracker.run();
    }
}

