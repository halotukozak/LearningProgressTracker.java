package tracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tracker.db.CourseEntity;

import static org.junit.jupiter.api.Assertions.*;

class CourseEntityTest {

    CourseEntity course;

    @BeforeEach
    void beforeEach() {
        course = new CourseEntity(1000, 9999, 15);
    }

    @Test
    void addPoints() {
        course.addPoints(15);
        assertEquals(30, course.getPoints());
    }

}