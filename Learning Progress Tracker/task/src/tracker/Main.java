package tracker;

import tracker.db.LearningDatabase;
import tracker.db.LearningDatabase.DetailsEntity;
import tracker.models.Course;
import tracker.models.Student;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    static final Scanner scanner = new Scanner(System.in);
    private static final String[] courses = new String[]{"Java", "DSA", "Databases", "Spring"};

    private static LearningDatabase db;

    public static void main(String[] args) {
        init();
        run();
    }

    private static void init() {
        db = new LearningDatabase();
        for (String courseName : courses) {
            Course course = new Course(courseName);
            db.add(course);
        }
    }

    private static void run() {
        println("Learning Progress Tracker");
        String input;
        do {
            try {
                input = scanner.nextLine().toLowerCase();
                if (input.isBlank()) {
                    throw new Exception("No input");
                }
                switch (input) {
                    case "exit" -> {
                        println("Bye!");
                        System.exit(0);
                    }
                    case "back" -> throw new Exception("Enter 'exit' to exit the program");
                    case "add students" -> addStudents();
                    case "add points" -> addPoints();
                    case "list" -> list();
                    case "find" -> find();
                    case "statistics" -> statistics();
                    default -> throw new Exception("Unknown command!");
                }
            } catch (Exception e) {
                println(e.getMessage());
            }
        }
        while (scanner.hasNextLine());
    }

    private static void statistics() {
        println("Type the name of a course to see details or 'back' to quit");

        List<String> options = List.of("Most popular", "Least popular", "Highest activity", "Lowest activity", "Easiest course", "Hardest course");
        Map<String, String> statistics = db.getStatistics(options);
        for (var option : options) println(option + ": " + statistics.get(option));

        String input;
        do {
            try {
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("back")) break;
                Course course = db.findCourseByName(input);
                if (course == null) throw new Exception("Unknown course");
                println(course.getName());
                println("id\tpoints\tcompleted");
                DecimalFormat df = new DecimalFormat("0.0");
                df.setRoundingMode(RoundingMode.HALF_UP);
                List<DetailsEntity> details = db.getDetailsOfCourse(input);
                if (details.isEmpty()) throw new Exception();
                for (DetailsEntity entity : details) {
                    Double percentage = (double) (entity.points() * 100) / course.getCompleteness();
                    println(entity.studentID() + "\t" + entity.points() + "\t" + df.format(percentage) + "%");
                }


            } catch (Exception e) {
                println(e.getMessage());
            }
        }
        while (scanner.hasNextLine());
    }

    private static void addPoints() {
        println("Enter an ID and points or 'back' to return.");
        String input;
        do {
            try {
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("back")) break;
                List<String> inputArr = List.of(input.split(" "));
                if (inputArr.size() != 5) throw new Exception("Incorrect points format.");
                String ID = inputArr.get(0);
                Student student = db.findStudentByID(ID);
                if (student == null) throw new Exception("No student is found for ID=" + ID);
                for (String e : inputArr) if (!e.matches("^[0-9]+$")) throw new Exception("Incorrect points format.");
                List<Integer> inputArrInt = inputArr.stream().map(Integer::parseInt).toList();
                for (int i = 0; i < 4; i++) {
                    String name = courses[i];
                    Course course = db.findCourseByName(name);
                    db.addPoints(student.getID(), course.getID(), inputArrInt.get(i + 1));
                }
                println("Points updated.");
            } catch (Exception e) {
                println(e.getMessage());
            }
        }
        while (scanner.hasNextLine());
    }

    private static void list() throws Exception {
        Set<Integer> IDs = db.getIDs();
        if (IDs.isEmpty()) throw new Exception("No students found.");
        println("Students:");
        IDs.forEach(System.out::println);
    }

    public static void find() {
        println("Enter an ID or 'back' to return:");
        do {
            try {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back")) break;
                int ID = Integer.parseInt(input);
                Student student = db.findStudentByID(ID);
                if (student == null) throw new Exception("No student is found for ID=" + ID);
                System.out.print(student.getID() + " points: ");
                Map<String, Integer> result = db.getResultsOfStudent(student.getID());

//                Project's tests require particular order, it will work for unknown courses :D
//                Iterator<Map.Entry<String, Integer>> it = result.entrySet().iterator();
//                while (it.hasNext()) {
//                    Map.Entry<String, Integer> entry = it.next();
//                    String name = entry.getKey();
//                    Integer points = entry.getValue();
//                    System.out.print(name + "=" + points);
//                    if (it.hasNext()) System.out.print("; ");
//                }
                for (String name : courses) {
                    System.out.print(name + "=" + result.getOrDefault(name, 0));
                    if (!name.equals("Spring")) System.out.print("; ");
                }

            } catch (Exception e) {
                println(e.getMessage());
            }
        }
        while (scanner.hasNextLine());
    }


    public static void addStudents() {

        int studentsCounter = 0;
        println("Enter student credentials or 'back' to return.");
        String input;
        do {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("back")) break;
            try {
                Student student = new Student(input);
                if (db.add(student) == null) throw new Exception("This email is already taken.");
                println("The student has been added.");
                studentsCounter += 1;
            } catch (Exception e) {
                println(e.getMessage());
            }

        }
        while (scanner.hasNextLine());

        println("Total " + studentsCounter + " students have been added.");

    }

    private static void println(String str) {
        System.out.println(str);
    }

}

