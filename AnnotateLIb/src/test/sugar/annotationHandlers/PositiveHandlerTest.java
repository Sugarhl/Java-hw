package sugar.annotationHandlers;

import org.junit.jupiter.api.Test;
import sugar.anotations.InRange;
import sugar.anotations.Negative;

import static org.junit.jupiter.api.Assertions.*;

class PositiveHandlerTest {

    PositiveHandler checker = new PositiveHandler();

    @Negative
    Integer negative = -10;
    @Negative
    Short positive = 30;
    @Negative
    Byte zero = 0;
    @Negative
    Long test1 = 20L;
    @Negative
    int test2 = 23;


    @Test
    void isNotCorrect() {
        var first = getClass().getDeclaredFields()[1].getAnnotatedType().getAnnotation(InRange.class);
        var second = getClass().getDeclaredFields()[2].getAnnotatedType().getAnnotation(InRange.class);
        var third = getClass().getDeclaredFields()[3].getAnnotatedType().getAnnotation(InRange.class);
        var fourth = getClass().getDeclaredFields()[4].getAnnotatedType().getAnnotation(InRange.class);
        var fifth = getClass().getDeclaredFields()[5].getAnnotatedType().getAnnotation(InRange.class);

        assertTrue(checker.isNotCorrect(first, negative));
        assertFalse(checker.isNotCorrect(second, positive));
        assertTrue(checker.isNotCorrect(third, zero));
        assertFalse(checker.isNotCorrect(fourth, test1));
        assertFalse(checker.isNotCorrect(fifth, test2));
    }


    @Test
    void createMessage() {
        var first = getClass().getDeclaredFields()[1].getAnnotatedType().getAnnotation(InRange.class);
        assertEquals(checker.createMessage(first), "Value must be positive");
    }
}