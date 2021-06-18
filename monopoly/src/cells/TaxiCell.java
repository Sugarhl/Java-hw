package cells;

import game.Game;
import playField.PlayField;
import gamers.Gamer;

public class TaxiCell extends AbstractCell {
    private final int taxiDistance;

    /**
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * Конструктор
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>* Конструктор с именем
     */
    public TaxiCell(PlayField world) {
        super(world);
        taxiDistance = PlayField.godRandom.nextInt(3, 5);
        typeCell = 'T';
    }

    /**
     * @param gamer Игрое
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * Обработка наступания нка клетку игроком
     */
    @Override
    public void gamerOn(Gamer gamer) {
        setBounds(gamer);
        world.showMap(gamer);
        System.out.println(Game.SEPARATOR);
        Game.println(WELCOME, position.x, position.y);
        Game.println(TAXI_MESSAGE, taxiDistance);
        System.out.println(Game.SEPARATOR);
        gamer.move(taxiDistance);
    }

    public static final String WELCOME = "You catch taxi on cell (%d,%d)";
    public static final String TAXI_MESSAGE = "You are shifted forward by %d cells";
}
