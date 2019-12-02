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
        var errorProbability = 0.01; // p
        var testSimulations = 10_000;
        var words = new HashSet<>(Files.readAllLines(path, StandardCharsets.UTF_8));
        var filterSize = BloomFilter.filterSize(words.size(), errorProbability);
        var numberOfHashFunctions = BloomFilter.numberOfHashFunctions(words.size(), filterSize);

        BloomFilter bloomFilter = new BloomFilter(words.size(), numberOfHashFunctions);
        words.forEach(bloomFilter::add); // fill bloom filter

        printResults(errorProbability, testSimulations, words, filterSize, numberOfHashFunctions, bloomFilter);
    }

    private static void printResults(double errorProbability, int testSimulations, HashSet<String> words, int filterSize, int numberOfHashFunctions, BloomFilter bloomFilter) {
        var falsePositivesCounter = simulation(words, bloomFilter, testSimulations); // run simulation

        System.out.println("Given words: " + words.size());
        System.out.println("Given error probability: " + errorProbability);
        System.out.println("Given number of simulations: " + testSimulations);
        System.out.println("Computed Bloom Filter size: " + filterSize);
        System.out.println("Computed number Of hash functions: " + numberOfHashFunctions);
        System.out.println("Computed number of false positives: " + falsePositivesCounter);
        System.out.println("Computed simulation precision is: " + (((double) falsePositivesCounter) / ((double) testSimulations)));
    }

    private static int simulation(HashSet<String> words, BloomFilter bloomFilter, int testSimulations) {
        int falsePositivesCounter = 0;
        for (int i = 0; i < testSimulations; i++) {
            var wordUnderTest = "";
            do {
                wordUnderTest = RandomWordUtil.randomWord(20);
            } while (words.contains(wordUnderTest));

            // wordUnderTest is surely not in the set of words
            // consequently if the bloom filter's contains method
            // returns true, it is a false positive
            boolean isFalsePositive = bloomFilter.contains(wordUnderTest);
            if (isFalsePositive) {
                falsePositivesCounter++;
            }

        }
        return falsePositivesCounter;
    }
}
