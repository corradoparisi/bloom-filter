package ch.fhnw.dist;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class BloomFilterMain {
    public static void main(String[] args) throws IOException {
        List<String> words = Files.readAllLines(Paths.get("data/words.txt"));
    }
}
