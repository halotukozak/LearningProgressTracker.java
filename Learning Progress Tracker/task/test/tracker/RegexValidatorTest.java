package tracker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tracker.Student.RegexValidator.*;

class RegexValidatorTest {


    //Email
    @Test
    void email_at() {
        assertTrue(isValidEmail("is@at.com"));

        assertFalse(isValidEmail("noAt.com"));
    }

    @Test
    void email_nonEnglishCharacters() {
        assertTrue(isValidEmail("abc@mail.com"));
        assertTrue(isValidEmail("a5bc@mail.com"));
        assertTrue(isValidEmail("125367at@zzz90.z9"));
        assertTrue(isValidEmail("abc-d@mail.com"));
        assertTrue(isValidEmail("abc_def@mail.com"));

        assertFalse(isValidEmail("ą@ę"));
        assertFalse(isValidEmail("abc#def@mail.com"));
    }

    @Test
    void email_atNotBetweenLettersOrNumber() {
        assertTrue(isValidEmail("is@ok.email"));
        assertTrue(isValidEmail("is5@ok.email"));

        assertFalse(isValidEmail("abc-@mail.com"));
        assertFalse(isValidEmail("abc@-mail.com"));
        assertFalse(isValidEmail("abc.@mail.com"));
        assertFalse(isValidEmail("abc@.mail.com"));
    }

    @Test
    void email_startsWithLetterOrNumber() {
        assertTrue(isValidEmail("is@ok.com"));
        assertTrue(isValidEmail("5is@ok.com"));
        assertTrue(isValidEmail("1@1.1"));

        assertFalse(isValidEmail(".abc@mail.com"));
        assertFalse(isValidEmail("-abc@mail.com"));
        assertFalse(isValidEmail("_abc@mail.com"));
        assertFalse(isValidEmail("..abc@mail.com"));
        assertFalse(isValidEmail("--abc@mail.com"));
        assertFalse(isValidEmail("__abc@mail.com"));
    }

    @Test
    void email_incorrectNumberOfDots() {
        assertTrue(isValidEmail("abcdef@dot.com"));

        assertFalse(isValidEmail("abcdef@com"));
        assertFalse(isValidEmail("abc.def@com"));
        assertFalse(isValidEmail("abcdef@mail..com"));
        assertFalse(isValidEmail("abc..def@mail.com"));
        assertFalse(isValidEmail("abcdef@mail...com"));
    }

    //firstName

    @Test
    void firstName_min2characters() {
        assertTrue(isValidFirstName("Sam"));
        assertTrue(isValidFirstName("Sa"));

        assertFalse(isValidFirstName("S"));
        assertFalse(isValidFirstName(""));
    }

    @Test
    void firstName_nonEnglishCharacters() {
        assertTrue(isValidFirstName("Sam"));
        assertTrue(isValidFirstName("Sam-Esmail"));
        assertTrue(isValidFirstName("O'Neill"));

        assertFalse(isValidFirstName("Stanisław"));
        assertFalse(isValidFirstName("Oğuz"));
        assertFalse(isValidFirstName("Sam98"));
    }

    @Test
    void firstName_startsWithLetter() {
        assertTrue(isValidFirstName("Sam"));
        assertFalse(isValidFirstName("-Sam-Esmail"));
        assertFalse(isValidFirstName("'O'Neill"));
    }

    @Test
    void firstName_endsWithLetter() {
        assertTrue(isValidFirstName("Sam"));
        assertFalse(isValidFirstName("Sam-Esmail-"));
        assertFalse(isValidFirstName("O'Neill'"));
    }
    //lastName

    @Test
    void lastName_nonEnglishCharacters() {
        assertTrue(isValidLastName("Sam"));
        assertTrue(isValidLastName("s-u"));
        assertTrue(isValidLastName("Sam-Esmail"));
        assertTrue(isValidLastName("Sam Esmail"));
        assertTrue(isValidLastName("Sam Esmail Boy"));
        assertTrue(isValidLastName("O'Neill"));

        assertFalse(isValidLastName("Stanisław Oğuz"));
        assertFalse(isValidLastName("Sam98"));
    }

    @Test
    void lastName_startsWithLetter() {
        assertTrue(isValidLastName("Sam"));
        assertFalse(isValidLastName("-Sam-Esmail"));
        assertFalse(isValidLastName("'O'Neill"));
        assertFalse(isValidLastName(" ONeill"));
        assertFalse(isValidLastName("ON 'Will"));
    }

    @Test
    void lastName_endsWithLetter() {
        assertTrue(isValidLastName("Sam"));
        assertFalse(isValidLastName("Sam-Esmail-"));
        assertFalse(isValidLastName("O'Neill'"));
        assertFalse(isValidLastName("ONeill "));
    }
}