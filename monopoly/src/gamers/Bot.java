package gamers;

import game.Game;
import playField.PlayField;

public class Bot extends Gamer {
    /**
     * @param game Игра
     * @param cash Деньги
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>* Контструктор с дефолтовым именем
     */
    public Bot(Game game, int cash) {
        super(game, "Bot", cash);
    }

    /**
     * @param game Игра
     * @param name Имя
     * @param cash Деньги
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>* Конструктор с именем
     */
    public Bot(Game game, String name, int cash) {
        super(game, name, cash);
    }

    /**
     * @param question Вопрос
     * @return Всегда отвечает нет
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>* Спршивает о желании взять кредит
     */
    @Override
    public boolean askForDebt(String question) {
        return false;
    }

    /**
     * @param question Вопрос
     * @return Рандомное решение
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>* Просит принять решение
     */
    @Override
    public boolean getAnswer(String question) {
        return PlayField.godRandom.nextBoolean();
    }

    public static final char RIGHT_BOUND = '*';
    public static final char LEFT_BOUND = '*';
}
