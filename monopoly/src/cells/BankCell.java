package cells;

import game.Game;
import org.jetbrains.annotations.NotNull;
import playField.PlayField;
import gamers.Gamer;
import gamers.Player;

public class BankCell extends AbstractCell {
    public static final double debtCoeff = PlayField.godRandom.nextDouble(1, 3);
    public static final double creditCoeff = PlayField.godRandom.nextDouble(0.002, 0.2);
    ;

    /**
     * Конструктор
     *
     * @param world Карта
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     */
    public BankCell(PlayField world) {
        super(world);
        typeCell = '$';
    }

    /**
     * Обработка наступания на клетку игроком
     *
     * @param gamer Игрок
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     */
    @Override
    public void gamerOn(@NotNull Gamer gamer) {
        setBounds(gamer);
        world.showMap(gamer);
        System.out.println(Game.SEPARATOR);
        Game.println(WELCOMES, position.x, position.y);
        if (gamer instanceof Player) {
            if (gamer.getDebt() == 0) {
                askForGetDebt(gamer);
            } else {
                payDebt((Player) gamer);
            }
        } else {
            System.out.println(BOT_MESSAGE);
        }
    }

    /**
     * Ветка получения кредита
     *
     * @param gamer Игрок
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     */
    private void askForGetDebt(Gamer gamer) {
        int limit = (int) Math.round(creditCoeff * gamer.getUsedCash());
        if (limit == 0) {
            System.out.println(NO_TRUST);
            return;
        }
        var answer = gamer.askForDebt(ASK_FOR_LOAN);
        if (answer) {
            var raise = ((Player) gamer).askForSizeDebt(
                    String.format(ASK_FOR_SUMM, limit),
                    String.format(WARNING, limit), limit);
            if (raise < 0 || raise > limit)
                throw new IllegalArgumentException();
            int debt = (int) Math.round(raise * debtCoeff);
            ((Player) gamer).becomeDebtor(raise, debt);
            Game.println(APPROVE_LOAN, raise, debt);
        } else {
            System.out.println(DO_NOTHING);
        }
    }

    /**
     * Ветка уплаты долга
     *
     * @param player Игрок
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>
     */
    private void payDebt(Player player) {
        Game.println(PAY_DEBT, player.getDebt());
        player.pay(this, player.getDebt());
    }

    public static final String ASK_FOR_LOAN = "Do you want to take out a loan?[y]/[n]";
    public static final String DO_NOTHING = "Ok, do your serious adult business";
    public static final String ASK_FOR_SUMM = "How much money do you want to get? (no more than %d)";
    public static final String WARNING = "You should input real number no more than %d";
    public static final String PAY_DEBT = "You must pay to bank %d";
    public static final String APPROVE_LOAN = "The Bank approved %d loan for you.\nYou must return to Bank %d";
    public static final String BOT_MESSAGE = "Sorry, we don't serve bots. :C";
    public static final String WELCOMES = "You are in Bank on cell (%d,%d).";
    public static final String NO_TRUST = "The Bank doesn't trust you yet. You should start spending your money.";
}
