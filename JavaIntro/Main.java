import java.util.Scanner;

public class Main {
    // Calculate the four arithmetic operations of two integers: addition,
    // subtraction, multiplication and division
    public static void calculate(int a, int b) {
        System.out.println("===== Calculation Results =====");
        System.out.println(a + " + " + b + " = " + (a + b));
        System.out.println(a + " - " + b + " = " + (a - b));
        System.out.println(a + " × " + b + " = " + (a * b));

        // Judge if the divisor is 0
        if (b == 0) {
            System.out.println("Cannot calculate! Divisor cannot be 0.");
        } else {
            System.out.println(a + " ÷ " + b + " = " + (a / b));
        }
        System.out.println("==============================");
    }

    // Get the maximum value from the integer array
    // Traverse the array with for loop, do not use any tool classes
    public static int getMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    // Get the minimum value from the integer array
    // Traverse the array with for loop, do not use any tool classes
    public static int getMin(int[] arr) {
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }

    // Calculate the average value of the integer array, return double type
    // Traverse the array with for loop, do not use any tool classes
    public static double getAverage(int[] arr) {
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        // Convert to double to avoid integer division error
        return (double) sum / arr.length;
    }

    // Independent test function for calculate: read two integers from console and
    // call calculate method
    // Use try-catch to handle illegal input exceptions (e.g. letters, decimals)
    public static void testCalculate() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("===== Start Calculate Test =====");
            System.out.print("Please enter the first integer: ");
            int a = scanner.nextInt();
            System.out.print("Please enter the second integer: ");
            int b = scanner.nextInt();
            calculate(a, b);
        } catch (Exception e) {
            System.out.println("Input error! Please enter a valid integer, program exits.");
        }
    }

    // Independent test function for array statistics: read 5 integers, call three
    // statistical functions
    // Use try-catch to handle illegal input exceptions, output statistical results
    // in clear format
    public static void testArrayStatistics() {
        int[] numArray = new int[5];
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("===== Start Array Statistics Test =====");
            // Read 5 integers from console and store in the array
            for (int i = 0; i < numArray.length; i++) {
                System.out.print("Please enter the " + (i + 1) + "th integer: ");
                numArray[i] = scanner.nextInt();
            }

            // Call statistical functions and get results
            int max = getMax(numArray);
            int min = getMin(numArray);
            double average = getAverage(numArray);

            // Output statistical results with clear format
            System.out.println("\n===== Array Statistics Results =====");
            System.out.println("Maximum value in the array: " + max);
            System.out.println("Minimum value in the array: " + min);
            System.out.printf("Average value of the array: %.2f\n", average); // Keep 2 decimal places
            System.out.println("====================================");
        } catch (Exception e) {
            System.out.println("Input error! Please enter a valid integer, program exits.");
        }
    }

    /**
     * Test OOP part: create Student objects, use StudentManager to manage and
     * statistics
     * Meet the requirements: create at least 3 students, output average score and
     * top score student
     */
    public static void testOOP() {
        System.out.println("\n===== Start OOP Test (Student Management) =====");
        // 1. Create StudentManager object
        StudentManager manager = new StudentManager();

        // 2. Create at least 3 Student objects and add to manager
        manager.addStudent(new Student("Alice", 20, 90.0));
        manager.addStudent(new Student("Bob", 21, 85.5));
        manager.addStudent(new Student("Charlie", 19, 92.0));
        manager.addStudent(new Student("David", 20, 88.0)); // Extra student for more comprehensive test

        // 3. Print all student information
        manager.printAll();

        // 4. Calculate and output average score (keep 2 decimal places)
        double avgScore = manager.getAverageScore();
        System.out.printf("Average Score of All Students: %.2f\n", avgScore);

        // 5. Get and output the top score student's information
        Student topStudent = manager.getMaxScoreStudent();
        if (topStudent != null) {
            System.out.print("Student with the Highest Score: ");
            topStudent.printInfo();
        } else {
            System.out.println("No student data to get the highest score!");
        }
        System.out.println("===============================================");
    }

    /**
     * Test File I/O part: complete the whole verification process
     * Step: 1.Create students → 2.Save to file → 3.Clear memory → 4.Load from file
     * → 5.Output result
     * Follow the assignment requirements strictly
     */
    public static void testFileIO() {
        System.out.println("\n===== Start File I/O Test (Persistence) =====");
        String filePath = "students.txt";
        StudentManager manager = new StudentManager();

        // Step 1: Create several Student objects (at least 3, same as OOP part)
        System.out.println("Step 1: Create student objects...");
        manager.addStudent(new Student("Alice", 20, 90.0));
        manager.addStudent(new Student("Bob", 21, 85.5));
        manager.addStudent(new Student("Charlie", 19, 92.0));
        manager.addStudent(new Student("David", 20, 88.0));
        System.out.println("Created " + manager.getStudentList().size() + " student objects.");

        // Step 2: Save student list to students.txt
        System.out.println("\nStep 2: Save to file...");
        manager.saveToFile(filePath);

        // Step 3: Clear student data in memory
        System.out.println("\nStep 3: Clear memory data...");
        manager.clearStudents();
        System.out.println("Memory data check: " + manager.getStudentList().size() + " students left.");

        // Step 4: Load student data from students.txt
        System.out.println("\nStep 4: Load from file...");
        manager.loadFromFile(filePath);

        // Step 5: Output recovered result (list + average score + highest score
        // student)
        System.out.println("\nStep 5: Output recovered student data...");
        manager.printAll();
        double avgScore = manager.getAverageScore();
        System.out.printf("Recovered Average Score: %.2f\n", avgScore);
        Student topStudent = manager.getMaxScoreStudent();
        if (topStudent != null) {
            System.out.print("Recovered Student with Highest Score: ");
            topStudent.printInfo();
        } else {
            System.out.println("No student data to show highest score!");
        }

        System.out.println("=============================================");
    }

    // Main method: call test functions to verify the implementation of each part
    public static void main(String[] args) {
        // testCalculate(); // Call calculate test function
        // testArrayStatistics(); // Call array statistics test function
        // testOOP(); // Call OOP test function for student management
        testFileIO(); // Call File I/O test function
    }
}