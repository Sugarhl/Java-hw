package util;

import client.Client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    // private static ServerSocket server;

    public static void main(String[] args) throws  InterruptedException {

        // запустим пул нитей в которых колличество возможных нитей ограничено -
        // 10-ю.
        ExecutorService exec = Executors.newFixedThreadPool(5);
        int j = 0;

        // стартуем цикл в котором с паузой в 10 милисекунд стартуем Runnable
        // клиентов,
        // которые пишут какое-то количество сообщений
        while (j < 5) {
            exec.execute(new Client());
            j++;
            Thread.sleep(10);
        }

        // закрываем фабрику
        exec.shutdown();
    }
}