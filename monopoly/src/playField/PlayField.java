package playField;

import cells.*;
import game.Game;
import gamers.Gamer;
import random.GameRandomGod;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class PlayField {
    private final int height;
    private final int width;
    private ArrayList<AbstractCell> map;
    private final Game game;
    public final int countCells;
    public final static GameRandomGod godRandom = new GameRandomGod();

    /**
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * Создает поле
     */
    public void generateMap() {
        map = new ArrayList<AbstractCell>();
        map.add(new EmptyCell(this));
        map.addAll(generateStreet(width));

        map.add(new EmptyCell(this));
        map.addAll(generateStreet(height));

        map.add(new EmptyCell(this));
        map.addAll(generateStreet(width));

        map.add(new EmptyCell(this));
        map.addAll(generateStreet(height));
        setPositions();
    }

    /**
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * Устанавливает позиции клеткам
     */
    private void setPositions() {
        int x = 1;
        int y = 1;
        for (int i = 0; i < map.size(); i++) {
            map.get(i).setPosition(x, y);
            if (i < width - 1) {
                x++;
            } else if (i < height + width - 2) {
                y++;
            } else if (i < 2 * width + height - 3) {
                x--;
            } else {
                y--;
            }
        }
    }

    /**
     * @param length длинна стороны поля
     * @return Улица
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * Генерирует цлицу между пустыми клетками
     */
    private ArrayList<AbstractCell> generateStreet(int length) {
        ArrayList<AbstractCell> street = new ArrayList<AbstractCell>();
        var taxi = godRandom.nextInt(3);
        var penalty = godRandom.nextInt(3);
        street.add(new BankCell(this));
        var shops = length - taxi - penalty - 3;
        if (shops < 0) {
            taxi--;
            penalty--;
        }
        shops = length - taxi - penalty - 3;
        for (int i = 0; i < taxi; i++) {
            street.add(new TaxiCell(this));
        }
        for (int i = 0; i < penalty; i++) {
            street.add(new PenaltyCell(this));
        }
        for (int i = 0; i < shops; i++) {
            street.add(new ShopCell(this));
        }
        // Collections.shuffle(street);
        return street;
    }

    /**
     * @param height высота
     * @param width  Ширина
     * @param game   Игра
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * Конструктор
     */
    public PlayField(int width, int height, Game game) {
        this.height = height;
        this.width = width;
        this.game = game;
        countCells = 2 * (height + width) - 4;
    }

    /**
     * @param gamer Игрок
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * Выводит карту в консоль для игрока
     */

    public void showMap(Gamer gamer) {
        String[] field = new String[height];
        for (int i = 0; i < height; i++) {
            field[i] = "";
        }
        for (int i = 0; i < width; i++) {
            field[0] += map.get(i).getImage(gamer);
            field[height - 1] += map.get(countCells - height + 1 - i).getImage(gamer);
        }
        for (int i = 1; i < height - 1; i++) {
            field[i] += map.get(countCells - i).getImage(gamer);
            for (int j = 0; j < width - 2; j++) {
                field[i] += "...";
            }
            field[i] += map.get(width - 1 + i).getImage(gamer);
        }
        for (int i = 0; i < height; i++) {
            System.out.println(field[i]);
        }
    }

    /**
     * @param gamer Игрок
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * Совершает ход игрока
     */
    public void nextTurn(Gamer gamer) {
        var dice = godRandom.rollTheDice();
        Game.println(ROLL_MESSAGE, dice.getFirst(),
                dice.getSecond());
        System.out.println(Game.SEPARATOR);
        showMap(gamer);
        conversion();
        gamer.move(dice.getShift());
    }

    /**
     * @param from  Стартовая позиция
     * @param to    Конечная позиция
     * @param gamer Игрок
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * Двигает игрока по карте
     */
    public void moveOn(int from, int to, Gamer gamer) {
        if (to < 0 || to >= countCells || from < 0 || from >= countCells)
            throw new IllegalArgumentException();
        map.get(from).restoreBounds(gamer);
        map.get(to).gamerOn(gamer);
    }

    /**
     * @param first  Первый по ходу
     * @param second Второй по ходу
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * Устанавливает маркировку игроков на первой клетке
     */
    public void setStart(Gamer first, Gamer second) {
        map.get(0).setStartCell(first, second);
    }

    /**
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * Выводит информацию о полях
     */
    public void getInfo() {
        System.out.println("Info:");
        System.out.printf("    penaltyCoeff: %.4f %n", PenaltyCell.penaltyCoeff);
        System.out.printf("    creditCoeff %.4f %n", BankCell.creditCoeff);
        System.out.printf("    debtCoeff %.4f %n", BankCell.debtCoeff);
    }

    /**
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * Метод выводящий переход между картами
     */
    public void conversion() {
        System.out.println();
        System.out.println(" ↓ ".repeat(width));
        System.out.println();
    }

    public static final String ROLL_MESSAGE = "You threw out %d : %d !";

}


