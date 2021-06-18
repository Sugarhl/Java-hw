package game;

import gamers.Bot;
import gamers.Gamer;
import gamers.Player;
import playField.PlayField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {
    public PlayField world;

    private final ArrayList<Gamer> gamers = new ArrayList<Gamer>();
    private Scanner in;
    private final int money;
    private int turn;
    private boolean continueGame = true;

    /**
     * @param format формат
     * @param args   агрументы
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>* Форматированный вывод
     */
    public static void println(String format, Object... args) {
        System.out.printf((format) + "%n", args);
    }

    Game(int money) {
        in = new Scanner(System.in);
        this.money = money;
    }

    /**
     * @param height Высота
     * @param width  Ширина
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>* Устанавливает в игру поле
     */
    private void setWorld(int width, int height) {
        world = new PlayField(width, height, this);
    }

    /**
     * @param args Аргументы командной строки
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>* Мейн
     */
    public static void main(String[] args) {
        try {


            List<Integer> agruments = getArguments(args);
            Game game = new Game(agruments.get(2));
            game.startGame(agruments.get(0), agruments.get(1));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * @return Игрок
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * * Регистрирует игрока
     */
    private Gamer registerPlayer() {
        System.out.print("Please enter your name: ");
        return new Player(this, in.nextLine(), money);
    }

    /**
     * @param width  Ширина
     * @param height Высота
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * * Иницрует начало игры
     */
    public void startGame(int width, int height) {
        setWorld(width, height);
        world.generateMap();

        gamers.add(registerPlayer());
        gamers.add(new Bot(this, money));

        world.getInfo();
        System.out.println(SEPARATOR);
        turn = PlayField.godRandom.nextInt(gamers.size());
        var next = ((turn + 1) % gamers.size());
        world.setStart(gamers.get(turn), gamers.get(next));
        processGame();
    }

    /**
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * * Процесс игры
     */
    private void processGame() {
        while (continueGame) {
            Game.println(TURN, gamers.get(turn).getName());
            world.nextTurn(gamers.get(turn));

            turn = (turn + 1) % gamers.size();
            if (continueGame) {
                System.out.println(SEPARATOR);
                System.out.println(NEXT_TURN);
                var temp = in.nextLine();
            }
        }
    }

    /**
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * Окончание игры
     */
    public void endGame() {
        continueGame = false;
        System.out.println();
        System.out.println(END_GAME);
        for (int i = 0; i < gamers.size(); ++i) {
            if (i == 0) {
                System.out.print(WINNER);
            } else {
                System.out.print(LOSER);
            }
            turn = (turn + 1) % gamers.size();
            System.out.println(gamers.get(turn).getInfo());
            System.out.println(SEPARATOR);
        }
    }

    /**
     * Получение ответа из консоли
     *
     * @param question вопрос
     * @return Ответ
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     */
    public boolean getAnswer(String question) {
        System.out.println(question);
        do {
            System.out.print(YOUR_ANSWER);
            var answer = in.nextLine();
            if (POSITIVE_ANSWERS.contains(answer)) {
                return true;
            } else if (NEGATIVE_ANSWERS.contains(answer)) {
                return false;
            }
            System.out.println("You should print");
            for (String item : POSITIVE_ANSWERS)
                System.out.print('"' + item + "\" ");
            System.out.println("if your answer is positive");
            for (String item : NEGATIVE_ANSWERS)
                System.out.print('"' + item + "\" ");
            System.out.println("if your answer is negative");
        } while (true);
    }

    /**
     * Спршивает в консоли о размере долга
     *
     * @param question Вопрос
     * @param warning  Предупреждение
     * @param bound    Граница
     * @return Сумма
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     */

    public int getDebt(String question, String warning, int bound) {
        System.out.println(question);
        System.out.print(YOUR_SUM);
        boolean isNotCorrerct = true;
        int answer = 0;
        while (isNotCorrerct) {
            while (!in.hasNextInt()) {
                in.next();
                System.out.println(warning);
                System.out.print(YOUR_SUM);
            }
            answer = in.nextInt();
            in.nextLine();
            isNotCorrerct = (answer <= 0 || answer > bound);
            if (isNotCorrerct) {
                System.out.println(warning);
                System.out.print(YOUR_SUM);
            }
        }
        return answer;
    }

    private static List<Integer> getArguments(String[] args) {
        int width;
        int height;
        int money;
        try {
            width = Integer.parseInt(args[0]);
            if (width < 6 || width > 30)
                throw new IllegalArgumentException("Incorrect width");
        } catch (Exception ex) {
            width = 10;
        }
        try {
            height = Integer.parseInt(args[1]);
            if (height < 6 || height > 30)
                throw new IllegalArgumentException("Incorrect height");

        } catch (Exception ex) {
            height = 10;
        }
        try {
            money = Integer.parseInt(args[2]);
            if (money < 500 || money > 15000)
                throw new IllegalArgumentException("Incorrect money");
        } catch (Exception ex) {
            money = 7500;
        }
        List<Integer> list = new ArrayList<Integer>();
        list.add(width);
        list.add(height);
        list.add(money);
        return list;
    }

    public static final String SEPARATOR = "\n.........................." +
            "................................................................\n";
    public static final String TURN = "It is turn for %s!";
    public static final String YOUR_ANSWER = "Your answer: ";
    public static final String YOUR_SUM = "Your sum: ";
    public static final String NEXT_TURN = "To next turn enter anything";
    public static final String END_GAME = "!!!CONGRATULATIONS!!!\n" +
            "      GAME OVER";
    public static final String WINNER = "WINNER: ";
    public static final String LOSER = "LOSER: ";
    public static final List<String> POSITIVE_ANSWERS = Arrays.asList("y", "Y", "yes", "Yes");
    public static final List<String> NEGATIVE_ANSWERS = Arrays.asList("n", "N", "no", "Yes");
}
