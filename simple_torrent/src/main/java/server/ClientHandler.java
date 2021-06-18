package server;

import util.Util;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class ClientHandler implements Runnable {

    private static Socket clientDialog;
    private static DirectoryManager directory;

    public ClientHandler(Socket client, DirectoryManager dir) {
        ClientHandler.clientDialog = client;
        directory = dir;
    }

    @Override
    public void run() {

        try {
            DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());

            System.out.println("DataInputStream created");
            System.out.println("DataOutputStream  created");


            if (!clientDialog.isClosed()) {
                sendFileList(out);

                while (response(in, out)) {
                    System.out.println(Util.SEPLINE);
                }

            }

            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");

            in.close();
            out.close();
            clientDialog.close();

            System.out.println("Closing connections & channels - DONE.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendFileList(DataOutputStream dout) {
        try {
            var files = directory.getFilesList();

            dout.writeInt(files.size());
            for (String file : files) {
                dout.writeUTF(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean response(DataInputStream din, DataOutputStream dout) throws IOException {
        int index = din.readInt();

        if (index < 0) {
            return false;
        }
        Path path = directory.getPathsList().get(index);
        dout.writeLong(Files.size(path));
        boolean accepted = din.readBoolean();
        if (accepted) {
            sendFile(path, dout);
            System.out.println("File sent");
        }
        return true;
    }

    private static void sendFile(Path file, OutputStream out) {
        try (
                InputStream in = new DataInputStream(new FileInputStream(String.valueOf(file)))
        ) {
            long bytesSent = Util.copy(in, out, Files.size(file));
            System.out.printf("%d bytes sent.%n", bytesSent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(3345)) {
            DirectoryManager directory = new DirectoryManager("data");
            Socket client = server.accept();
            var serv = new ClientHandler(client, directory);
            serv.run();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}