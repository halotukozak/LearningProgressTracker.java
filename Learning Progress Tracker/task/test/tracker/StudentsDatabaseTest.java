package tracker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentsDatabaseTest {
    static StudentsDatabase db;
    static Student s1, s2, s3;

    @BeforeAll
    static void beforeAll() throws Exception {
        db = new StudentsDatabase();
        s1 = new Student("Joe", "Biden", "joe.biden@usa.com");
        s2 = new Student("Donald", "Trump", "donald.trump@usa.com");
        s3 = new Student("Anna", "Maria Wesolowska", "anna.maria@wesolowska.com");
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
        Student found = db.findStudentById(s1.getID());
        assertEquals(found, s1);
        found = db.findStudentById(String.valueOf(s2.getID()));
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
}