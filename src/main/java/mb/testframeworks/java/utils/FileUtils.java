package mb.testframeworks.java.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    public static String createCompletePath(String filePath) {
        String cwd = System.getProperty("user.dir");
        String[] rootParts = {cwd, "src/main", "resources"};
        String[] splitted = filePath.split("/");
        Path completePath = Paths.get("", rootParts).resolve(Paths.get("", splitted));
        return completePath.toString();
    }

    public static String readFileFromResourceFolder(String filePath) throws IOException {
        String completePath = createCompletePath(filePath);
        Path path = Paths.get(completePath);
        return new String(Files.readAllBytes(path), "UTF-8");
    }
}