package utility;

import animal.Animal;
import transport.Cart;


public class Logger implements Runnable {
    Cart cart;
    Long lastTime;
    private final Long startTime;

    /**
     * КОнкструктор логгера.
     *
     * @param cart Телега, на которую завязан лог.(нужна для реализации вывода лога каждые две секунды)
     */
    public Logger(Cart cart) {
        this.cart = cart;
        lastTime = System.currentTimeMillis();
        startTime = System.currentTimeMillis();
    }

    /**
     * Метод печати информафии о действиях животного.
     *
     * @param animal Животное.
     * @param delay  Время, на которое оно заспыает.
     */
    public synchronized void printAnimalInfo(Animal animal, int delay) {
        System.out.printf("%s move cart and sleep for %d seconds\n", animal.name, delay);
    }

    /**
     * Метод печати информации о позиции телеги.
     */
    public synchronized void printCartInfo() {
        var position = cart.GetPosition();
        System.out.printf("Cart on position (%.2f,%.2f)\n", position.getX(), position.getY());
    }

    /**
     * Метод печати информации о времени.
     */
    public synchronized void printTimeInfo() {
        var currentTime = System.currentTimeMillis();
        var difference = currentTime - lastTime;
        var timeFromStart = currentTime - startTime;
        lastTime = currentTime;
        System.out.printf("Current time: %d \n", timeFromStart);
        System.out.printf("Time difference: %d \n", difference);
    }

    /**
     * Метод печати лога по телеге.
     */
    public synchronized long printLog() {
        long start = System.currentTimeMillis();
        System.out.println(separator);
        printCartInfo();
        printTimeInfo();
        System.out.println(separator);
        return System.currentTimeMillis() - start;
    }

    /**
     * Метод вывода лога по телеге каждые 2 секунды, с учетов траты времени на вывод.
     */
    @Override
    public void run() {
        while (true) {
            var delta = printLog();
            try {
                Thread.sleep(2000 - delta);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public static final String separator = "---------------------------------------";
}
