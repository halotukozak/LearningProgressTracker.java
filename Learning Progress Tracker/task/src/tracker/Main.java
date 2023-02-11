package tracker;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Learning Progress Tracker");

        String input;
        do {
            input = scanner.nextLine().toLowerCase();
            if (input.isBlank()) {
                System.out.println("No input");
                continue;
            }
            switch (input) {
                case "exit" -> {
                    System.out.println("Bye!");
                    System.exit(0);
                }
                case "back" -> System.out.println("Enter 'exit' to exit the program");
                case "add students" -> addStudents();
                default -> System.out.println("Unknown command!");
            }
        }
        while (scanner.hasNextLine());
    }

    public static void addStudents() {
        Scanner scanner = new Scanner(System.in);

        int studentsCounter = 0;
        System.out.println("Enter student credentials or 'back' to return.");
        String input;
        do {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("back")) break;
            try {
                Student student = new Student(input);
                System.out.println("The student has been added.");
                studentsCounter += 1;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        while (scanner.hasNextLine());

        System.out.println("Total " + studentsCounter + " students have been added.");

    }
}

class Student {
    private String firstName;
    private String lastName;
    private String email;

    public Student(String firstName, String lastName, String email) throws Exception {
        this.setEmail(email);
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

    public Student(String input) throws Exception {
        String[] arrInput = input.split(" ");
        int length = arrInput.length;

        if (length < 3) {
            throw new Exception("Incorrect credentials.");
        }
        this.setFirstName(arrInput[0]);
        this.setLastName(String.join(" ", Arrays.copyOfRange(arrInput, 1, length - 1)));
        this.setEmail(arrInput[length - 1]);

    }

    public void setEmail(String email) throws Exception {
        if (Validator.isValidEmail(email))
            this.email = email;
        else {
            throw new Exception("Incorrect email.");
        }
    }

    public void setFirstName(String firstName) throws Exception {
        if (Validator.isValidFirstName(firstName))
            this.firstName = firstName;
        else {
            throw new Exception("Incorrect first name.");
        }
    }

    public void setLastName(String lastName) throws Exception {
        if (Validator.isValidLastName(lastName))
            this.lastName = lastName;
        else {
            throw new Exception("Incorrect last name.");
        }
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;

    }
}

class Validator {


    public static boolean isValidFirstName(String firstName) {
        return firstName.matches("^[A-Za-z]([-']?[A-Za-z])+$");
    }

    public static boolean isValidLastName(String lastName) {
        return lastName.matches("^[A-Za-z]([-' ]?[A-Za-z])+$");
    }

//    These emails are invalid, but that's the requirements of task.
    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9]+([.\\-_]?\\w)*@[A-Za-z0-9]+([.\\-_]?[A-Za-z0-9])*(\\.[A-Za-z0-9]+)$");

    }
}