package cells;

import gamers.Bot;
import gamers.Gamer;
import gamers.Player;
import playField.PlayField;


public abstract class AbstractCell implements PlayerOnCell {
    protected char typeCell;
    protected char leftBound = LEFT_BOUND;
    protected char rightBound = RIGHT_BOUND;
    protected final PlayField world;
    protected Position position;
    private Gamer lastGamer = null;

    /**
     * Востановение рамок клетки
     *
     * @param gamer Игрок
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     */
    public void restoreBounds(Gamer gamer) {
        leftBound = LEFT_BOUND;
        rightBound = RIGHT_BOUND;
        if (lastGamer != null && !lastGamer.equals(gamer)) {
            setBounds(lastGamer);
        }
    }

    /**
     * Метод получения строки представления клетки.
     *
     * @param gamer
     * @return
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     */
    public String getImage(Gamer gamer) {
        return String.format("%c%c%c", leftBound, typeCell, rightBound);
    }

    /**
     * Сеттер позиции
     *
     * @param x
     * @param y
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     */
    public void setPosition(int x, int y) {
        if (x < 1 || y < 1)
            throw new IllegalArgumentException();
        position = new Position(x, y);
    }

    /**
     * Метод установки рамоек клетки
     *
     * @param gamer Игрок
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     */
    protected void setBounds(Gamer gamer) {
        if (gamer instanceof Player) {
            if (rightBound == RIGHT_BOUND) {
                rightBound = Player.RIGHT_BOUND;
            }
            leftBound = Player.LEFT_BOUND;
        } else {
            if (rightBound == RIGHT_BOUND) {
                rightBound = Bot.RIGHT_BOUND;
            }
            leftBound = Bot.LEFT_BOUND;
        }
        lastGamer = gamer;
    }

    /**
     * Утановка рамок для начальной клетки
     *
     * @param first  Первый по ходу
     * @param second Второй по ходу
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     */
    public void setStartCell(Gamer first, Gamer second) {
        if (first instanceof Player) {
            leftBound = Bot.LEFT_BOUND;
            rightBound = Player.RIGHT_BOUND;
        } else {
            leftBound = Player.LEFT_BOUND;
            rightBound = Bot.RIGHT_BOUND;
        }
        lastGamer = second;
    }

    /**
     * Констурктор
     *
     * @param world Карта
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     */
    AbstractCell(PlayField world) {
        this.world = world;
        typeCell = 'a';
    }

    /**
     * Класс позиции
     *
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     */
    protected static class Position {

        public final int x;
        public final int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

    public static final char LEFT_BOUND = '[';
    public static final char RIGHT_BOUND = ']';
}
