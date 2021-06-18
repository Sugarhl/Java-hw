package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiServer {

    static ExecutorService executeIt = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        int port = 3345;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        try (ServerSocket server = new ServerSocket(port);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Server socket created, command console reader for listen to server commands");

            System.out.println("Enter path to data directory: ");

            Scanner scanner = new Scanner(System.in);

            String path = scanner.nextLine();
            while (!DirectoryManager.pathValidation(path)) {
                System.out.println("Incorrect directory path. Try again");
                path = scanner.nextLine();
            }
            DirectoryManager directory = new DirectoryManager(path);

            System.out.println("Waiting for connection.");

            while (!server.isClosed()) {

                if (isEndOfLife(server, br)) break;

                Socket client = server.accept();

                executeIt.execute(new ClientHandler(client, directory));
                System.out.println("Connection accepted.");
            }

            executeIt.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isEndOfLife(ServerSocket server, BufferedReader br) throws IOException {
        if (br.ready()) {
            System.out.println("Main Server found any messages in channel, let's look at them.");

            String serverCommand = br.readLine();
            if (serverCommand.equalsIgnoreCase("quit") ||
                    !DirectoryManager.pathValidation(serverCommand)) {
                System.out.println("Main Server initiate exiting...");
                server.close();
                return true;
            }
        }
        return false;
    }
}
