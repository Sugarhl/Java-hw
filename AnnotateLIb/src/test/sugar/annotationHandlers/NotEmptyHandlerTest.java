package sugar.annotationHandlers;

import org.junit.jupiter.api.Test;
import sugar.anotations.NotEmpty;

import java.lang.annotation.Annotation;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class NotEmptyHandlerTest {
    NotEmptyHandler checker = new NotEmptyHandler();
    @NotEmpty
    Set<Integer> emptySet = new HashSet<>();
    @NotEmpty
    Map<Integer, Integer> emptyMap = new HashMap<>();
    @NotEmpty
    List<Integer> emptyList = new ArrayList<>();
    @NotEmpty
    String emptyString = "";

    @NotEmpty
    Set<Integer> notEmptySet = Set.of(1, 2, 3, 4);
    @NotEmpty
    Map<Integer, Integer> notEmptyMap = Map.of(1, 2, 3, 4);
    @NotEmpty
    List<Integer> notEmptyList = List.of(1, 2, 3, 4);
    @NotEmpty
    String notEmptyString = "sugar";


    @Test
    void isNotCorrect() {
        Annotation[] annotations = new Annotation[8];
        for (int i = 1; i < 9; i++) {
            annotations[i - 1] = getClass().getDeclaredFields()[i]
                    .getAnnotatedType().getAnnotation(NotEmpty.class);
        }
        assertTrue(checker.isNotCorrect(annotations[0], emptySet));
        assertTrue(checker.isNotCorrect(annotations[1], emptyMap));
        assertTrue(checker.isNotCorrect(annotations[2], emptyList));
        assertTrue(checker.isNotCorrect(annotations[3], emptyString));

        assertFalse(checker.isNotCorrect(annotations[4], notEmptySet));
        assertFalse(checker.isNotCorrect(annotations[5], notEmptyMap));
        assertFalse(checker.isNotCorrect(annotations[5], notEmptyList));
        assertFalse(checker.isNotCorrect(annotations[7], notEmptyString));

    }

    @Test
    void createMessage() {
        var annotation = getClass().getDeclaredFields()[1]
                .getAnnotatedType().getAnnotation(NotEmpty.class);
        assertEquals(checker.createMessage(annotation), "Container is empty. Please add some elements");
    }
}