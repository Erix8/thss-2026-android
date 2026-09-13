/**
 * Student entity class
 * Contains basic attributes: name, age, score
 * Provides constructor and printInfo() method
 */
public class Student {
    // Member variables
    private String name;
    private int age;
    private double score;

    // Full-parameter constructor
    public Student(String name, int age, double score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    // Getter methods (for StudentManager to access private attributes)
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getScore() {
        return score;
    }

    // Print student's complete information in a readable format
    public void printInfo() {
        System.out.println("Name: " + name + ", Age: " + age + ", Score: " + score);
    }
}