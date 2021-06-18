package utility;

import animal.Animal;
import transport.Cart;

import java.util.ArrayList;

public class Program {
    /**
     * Моделирование жизни лебедя рака и щуки.
     *
     * @param args Аргументы командной строки
     */
    public static void main(String[] args) {
        double x = 0;
        double y = 0;
        if (args.length == 2) {
            try {
                x = Double.parseDouble(args[0]);
                y = Double.parseDouble(args[1]);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else if (args.length != 0) {
            System.out.println("Error. Incorrect arguments of command line.\n" +
                    "You should use 2 parameters of position cart or run without arguments.");
            return;
        }
        Cart cart = new Cart(x, y);
        Logger logger = new Logger(cart);

        ArrayList<Animal> animals = new ArrayList<>();

        animals.add(new Animal("Crayfish", cart, Math.PI / 6, logger));
        animals.add(new Animal("Pike", cart, Math.PI, logger));
        animals.add(new Animal("Swan", cart, Math.PI * 5 / 3, logger));

        ArrayList<Thread> animalThreads = new ArrayList<>();
        for (Animal animal : animals) {
            animalThreads.add(new Thread(animal));
        }

        Thread log = new Thread(logger);
        log.start();

        for (Thread animal : animalThreads) {
            animal.start();
        }

        try {
            Thread.sleep(25000);
        } catch (InterruptedException e) {
            System.out.println("Something is wrong");
            System.out.println(e.getMessage());
        }

        for (Thread animal : animalThreads) {
            animal.interrupt();
        }

        log.interrupt();

        System.out.println();
        logger.printCartInfo();
    }
}
