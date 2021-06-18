package sugar.annotationHandlers;

import org.junit.jupiter.api.Test;
import sugar.anotations.InRange;
import sugar.anotations.NotNull;

import static org.junit.jupiter.api.Assertions.*;

class NotNullHandlerTest {

    NotNullHandler checker = new NotNullHandler();

    @NotNull
    Object notNull = new Object();
    @NotNull
    Object Null = null;

    @Test
    void isNotCorrect() {
        var first = getClass().getDeclaredFields()[1].getAnnotatedType().getAnnotation(InRange.class);
        var second = getClass().getDeclaredFields()[2].getAnnotatedType().getAnnotation(InRange.class);


        assertFalse(checker.isNotCorrect(first, notNull));
        assertTrue(checker.isNotCorrect(second, Null));

    }

    @Test
    void createMessage() {
        var first = getClass().getDeclaredFields()[1].getAnnotatedType().getAnnotation(InRange.class);
        assertEquals(checker.createMessage(first), "Value is null. Please put some object this place");;
    }
}