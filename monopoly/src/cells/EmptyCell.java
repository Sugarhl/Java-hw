package cells;

import game.Game;
import playField.PlayField;
import gamers.Gamer;


public class EmptyCell extends AbstractCell {
    /**
     * Конструкторк
     *
     * @param world Корта
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     */
    public EmptyCell(PlayField world) {
        super(world);
        typeCell = 'E';
    }

    /**
     * Обработка наступания на клетку игроком
     *
     * @param gamer Игрок
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     */
    @Override
    public void gamerOn(Gamer gamer) {
        setBounds(gamer);
        world.showMap(gamer);
        System.out.println(Game.SEPARATOR);
        Game.println(WELCOME, position.x, position.y);
    }

    public static final String WELCOME = "You are on empty cell (%d,%d).\nJust relax here.";
}
