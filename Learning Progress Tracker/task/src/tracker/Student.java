package tracker;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Student {


    private String firstName;
    private String lastName;
    private String email;
    private int ID;

    private final Map<String, Integer> points = new HashMap<>() {
    };

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
        if (RegexValidator.isValidEmail(email))
            this.email = email;
        else {
            throw new Exception("Incorrect email.");
        }
    }

    public void setFirstName(String firstName) throws Exception {
        if (RegexValidator.isValidFirstName(firstName))
            this.firstName = firstName;
        else {
            throw new Exception("Incorrect first name.");
        }
    }

    public void setLastName(String lastName) throws Exception {
        if (RegexValidator.isValidLastName(lastName))
            this.lastName = lastName;
        else {
            throw new Exception("Incorrect last name.");
        }
    }

    public void addPoints(String subject, int i) {
        this.points.put(subject, this.getPoints(subject) + i);
    }

    public int getPoints(String subject) {
        return points.getOrDefault(subject, 0);
    }

    public Map<String, Integer> getAllPoints() {
        return points;
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    static class RegexValidator {


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
}