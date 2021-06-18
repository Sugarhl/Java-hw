package transport;

import animal.Animal;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import utility.Logger;

import java.util.Random;

class CartTest {
    static Random rnd = new Random();
    Cart cart = new Cart();
    Cart nCart;
    Logger logger = new Logger(cart);
    Animal animal = new Animal("test", cart, 10, logger);
    final double delta = 0.00000001;
    final int testCount = 40;

    @Test
    void move() {
        cart.move(animal);
        Assert.assertEquals(animal.strength * Math.cos(animal.angle), cart.pos.x, delta);
        Assert.assertEquals(animal.strength * Math.sin(animal.angle), cart.pos.y, delta);
        for (int i = 0; i < 40; i++) {
            double x = rnd.nextDouble() * 10000 - 5000;
            double y = rnd.nextDouble() * 10000 - 5000;
            nCart = new Cart(x, y);
            nCart.move(animal);
            Assert.assertEquals(x + animal.strength * Math.cos(animal.angle), nCart.pos.x, delta);
            Assert.assertEquals(y + animal.strength * Math.sin(animal.angle), nCart.pos.y, delta);
        }
    }

    @Test
    void getPosition() {
        for (int i = 0; i < testCount; i++) {
            double x = rnd.nextDouble() * 10000 - 5000;
            double y = rnd.nextDouble() * 10000 - 5000;
            nCart = new Cart(x, y);
            Assert.assertEquals(x, nCart.pos.x, delta);
            Assert.assertEquals(y, nCart.pos.y, delta);
        }
    }
}