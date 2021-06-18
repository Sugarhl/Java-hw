package sugar.samples;

import sugar.anotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс пример для теста
 */
@Constrained
public class Sample {
    @NotNull
    @Positive
    @InRange(min = -31, max = 1)
    Integer id;

    @NotNull
    String name;

    @AnyOf({"Dima", "Sahsa", "Danya"})
    String sername = "Nikita";

    @NotBlank
    String kek = "";

    @NotEmpty
    List<Object> emptyList = new ArrayList<>();

    List<@Positive Integer> positive = List.of(-1, -2, -3, 5);

    ListCheckClass lists = new ListCheckClass();
    AnnotationCheckClass annotationCheckClass;


    List<@NotNull @Size(min = 2, max = 3) @NotEmpty List<@NotNull Integer>> friends;

    public Sample(int id, String name, List<List<Integer>> friends, AnnotationCheckClass annotationCheckClass) {
        this.id = id;
        this.name = name;
        this.friends = friends;
        this.annotationCheckClass = annotationCheckClass;
    }
}


