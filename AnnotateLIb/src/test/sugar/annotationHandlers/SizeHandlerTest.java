package sugar.annotationHandlers;

import org.junit.jupiter.api.Test;
import sugar.anotations.NotEmpty;
import sugar.anotations.Size;

import java.lang.annotation.Annotation;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SizeHandlerTest {
    SizeHandler checker = new SizeHandler();

    @Size(min = 2, max = 5)
    Set<Integer> low = Set.of(1);

    @Size(min = 1, max = 6)
    Map<Integer, Integer> high = Map.of(1, 2, 3, 4, 5, 6,
            7, 8, 9, 10, 11, 12, 13, 14);

    @Size(min = 2, max = 5)
    List<Integer> center = List.of(1, 2, 3);

    @Size(min = 2, max = 5)
    String lowBound = "12";

    @Size(min = 2, max = 5)
    String highBound = "12345";

    @Test
    void isNotCorrect() {
        Annotation[] annotations = new Annotation[8];
        for (int i = 1; i < 6; i++) {
            annotations[i - 1] = getClass().getDeclaredFields()[i]
                    .getAnnotatedType().getAnnotation(Size.class);
        }
        assertFalse(checker.isNotCorrect(annotations[3], lowBound));
        assertFalse(checker.isNotCorrect(annotations[4], highBound));
        assertFalse(checker.isNotCorrect(annotations[2], center));
        assertTrue(checker.isNotCorrect(annotations[0], low));
        assertTrue(checker.isNotCorrect(annotations[1], high));
    }

    @Test
    void createMessage() {
        var first = getClass().getDeclaredFields()[1]
                .getAnnotatedType().getAnnotation(Size.class);
        var second = getClass().getDeclaredFields()[2]
                .getAnnotatedType().getAnnotation(Size.class);
        assertEquals(checker.createMessage(first), "Size of this object must be in range [2, 5]");
        assertEquals(checker.createMessage(second), "Size of this object must be in range [1, 6]");

    }
}