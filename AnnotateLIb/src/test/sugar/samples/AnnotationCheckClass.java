package sugar.samples;

import sugar.anotations.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс со всеми вердиктами по аннотациям
 */
@Constrained
public class AnnotationCheckClass {
    public AnnotationCheckClass() {
    }

    @NotNull
    public Object notNull = null;

    @NotNull
    Object trueNotNull = new Object();

    @Positive
    int positive = -1;

    @Positive
    int truePositive = 1;

    @Negative
    byte negative = 1;

    @Negative
    int trueNegative = -1;


    @InRange(min = 1, max = 1000)
    long inRange = 100034;

    @InRange(min = 3, max = 7)
    long trueInRange = 5;

    @NotBlank
    String notBlank = "";
    @NotBlank
    String trueNotBlank = "sugar";

    @NotEmpty
    Set<Integer> notEmpty = new HashSet<>();

    @NotEmpty
    Set<Integer> trueNotEmpty = Set.of(123, 12321);

    @Size(min = 1, max = 4)
    List<Integer> size = List.of(1, 2, 3, 4, 5);

    @Size(min = 1, max = 8)
    List<Integer> trueSize = List.of(1, 2, 3, 4, 5);


    @AnyOf({"Danya", "Andrey", "Dima"})
    String anyof = "Sugar";

    @AnyOf({"Danya", "Andrey", "Dima", "Sugar"})
    String trueAnyof = "Sugar";

//    @Negative @Positive
//    short zero = 0;
}


