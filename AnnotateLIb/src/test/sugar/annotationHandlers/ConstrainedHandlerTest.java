package sugar.annotationHandlers;

import org.junit.jupiter.api.Test;
import sugar.anotations.Constrained;

import static org.junit.jupiter.api.Assertions.*;

@Constrained
class ConstrainedHandlerTest {
    ConstrainedHandler checker = new ConstrainedHandler();

    @Test
    void isNotCorrect() {
        assertFalse(checker.isNotCorrect(getClass().getAnnotation(Constrained.class), this));
    }

    @Test
    void createMessage() {
        assertEquals(checker.createMessage(getClass().getAnnotation(Constrained.class)), "");
    }
}