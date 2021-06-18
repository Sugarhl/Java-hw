package utility;

import animal.Animal;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import transport.Cart;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

class LoggerTest {

    static Random rnd = new Random();
    static Cart cart = new Cart();
    static Cart nCart;
    static Logger logger = new Logger(cart);
    static Logger nLogger;
    static Animal animal = new Animal("Dog", cart, 300, logger);
    static private ByteArrayOutputStream output;

    @BeforeEach
    void init() {
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }


    @Test
    void printAnimalInfo() {
        int delay = rnd.nextInt();
        logger.printAnimalInfo(animal, delay);
        Assert.assertEquals("Dog move cart and sleep for " + delay + " seconds\n",
                output.toString());
    }

    @Test
    void printCartInfo() {
        double x = rnd.nextDouble() * 10000 - 5000;
        double y = rnd.nextDouble() * 10000 - 5000;
        nCart = new Cart(x, y);
        nLogger = new Logger(nCart);
        nLogger.printCartInfo();
        Assert.assertEquals(String.format("Cart on position (%.2f,%.2f)\n", x, y), output.toString());

    }

    @AfterAll
    static void Clean() {
        System.setOut(null);
    }
}