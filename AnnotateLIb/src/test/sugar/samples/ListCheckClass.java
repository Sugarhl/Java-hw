package sugar.samples;

import sugar.anotations.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("All")
@Constrained
public class ListCheckClass {
    public ListCheckClass() {
    }

    List<@Positive Integer> list = List.of(1, -1, -22);

    List<@Positive @NotEmpty List<@Positive Integer>> subList = List.of(
            List.of(1, -2, 2),
            new ArrayList<>(),
            List.of(1, 2, 4),
            List.of(1, 2, 4, -2),
            List.of(1, 123, -2, 2)
    );

    List<@Positive @Size(min = 2, max = 5) List<@Positive @NotEmpty List<@Positive Integer>>> subSubList =
            List.of(
                    List.of(
                            List.of(1, -2, 2),
                            new ArrayList<>(),
                            List.of(1, 2, 4),
                            List.of(1, 2, 4, -2),
                            List.of(1, 123, -2, 2)
                    ),
                    List.of(
                            List.of(1, 2, 4),
                            List.of(1, -2, 2),
                            List.of(1, 2, 4, -2),
                            new ArrayList<>(),
                            List.of(1, 123, -2, -2, 2),
                            new ArrayList<>()
                    ),
                    new ArrayList<>()
            );
}
