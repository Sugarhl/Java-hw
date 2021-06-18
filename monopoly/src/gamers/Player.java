package gamers;

import game.Game;

public class Player extends Gamer {
    /**
     * Спршивает о желании взять кредит
     *
     * @param question
     * @return Ответ
     */
    @Override
    public boolean askForDebt(String question) {
        return game.getAnswer(question);
    }

    /**
     * Спрашивает игрока о размере кредита
     *
     * @param question Вопрос
     * @param warning  Предупреждение
     * @param bound    Граница
     * @return Сумма кредита
     */

    public int askForSizeDebt(String question, String warning, int bound) {
        return game.getDebt(question, warning, bound);
    }

    /**
     * Проводит деньги из банка игроку и назначет долг
     *
     * @param raise Прирост
     * @param debt  Досг
     */
    public void becomeDebtor(int raise, int debt) {
        if (debt < 0)
            throw new IllegalArgumentException();
        this.debt = debt;
        raiseCash(raise);
    }

    /**
     * Конструктор
     *
     * @param game Игра
     * @param name Имя
     * @param cash Деньги
     */
    public Player(Game game, String name, int cash) {
        super(game, name, cash);
    }

    public static final char RIGHT_BOUND = '>';
    public static final char LEFT_BOUND = '<';

}