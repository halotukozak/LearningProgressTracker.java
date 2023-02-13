package tracker;

import org.junit.jupiter.api.Test;
import tracker.models.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class StudentTest {

    @Test
    void numberOfParameters() {
        try {
            new Student("firstName lastName email@dot.pl");
            new Student("firstName lastName lastNameToo email@dot.pl");
        } catch (Exception e) {
            fail("These numbers of parameters should be OK.");

        }

        try {
            new Student("email@dot.pl");
            new Student("name");
            new Student("name firstName");
            new Student("firstName email@dot");
            fail("Students should not be created");
        } catch (Exception e) {

        }


    }

    @Test
    void setParameters() {
        try {
            Student s = new Student("firstName lastName email@dot.com");
            assertEquals(s.getEmail(), "email@dot.com");
            assertEquals(s.getFirstName(), "firstName");
            assertEquals(s.getLastName(), "lastName");
        } catch (Exception e) {
            fail("These parameters should be OK.");
        }

    }
}
