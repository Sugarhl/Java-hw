package server;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DirectoryManager {

    private final File mainDirectory;

    public DirectoryManager(String path) {
        mainDirectory = new File(path);
    }

    static boolean pathValidation(String path) {
        File file = new File(path);
        return (file.exists() && file.isDirectory());
    }

    public List<String> getFilesList() {
        List<String> filesList = new ArrayList<>();
        try (DirectoryStream<Path>
                     stream = Files.newDirectoryStream(mainDirectory.toPath())) {
            stream.forEach(x -> filesList.add(x.getFileName().toString()));
        } catch (IOException x) {
            x.printStackTrace();
        }
        return filesList;
    }

    public List<Path> getPathsList() {
        List<Path> pathsList = new ArrayList<>();
        try (DirectoryStream<Path>
                     stream = Files.newDirectoryStream(mainDirectory.toPath())) {
            stream.forEach(pathsList::add);
        } catch (IOException x) {
            x.printStackTrace();
        }
        return pathsList;
    }

}
