package animal;

import transport.Cart;
import utility.Logger;

import java.util.Random;

public class Animal implements Runnable {
    public final double angle;
    public final double strength;
    public final String name;
    private Cart cart;
    private Logger logger;

    /**
     * Консткруктор животного.
     * @param name Имя
     * @param cart Телега, которую двигаем
     * @param angle Угол под которым идем
     * @param logger Логгер
     */
    public Animal(String name, Cart cart, double angle, Logger logger) {
        this.name = name;
        strength = strengthMin + rnd.nextDouble() * strengthMax;
        this.angle = angle;
        this.cart = cart;
        this.logger = logger;
    }

    /**
     * Метод жизни животного.
     * Оно сдивгает телегу, просит логгер написать об этом
     * и засыпает.
     */
    @Override
    public void run() {
        while (true) {
            cart.move(this);
            int delay = sleepMin + rnd.nextInt(sleepMax);
            logger.printAnimalInfo(this, delay);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private static final Random rnd = new Random();
    public static final int strengthMax = 9;
    public static final int strengthMin = 1;
    public static final int sleepMax = 4000;
    public static final int sleepMin = 1000;
}
