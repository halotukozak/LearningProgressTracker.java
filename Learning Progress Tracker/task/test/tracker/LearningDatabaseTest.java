package tracker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tracker.db.CourseEntity;
import tracker.db.LearningDatabase;
import tracker.models.Course;
import tracker.models.Student;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

class LearningDatabaseTest {
    static LearningDatabase db;
    static Student s1, s2, s3;
    static Course sampleCourse;
    int samplePoints = 15;


    @BeforeAll
    static void beforeAll() throws Exception {
        db = new LearningDatabase();
        s1 = new Student("Joe", "Biden", "joe.biden@usa.com");
        s2 = new Student("Donald", "Trump", "donald.trump@usa.com");
        s3 = new Student("Anna", "Maria Wesolowska", "anna.maria@wesolowska.com");
        sampleCourse = new Course("test");

    }

    @AfterEach
    void afterEach() {
        db.clear();
    }

    @Test
    void findStudentsByID() {
        try {
            db.add(s1);
            db.add(s2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        Student found = db.findStudentByID(s1.getID());
        assertEquals(found, s1);
        found = db.findStudentByID(String.valueOf(s2.getID()));
        assertEquals(found, s2);
    }

    @Test
    void findStudentsByEmail() {
        Student sampleStudent = s1;
        try {
            db.add(sampleStudent);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        Student found = db.findStudentByEmail(sampleStudent.getEmail());
        assertEquals(found, sampleStudent);
    }

    @Test
    void getDetailsAboutCourse() {
        db.add(sampleCourse);
        assertEquals(sampleCourse, db.findCourseByID(sampleCourse.getID()));
    }

    @Test
    void findCourseByName() {
        db.add(sampleCourse);
        assertEquals(sampleCourse, db.findCourseByName("test"));
    }


    @Test
    void addPoints() {
        int studentID = db.add(s1);
        int courseID = db.add(sampleCourse);
        CourseEntity entity = db.addPoints(studentID, courseID, samplePoints);
        CourseEntity newEntity = db.addPoints(studentID, courseID, samplePoints);
        assertEquals(samplePoints + samplePoints, newEntity.getPoints());
    }

    @Test
    void addPointsToNonExistingEntity() {
        int studentID = db.add(s1);
        int courseID = db.add(sampleCourse);
        CourseEntity newEntity = db.addPoints(studentID, courseID, samplePoints);
        assertEquals(samplePoints, newEntity.getPoints());
    }

    @Test
    void getResultsOfStudent() {
        assertEquals(0, db.getResultsOfStudent(-1).size());

        int studentID = db.add(s1);
        int courseID = db.add(sampleCourse);
        CourseEntity newEntity = db.addPoints(studentID, courseID, samplePoints);

        assertEquals(1, db.getResultsOfStudent(studentID).size());
    }

}