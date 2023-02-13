package tracker.db;

public class CourseEntity implements Comparable {
    private int courseID;
    private int studentID;
    private int points = 0;

    public CourseEntity(int studentID, int courseID, int points) {
        this.studentID = (studentID);
        this.courseID = (courseID);
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CourseEntity other)) return false;
        return other.courseID == this.courseID && other.studentID == this.studentID;
    }

    public CourseEntity addPoints(int points) {
        this.points += points;
        return this;
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;
        CourseEntity other = (CourseEntity) o;
        return getPoints() - other.getPoints();
    }
}
