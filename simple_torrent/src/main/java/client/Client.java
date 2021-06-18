package client;

import server.DirectoryManager;
import util.Util;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static util.Util.SEPLINE;

public class Client implements Runnable {

    static Socket socket;
    static DirectoryManager dir = new DirectoryManager("data");

    List<String> filesToDownload = null;
    List<String> downloads = new ArrayList<>();


    int index;

    public Client(String host, int port) {
        try {
            socket = new Socket(host, port);
            System.out.println("Client connected to socket");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Client() {
        try {
            socket = new Socket("localhost", 3345);
            System.out.println("Client connected to socket");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try (
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());
        ) {
            System.out.println("Client streams initialized");

            filesToDownload = receiveFilesList(in);

            var fileIndex = requestForFile(out);
            while (fileIndex > -1) {
                var fileSize = acceptDownload(in, out);
                if (fileSize > -1) {
                    var path = getFileName(fileIndex);
                    receiveFile(path, in, fileSize);
                    System.out.println("File received");
                    System.out.println(SEPLINE);
                }
                fileIndex = requestForFile(out);
            }
            out.writeInt(-1);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private int requestForFile(DataOutputStream dout) throws IOException {
        Scanner console = new Scanner(System.in);

        PrintFiles(downloads, "You already download in this session:");
        PrintFiles(filesToDownload, "Files to download:");

        System.out.print("Enter number of file to download: ");

        int index = console.nextInt();

        while (index < 0 || index > filesToDownload.size()) {
            if (index == -1) {
                return index;
            }
            System.out.println("Incorrect number. Enter -1 to exit.");
            index = console.nextInt();
        }

        downloads.add(filesToDownload.get(index));
        dout.writeInt(index);
        return index;
    }


    private Path getFileName(int index) throws IOException {
        Scanner console = new Scanner(System.in);

        System.out.print("Enter file name(without file extension): ");
        var name = console.nextLine();
        var file = filesToDownload.get(index);
        var ext = file.substring(file.indexOf('.'));
        name += ext;
        while (Files.exists(Paths.get(name))) {
            System.out.println("File with this name already exists. Enter another name.");
            name = console.nextLine() + ext;
        }
        System.out.println(SEPLINE);
        return Paths.get(name);
    }


    private long acceptDownload(DataInputStream din, DataOutputStream dout) throws IOException {
        Scanner console = new Scanner(System.in);
        var fileSize = din.readLong();
        System.out.printf("File size: %s\n", Util.getFileSize(fileSize));
        System.out.println("Do you want to download it? (Yes/No)");

        var answer = console.nextLine();
        if (answer.equalsIgnoreCase("Yes") || answer.equalsIgnoreCase("y")) {
            dout.writeBoolean(true);
            return fileSize;
        }
        dout.writeBoolean(false);
        downloads.remove(downloads.size() - 1);
        return -1;
    }

    private void PrintFiles(List<String> files, String preview) {
        System.out.println(preview);
        if (files.isEmpty()) {
            System.out.println("*empty*");
        }
        for (int i = 0; i < files.size(); ++i) {
            System.out.printf("%d: %s\n", i, files.get(i));
        }
        System.out.println(Util.SEPLINE);
    }


    private static List<String> receiveFilesList(DataInputStream din) {
        List<String> fileNames = new ArrayList<>();
        try {
            int size = din.readInt();
            for (int i = 0; i < size; i++) {
                fileNames.add(din.readUTF());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileNames;
    }

    private static void receiveFile(Path file, InputStream in, long size) throws IOException {
        try (
                OutputStream out = new DataOutputStream(new FileOutputStream(String.valueOf(file)))
        ) {
            System.out.println("Connected to " + socket.getRemoteSocketAddress());
            long bytesCopied = Util.copy(in, out, size);
            System.out.printf("%d bytes received%n", bytesCopied);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client;
        if (args.length == 0) {
            client = new Client();
        } else {
            int port = Integer.parseInt(args[1]);
            client = new Client(args[0], port);
        }
        client.run();
    }
}