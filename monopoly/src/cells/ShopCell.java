package cells;


import game.Game;
import gamers.Bot;
import org.jetbrains.annotations.NotNull;
import playField.PlayField;
import gamers.Gamer;


public class ShopCell extends AbstractCell {
    private Gamer owner;
    private int cost;
    private int rent;
    private final double compesationCoeff;
    private final double improvementCoeff;

    /**
     * Строковый вид клетки в зависимоти от того кто на нее смотрит
     *
     * @param gamer Игрок
     * @return Вид клетки
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     */
    public String getImage(Gamer gamer) {
        if (owner == null) {
            typeCell = FREE_SHOP;
        } else if (gamer.equals(owner)) {
            typeCell = MY_SHOP;
        } else {
            typeCell = OPPONENT_SHOP;
        }
        return super.getImage(gamer);
    }

    /**
     * конструктор
     *
     * @param world Карта
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     */
    public ShopCell(PlayField world) {
        super(world);
        typeCell = 'S';
        compesationCoeff = PlayField.godRandom.nextDouble(0.1, 1);
        improvementCoeff = PlayField.godRandom.nextDouble(0.1, 2);
        cost = PlayField.godRandom.nextInt(50, 500);
        rent = (int) (cost * PlayField.godRandom.nextDouble(0.5, 0.9));
    }

    /**
     * Улучшает магазин
     */
    private void levelUp() {
        cost += Math.round(cost * improvementCoeff);
        rent += Math.round(rent * compesationCoeff);
        owner.pay(this, cost);
        Game.println(UPGRADE_MESSAGE, cost);
    }

    /**
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * Ход владельца
     */
    private void ownerStep() {
        var answer = owner.getAnswer(ASK_FOR_UPGRADE);
        if (answer) {
            levelUp();
        } else {
            System.out.println(DO_NOTHING);
        }
    }

    /**
     * Ветка вопроса о покупке
     *
     * @param gamer Игрок
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     */
    private void askForBuy(@NotNull Gamer gamer) {
        if (gamer.getCash() < cost) {
            Game.println(NOT_ENOUGH_MONEY);
            return;
        }
        var answer = gamer.getAnswer(String.format(ASK_FOR_BUY, cost));
        if (gamer instanceof Bot) {
            Game.println(BOT_CHOOSE, answer);
        }
        if (answer) {
            owner = gamer;
            owner.pay(this, cost);
            Game.println(SHOP_IS_YOUR, position.x, position.y);
        } else {
            Game.println(DONT_BUY);
        }
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
        Game.println(WELCOMES, position.x, position.y);
        if (owner == null) {
            askForBuy(gamer);
        } else if (owner.equals(gamer)) {
            ownerStep();
        } else {
            Game.println(PAY_RENT, rent, owner.getName());
            playerToPlayer(gamer, owner);
        }
    }

    /**
     * @param from Плательщик
     * @param to   Приемник
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     * Проведение денег через магазин между игроками
     */
    private void playerToPlayer(@NotNull Gamer from, @NotNull Gamer to) {
        if (from.pay(to, rent)) {

            to.raiseCash(rent);
        }
    }

    public static final char MY_SHOP = 'M';
    public static final char OPPONENT_SHOP = 'O';
    public static final char FREE_SHOP = 'S';
    public static final String ASK_FOR_UPGRADE = "Do you want to upgrade your shop? [y]/[n]";
    public static final String DO_NOTHING = "Ok, just relax in your shop.";
    public static final String DONT_BUY = "Ok, but dont cry when your enemy buys it.";
    public static final String ASK_FOR_BUY = "Do you want to buy this shop by %d ? [y]/[n]";
    public static final String UPGRADE_MESSAGE = "Congratulations, you have improved your shop!" +
            "\nNew cost: %d";
    public static final String SHOP_IS_YOUR = "Well, this shop on cell (%d, %d) is your!";
    public static final String WELCOMES = "You are in Shop on cell (%d,%d).";
    public static final String PAY_RENT = "You must pay %d to %s as rent.";
    public static final String BOT_CHOOSE = "Do you want to buy this shop?\nBot choose: %b";
    public static final String NOT_ENOUGH_MONEY = "Sorry, you haven't got enough money to buy this shop.";
}
