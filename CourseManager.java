import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CourseManager {

    private ArrayList<Course> courses;
    private Scanner scanner;
    private final String FILE_NAME = "Course.dat";

    public CourseManager() {
        courses = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void runMenu() {
        loadFromFile();

        while (true) {
            System.out.println("\n====================================");
            System.out.println(" STUDENT COURSE MANAGEMENT SYSTEM ");
            System.out.println("====================================");
            System.out.println("1. Add Course");
            System.out.println("2. View All Courses");
            System.out.println("3. Search Course by Code");
            System.out.println("4. Compute Total Units");
            System.out.println("5. Save to File");
            System.out.println("6. Load from File");
            System.out.println("7. Exit Program");
            System.out.println("====================================");
            System.out.print("Select an option (1-7): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addCourse();
                    break;
                case "2":
                    displayCourses();
                    break;
                case "3":
                    searchCourse();
                    break;
                case "4":
                    computeTotalUnits();
                    break;
                case "5":
                    saveToFile();
                    break;
                case "6":
                    loadFromFile();
                    break;
                case "7":
                    System.out.println("\nExiting program. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid selection! Please enter a number between 1 and 7.");
            }
        }
    }

    private void addCourse() {
        System.out.println("\n--- Add New Course ---");
        System.out.print("Enter Course Code (e.g., COS201): ");
        String code = scanner.nextLine();

        System.out.print("Enter Course Title: ");
        String title = scanner.nextLine();

        int unit;

        try {
            System.out.print("Enter Course Units: ");
            unit = Integer.parseInt(scanner.nextLine().trim());

            if (unit < 0) {
                System.out.println("Error: Units cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input! Units must be a valid integer.");
            return;
        }

        if (recursiveSearch(courses, code.trim().toUpperCase(), 0) != null) {
            System.out.println("Error: A course with this code already exists.");
            return;
        }

        Course newCourse = new Course(code, title, unit);
        courses.add(newCourse);

        System.out.println("Course '" + newCourse.getCourseCode() + "' added successfully!");
    }

    private void displayCourses() {
        System.out.println("\n--- All Recorded Courses ---");

        if (courses.isEmpty()) {
            System.out.println("No courses recorded yet.");
            return;
        }

        System.out.printf("%-12s | %-30s | %-5s%n", "Course Code", "Course Title", "Units");
        System.out.println("---------------------------------------------------------------");

        for (Course course : courses) {
            System.out.println(course);
        }
    }

    private void searchCourse() {
        if (courses.isEmpty()) {
            System.out.println("No courses available to search.");
            return;
        }

        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine().trim().toUpperCase();

        Course result = recursiveSearch(courses, code, 0);

        if (result != null) {
            System.out.println(result);
        } else {
            System.out.println("Course not found.");
        }
    }

    private Course recursiveSearch(ArrayList<Course> list, String targetCode, int index) {
        if (index >= list.size()) {
            return null;
        }

        if (list.get(index).getCourseCode().equals(targetCode)) {
            return list.get(index);
        }

        return recursiveSearch(list, targetCode, index + 1);
    }

    private void computeTotalUnits() {
        int total = 0;

        for (Course course : courses) {
            total += course.getUnit();
        }

        System.out.println("Total Units = " + total);
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {

            for (Course course : courses) {
                writer.println(course.getCourseCode() + "," +
                               course.getCourseTitle() + "," +
                               course.getUnit());
            }

            System.out.println("Courses saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    private void loadFromFile() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            courses.clear();

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length == 3) {

                    courses.add(
                        new Course(
                            data[0],
                            data[1],
                            Integer.parseInt(data[2])
                        )
                    );
                }
            }

        } catch (Exception e) {
            System.out.println("Error loading file.");
        }
    }
}