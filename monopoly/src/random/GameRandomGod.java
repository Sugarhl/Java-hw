package random;

import java.security.KeyPair;
import java.util.Random;

public class GameRandomGod extends Random {
    Dice dices;

    /**
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * Конструктор геретароа рандома.
     */
    public GameRandomGod() {
        super();
        dices = new Dice();
    }

    /**
     * @param seed старт
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * Конструктор гереатора рандома от стартовой точки.
     */
    public GameRandomGod(long seed) {
        super(seed);
        dices = new Dice();
    }

    /**
     * @param l левая граница
     * @param r правая граница (не включается)
     * @return рандомное в диапозоне
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * Метод полччения случайного целого числа в полуинтервале
     */
    public int nextInt(int l, int r) {
        if (l > r)
            throw new IllegalArgumentException();
        return l + nextInt(r - l) + 1;
    }

    /**
     * @param l левая граница
     * @param r правая граница (не включается)
     * @return рандомное в диапозоне
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * Метод полччения случайного действительно числа в полуинтервале
     */
    public double nextDouble(double l, double r) {
        if (l > r)
            throw new IllegalArgumentException();
        return l + nextDouble() * (r - l);
    }

    /**
     * @return кубики
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * Метод подбрасывания кубиков
     */
    public Dice rollTheDice() {
        dices.nextRoll();
        return dices;
    }

    /**
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * Класс кубиков
     */
    public class Dice {
        int first;
        int second;

        /**
         * @return число смещения
         * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
         * Метод получения смещения
         */
        public int getShift() {
            return second + first;
        }

        /**
         * @return число на кубике
         * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
         * Метод получения первой кости
         */
        public int getFirst() {
            return first;
        }

        /**
         * @return число на кубике
         * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
         * Метод получения второй кости
         */
        public int getSecond() {
            return second;
        }

        /**
         * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
         * Метод приваивает рандомные значения числом на кубиках
         */
        void nextRoll() {
            first = nextInt(6) + 1;
            second = nextInt(6) + 1;
        }
    }
}
