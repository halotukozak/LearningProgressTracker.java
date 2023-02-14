package tracker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tracker.db.CourseEntity;
import tracker.db.LearningDatabase;
import tracker.models.Course;
import tracker.models.Student;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class LearningDatabaseTest {
    static LearningDatabase db;
    static Student s1, s2, s3;
    static Course sampleCourse;
    final int samplePoints = 15;
    final List<String> options = List.of("Most popular", "Least popular", "Highest activity", "Lowest activity", "Easiest course", "Hardest course");


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
        assertEquals(sampleCourse, db.findCourseByName("TEST"));
    }


    @Test
    void addPoints() {
        int studentID = db.add(s1);
        int courseID = db.add(sampleCourse);
        db.addPoints(studentID, courseID, samplePoints);
        CourseEntity newCourseEntity = db.addPoints(studentID, courseID, samplePoints);
        assertEquals(samplePoints + samplePoints, newCourseEntity.getPoints());
    }

    @Test
    void addPointsTwice() {
        int studentID = db.add(s1);
        int courseID = db.add(sampleCourse);
        int courseID2 = db.add(new Course("test2"));
        CourseEntity courseEntity1 = db.addPoints(studentID, courseID, samplePoints);
        CourseEntity courseEntity2 = db.addPoints(studentID, courseID2, samplePoints);

        assertEquals(samplePoints, courseEntity1.getPoints());
        assertEquals(samplePoints, courseEntity2.getPoints());

        courseEntity1 = db.addPoints(studentID, courseID, samplePoints);
        courseEntity2 = db.addPoints(studentID, courseID2, samplePoints);

        assertEquals(samplePoints * 2, courseEntity1.getPoints());
        assertEquals(samplePoints * 2, courseEntity2.getPoints());
    }


    @Test
    void addPointsToNonExistingEntity() {
        int studentID = db.add(s1);
        int courseID = db.add(sampleCourse);
        CourseEntity newCourseEntity = db.addPoints(studentID, courseID, samplePoints);
        assertEquals(samplePoints, newCourseEntity.getPoints());
        assertTrue(sampleCourse.getEntities().contains(newCourseEntity));
    }

    @Test
    void addZeroPoints() {
        int studentID = db.add(s1);
        int courseID = db.add(sampleCourse);
        CourseEntity newCourseEntity = db.addPoints(studentID, courseID, 0);
        assertNull(newCourseEntity);
    }

    @Test
    void getResultsOfStudent() {
        assertEquals(0, db.getResultsOfStudent(-1).size());

        int studentID = db.add(s1);
        int courseID = db.add(sampleCourse);
        db.addPoints(studentID, courseID, samplePoints);

        assertEquals(1, db.getResultsOfStudent(studentID).size());
        courseID = db.add(new Course("test2"));
        db.addPoints(studentID, courseID, samplePoints);

        assertEquals(2, db.getResultsOfStudent(studentID).size());

    }

    @Test
    void getStatistics() {
        Map<String, String> details = db.getStatistics(options);
        for (var d : details.values()) assertEquals("n/a", d);
        assertEquals(options.size(), details.size());
    }


}