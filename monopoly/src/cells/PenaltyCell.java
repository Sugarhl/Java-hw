package cells;

import game.Game;
import playField.PlayField;
import gamers.Gamer;

public class PenaltyCell extends AbstractCell {
    public static final double penaltyCoeff = PlayField.godRandom.nextDouble(0.01, 0.1);

    /**
     * конструктор
     *
     * @param world Карта
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     */
    public PenaltyCell(PlayField world) {
        super(world);
        typeCell = '%';

    }

    /**
     * @param gamer Игрок
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * Обработка наступания на клетку игроком
     */
    @Override
    public void gamerOn(Gamer gamer) {
        setBounds(gamer);
        world.showMap(gamer);
        System.out.println(Game.SEPARATOR);
        Game.println(WELCOME, position.x, position.y);
        int penalty = (int) Math.round(gamer.getCash() * penaltyCoeff);
        Game.println(NOTICE_PENALTY, penalty);
        gamer.pay(this, penalty);
        System.out.println(Game.SEPARATOR);
    }

    public static final String NOTICE_PENALTY = "You be fined %d";
    public static String WELCOME = "You are on PenaltyCell (%d,%d).";
}
