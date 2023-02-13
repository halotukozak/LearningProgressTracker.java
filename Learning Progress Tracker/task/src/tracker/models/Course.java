package tracker.models;

public class Course implements Comparable {
    private String name;
    private static int lastID = 0;
    private int ID;

    public Course(String name) {
        this.ID = ++lastID;
        this.setName(name);
    }

    public int getCompleteness() {
        return switch (name) {
            case "Java" -> 600;
            case "DSA" -> 400;
            case "Databases" -> 480;
            case "Spring" -> 550;
            default -> 0;
        };

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Course other)) return false;
        return this.name.equals(other.name);
    }

    @Override
    public int compareTo(Object o) {
        Course other = (Course) o;
//        TODO wait, is it necessary here?
        return 0;
    }

    public int getID() {
        return ID;
    }

    public int setID() {
        this.ID = ++lastID;
        return this.ID;
    }
}
