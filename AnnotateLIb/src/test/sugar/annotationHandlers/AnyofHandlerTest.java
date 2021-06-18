package sugar.annotationHandlers;

import org.junit.jupiter.api.Test;
import sugar.anotations.AnyOf;

import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.*;

class AnyofHandlerTest {
    AnyofHandler checker = new AnyofHandler();

    @AnyOf({"Sugar", "Vladik", "Dima"})
    String correct = "Dima";

    @AnyOf({"Девачки", "Успех", "Деньги"})
    String incorrect = "Сахар";

    @Test
    void isNotCorrect() {

        Annotation correctAn = getClass().getDeclaredFields()[1].getAnnotatedType().getAnnotation(AnyOf.class);
        Annotation incorrectAn = getClass().getDeclaredFields()[2].getAnnotatedType().getAnnotation(AnyOf.class);

        assertFalse(checker.isNotCorrect(correctAn, correct));
        assertTrue(checker.isNotCorrect(incorrectAn, incorrect));

    }

    @Test
    void createMessage() {
        Annotation correctAn = getClass().getDeclaredFields()[1].getAnnotatedType().getAnnotation(AnyOf.class);
        Annotation incorrectAn = getClass().getDeclaredFields()[2].getAnnotatedType().getAnnotation(AnyOf.class);
        assertEquals(checker.createMessage(incorrectAn), "The value must be one of the list [Девачки, Успех, Деньги]");
        assertEquals(checker.createMessage(correctAn), "The value must be one of the list [Sugar, Vladik, Dima]");

    }
}