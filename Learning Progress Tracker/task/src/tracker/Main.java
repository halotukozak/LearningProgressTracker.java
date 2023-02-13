package tracker;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    private static StudentsDatabase db;

    public static void main(String[] args) {
        init();
        run();
    }

    private static void init() {
        db = new StudentsDatabase();
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
                    case "list" -> printStudents();
                    case "find" -> find();
                    default -> throw new Exception("Unknown command!");
                }
            } catch (Exception e) {
                println(e.getMessage());
            }
        }
        while (scanner.hasNextLine());
    }

    private static void addPoints() {
        println("Enter an id and points or 'back' to return.");
        String input;
        do {
            try {
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("back")) break;
                List<String> inputArr = List.of(input.split(" "));
                String id = inputArr.get(0);
                Student student = db.findStudentById(id);
                if (inputArr.size() != 5) throw new Exception("Incorrect points format.");
                if (student == null) throw new Exception("No student is found for id=" + id);
                for (String e : inputArr) if (!e.matches("^[0-9]+$")) throw new Exception("Incorrect points format.");
                List<Integer> inputArrInt = inputArr.stream().map(Integer::parseInt).toList();
                student.addPoints("Java", inputArrInt.get(1));
                student.addPoints("DSA", inputArrInt.get(2));
                student.addPoints("Databases", inputArrInt.get(3));
                student.addPoints("Spring", inputArrInt.get(4));
                println("Points updated.");
            } catch (Exception e) {
                println(e.getMessage());
            }
        }
        while (scanner.hasNextLine());
    }

    private static void printStudents() throws Exception {
        Set<Integer> IDs = db.getIDs();
        if (IDs.isEmpty()) throw new Exception("No students found.");
        println("Students:");
        IDs.forEach(System.out::println);
    }

    public static void find() {
        println("Enter an id or 'back' to return:");
        do {
            try {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back")) break;
                int id = Integer.parseInt(input);
                Student student = db.findStudentById(id);
                if (student == null) throw new Exception("No student is found for id=" + id);
                System.out.print(student.getID() + " points: ");
                student.getAllPoints().forEach((subject, points) -> System.out.print(subject + "=" + points + "; "));
                println("");
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

