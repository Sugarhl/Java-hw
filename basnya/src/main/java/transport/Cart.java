package transport;

import animal.Animal;

import java.awt.geom.Point2D;


public class Cart {
    Point2D.Double pos;


    /**
     * КОнтруктор телеги по умолчанию.
     */
    public Cart() {
        pos = new Point2D.Double(0, 0);
    }

    /**
     * Конутрктор телеги по положению.
     * @param x Абсциисса.
     * @param y Ордината.
     */
    public Cart(double x, double y) {
        pos = new Point2D.Double(x, y);
    }

    /**
     * Метод передвижения телеги с помощью животного.
     *
     * @param engine Животное-двигатель.
     */
    public synchronized void move(Animal engine) {
        pos.x = pos.x + engine.strength * Math.cos(engine.angle);
        pos.y = pos.y + engine.strength * Math.sin(engine.angle);

    }

    /**
     * Метод получения информации о положении телеги.
     *
     * @return Точка позиции телеги.
     */
    public synchronized Point2D.Double GetPosition() {
        var currentPosition = new Point2D.Double();
        currentPosition.setLocation(pos);
        return pos;
    }
}
