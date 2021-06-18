package sugar.annotationHandlers;

import org.junit.jupiter.api.Test;
import sugar.anotations.InRange;
import sugar.anotations.NotBlank;

import static org.junit.jupiter.api.Assertions.*;

class NotBlankHandlerTest {
    NotBlankHandler cheker = new NotBlankHandler();

    @NotBlank
    String blank = "";
    @NotBlank
    String notBlank = "sugar";
    @NotBlank
    String nv = null;

    @Test
    void isNotCorrect() {
        var first = getClass().getDeclaredFields()[1].getAnnotatedType().getAnnotation(InRange.class);
        var second = getClass().getDeclaredFields()[2].getAnnotatedType().getAnnotation(InRange.class);
        var third = getClass().getDeclaredFields()[3].getAnnotatedType().getAnnotation(InRange.class);


        assertTrue(cheker.isNotCorrect(first, blank));
        assertFalse(cheker.isNotCorrect(second, notBlank));
        assertFalse(cheker.isNotCorrect(third, nv));
    }

    @Test
    void createMessage() {
        var first = getClass().getDeclaredFields()[1].getAnnotatedType().getAnnotation(InRange.class);
        assertEquals(cheker.createMessage(first), "String is blank, please fill it");
    }
}