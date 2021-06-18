package sugar.samples;

import sugar.anotations.Positive;

public class Unrelated {
    @Positive
    private int x;

    public Unrelated(int x) {
        this.x = x;
    }
}
