package ch.fhnw.dist;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

public class BloomFilterMain {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("data/words.txt");
        var p = 0.01;

        var words = new HashSet<>(Files.readAllLines(path, StandardCharsets.UTF_8));
        BloomFilter bloomFilter = BloomFilter.of(words.size(), p);
        words.forEach(bloomFilter::add);
        //TODO generate random words with apache word generatro like this range 1000 not contained in words contains
    }
}
