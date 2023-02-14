package tracker.models;

import java.util.HashSet;
import java.util.Set;

public class Course {
    private final String name;
    private static int lastID = 0;
    private int ID;

    private final Set<tracker.db.CourseEntity> entities = new HashSet<>();

    public Course(String name) {
        this.ID = ++lastID;
        this.name = name;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Course other)) return false;
        return this.name.equals(other.name);
    }

    public int getID() {
        return ID;
    }

    public int setID() {
        this.ID = ++lastID;
        return this.ID;
    }

    public Set<tracker.db.CourseEntity> getEntities() {
        return this.entities;
    }

    public void updateEntity(tracker.db.CourseEntity newCourseEntity) {
        this.entities.removeIf(e -> e == newCourseEntity);
        this.entities.add(newCourseEntity);
    }
}
