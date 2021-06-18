package sugar.annotationHandlers;

import org.junit.jupiter.api.Test;
import sugar.anotations.InRange;

import static org.junit.jupiter.api.Assertions.*;

class InRangeHandlerTest {
    InRangeHandler checker = new InRangeHandler();
    @InRange(min = 20, max = 40)
    Integer low = 10;
    @InRange(min = 20, max = 40)
    Short center = 30;
    @InRange(min = 20, max = 40)
    Byte high = 50;
    @InRange(min = 20, max = 40)
    Long lowBound = 20L;
    @InRange(min = 20, max = 40)
    int highBound = 40;


    @Test
    void isNotCorrect() {
        var first = getClass().getDeclaredFields()[1].getAnnotatedType().getAnnotation(InRange.class);
        var second = getClass().getDeclaredFields()[2].getAnnotatedType().getAnnotation(InRange.class);
        var third = getClass().getDeclaredFields()[3].getAnnotatedType().getAnnotation(InRange.class);
        var fourth = getClass().getDeclaredFields()[4].getAnnotatedType().getAnnotation(InRange.class);
        var fifth = getClass().getDeclaredFields()[5].getAnnotatedType().getAnnotation(InRange.class);

        assertFalse(checker.isNotCorrect(second, center));
        assertFalse(checker.isNotCorrect(fourth, lowBound));
        assertFalse(checker.isNotCorrect(fifth, highBound));

        assertTrue(checker.isNotCorrect(first, low));
        assertTrue(checker.isNotCorrect(third, high));


    }

    @Test
    void createMessage() {
        var first = getClass().getDeclaredFields()[1].getAnnotatedType().getAnnotation(InRange.class);

        assertEquals(checker.createMessage(first),"Value must be in range [20,40]");

    }
}