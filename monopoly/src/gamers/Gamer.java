package gamers;

import cells.ShopCell;
import game.Game;

public abstract class Gamer implements Transactions {
    protected int cash;
    protected int usedCash;
    protected int position;
    protected int debt;
    protected Game game;
    protected final String name;

    /**
     * @return Имя
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>* Возврщает имя
     */
    public String getName() {
        return name;
    }

    /**
     * @return Долг
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>* Возврщает долг
     */
    public int getDebt() {
        return debt;
    }

    /**
     * @return Сумма
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>* Возвращает сумму
     */
    public double getCash() {
        return cash;
    }

    /**
     * @return Сумма
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>* Возвращает потраченую сумму
     */
    public int getUsedCash() {
        return usedCash;
    }

    /**
     * @param game Игра
     * @param name Имя
     * @param cash Деньги
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>* Конструктор
     */
    Gamer(Game game, String name, int cash) {
        this.cash = cash;
        this.game = game;
        this.name = name;
        usedCash = 0;
    }

    /**
     * @param question Вопрос
     * @return Ответ
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>* Спрашивает о желании взять кредит
     */
    public abstract boolean askForDebt(String question);

    /**
     * @param question Вопрос
     * @return Ответ
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>* Просит принять решение
     */
    public boolean getAnswer(String question) {
        return game.getAnswer(question);
    }

    /**
     * @param shift Смещение
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>* Сдивгает игрока
     */
    public void move(int shift) {
        if (shift < 0)
            throw new IllegalArgumentException();
        int oldPosition = position;
        position = (position + shift) % game.world.countCells;
        game.world.moveOn(oldPosition, position, this);
    }

    /**
     * @param sender Запросивший снятие объект
     * @param sum    сумма
     * @return
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>* Проводит оплату
     */
    @Override
    public boolean pay(Object sender, int sum) {
        if (sum < 0) {
            throw new IllegalArgumentException();
        }
        if (sum > cash) {
            cash -= sum;
            game.endGame();
            return false;
        }
        if (sender instanceof ShopCell) {
            usedCash += sum;
        }
        cash -= sum;
        Game.println(PAY_SUCCESSFUL_MESSAGE, name, cash);
        return true;
    }

    /**
     * @param sum сумма
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>* Повышает счет на сумму
     */
    @Override
    public void raiseCash(int sum) {
        if (sum <= 0)
            throw new IllegalArgumentException();
        cash += sum;
        Game.println(СASH_RAISE_SUCCESSFUL_MESSAGE, name, sum, cash);
    }

    /**
     * @return
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>* Строковое представление иформации об игроке
     */
    public String getInfo() {
        return String.format("Name: %s\n" +
                        "    Cash: %d\n" +
                        "    Used cash: %d\n" +
                        "    Debt: %d",
                name, cash, usedCash, debt);
    }


    public static final String PAY_SUCCESSFUL_MESSAGE = "User: %s\n    Payment was successful.\n    Account balance: %d";
    public static final String СASH_RAISE_SUCCESSFUL_MESSAGE = "User %s\n    You raise %d.\n    Account balance: %d";
}
