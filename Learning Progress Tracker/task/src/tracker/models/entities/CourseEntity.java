package tracker.models.entities;

public class CourseEntity {
    private final int courseID;
    private final int studentID;
    private int points = 0;

    public CourseEntity(int studentID, int courseID, int points) {
        this.studentID = studentID;
        this.courseID = courseID;
        this.addPoints(points);
    }

    public int getCourseID() {
        return courseID;
    }


    public int getStudentID() {
        return studentID;
    }


    public int getPoints() {
        return points;
    }


    public CourseEntity addPoints(int points) {
        this.points += points;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CourseEntity other)) return false;
        return other.courseID == this.courseID && other.studentID == this.studentID;
    }
}
