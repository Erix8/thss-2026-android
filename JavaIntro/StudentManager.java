import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Student management class
 * Encapsulates the logic of adding students and statistical operations
 */
public class StudentManager {
    // List to store student objects (more flexible than array)
    private List<Student> studentList;

    // Constructor: initialize the student list
    public StudentManager() {
        studentList = new ArrayList<>();
    }

    // Add student object to the list
    public void addStudent(Student student) {
        studentList.add(student);
    }

    // Print all students' information
    public void printAll() {
        if (studentList.isEmpty()) {
            System.out.println("No student data in the list!");
            return;
        }
        System.out.println("===== All Student Information =====");
        for (Student s : studentList) {
            s.printInfo();
        }
        System.out.println("===================================");
    }

    // Get the student object with the highest score
    // Return null if the list is empty to avoid null pointer exception
    public Student getMaxScoreStudent() {
        if (studentList.isEmpty()) {
            return null;
        }
        Student maxScoreStudent = studentList.get(0);
        for (Student s : studentList) {
            if (s.getScore() > maxScoreStudent.getScore()) {
                maxScoreStudent = s;
            }
        }
        return maxScoreStudent;
    }

    // Calculate the average score of all students, return double type
    public double getAverageScore() {
        if (studentList.isEmpty()) {
            return 0.0;
        }
        double totalScore = 0.0;
        for (Student s : studentList) {
            totalScore += s.getScore();
        }
        return totalScore / studentList.size();
    }

    // Clear all student data in the memory (for subsequent File I/O part)
    public void clearStudents() {
        studentList.clear();
        System.out.println("Student data in memory has been cleared!");
    }

    // Getter for student list (for subsequent File I/O part)
    public List<Student> getStudentList() {
        return studentList;
    }

    /**
     * Save student list to students.txt
     * Use BufferedWriter, each student in one line, format: name,age,score
     * Handle IO exception with try-catch
     * 
     * @param filePath file path (e.g. "students.txt")
     */
    public void saveToFile(String filePath) {
        // Use try-with-resources to auto close stream (recommended)
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Student s : studentList) {
                // Splice string by fixed order: name,age,score
                String line = s.getName() + "," + s.getAge() + "," + s.getScore();
                bw.write(line);
                bw.newLine(); // New line for each student
            }
            System.out.println("Successfully saved " + studentList.size() + " students to " + filePath);
        } catch (IOException e) {
            System.out.println("Failed to save file! Reason: " + e.getMessage());
        }
    }

    /**
     * Load student data from students.txt
     * Use BufferedReader, split line by ",", construct Student object
     * Handle exceptions: file not found, empty file, format error
     * 
     * @param filePath file path (e.g. "students.txt")
     */
    public void loadFromFile(String filePath) {
        // Clear original data first to avoid duplication
        studentList.clear();
        File file = new File(filePath);
        // Check if file exists
        if (!file.exists()) {
            System.out.println("Load failed! File " + filePath + " does not exist.");
            return;
        }
        // Check if file is empty
        if (file.length() == 0) {
            System.out.println("Load failed! File " + filePath + " is empty.");
            return;
        }

        // Read file line by line
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip empty line (avoid format error)
                if (line.trim().isEmpty())
                    continue;
                // Split by comma, strict to the format: name,age,score
                String[] fields = line.split(",");
                // Check if split result has 3 fields
                if (fields.length != 3) {
                    System.out.println("Skip invalid line: " + line + " (format error)");
                    continue;
                }
                // Parse fields, handle type conversion exception
                String name = fields[0].trim();
                int age = Integer.parseInt(fields[1].trim());
                double score = Double.parseDouble(fields[2].trim());
                // Construct Student and add to list
                studentList.add(new Student(name, age, score));
            }
            System.out.println("Successfully loaded " + studentList.size() + " students from " + filePath);
        } catch (NumberFormatException e) {
            System.out.println("Load failed! Data type conversion error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Load failed! File read error: " + e.getMessage());
        }
    }
}