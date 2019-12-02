package ch.fhnw.dist;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.*;

public class BloomFilter {
    private final boolean[] array;
    private final List<HashFunction> hashFunctions;

    /**
     * @param filterSize = m
     * @param numberOfHashFunctions = k
     */
    public BloomFilter(int filterSize, int numberOfHashFunctions) {
        array = new boolean[filterSize];
        hashFunctions = IntStream.range(0, numberOfHashFunctions).mapToObj(Hashing::murmur3_128).collect(Collectors.toUnmodifiableList());
    }

    /**
     * numberOfElements = n
     * probabilityOfError = p
     * filterSize = m
     * numberOfHashFunctions = k
     *
     * @see <a href="https://en.wikipedia.org/wiki/Bloom_filter">Bloom Filter</a>
     */
    static BloomFilter of(double numberOfElements, double probabilityOfError) {
        int filterSize = filterSize(numberOfElements, probabilityOfError);
        int numberOfHashFunctions = numberOfHashFunctions(numberOfElements, filterSize);
        return new BloomFilter(filterSize, numberOfHashFunctions);
    }

    static int numberOfHashFunctions(double numberOfElements, int filterSize) {
        return (int) round((filterSize / numberOfElements) * log(2));
    }

    static int filterSize(double numberOfElements, double probabilityOfError) {
        return (int) ceil((numberOfElements * log(probabilityOfError)) / log(1 / pow(2, log(2))));
    }

    public void add(String s) {
        stream(s).forEach(hashCode -> array[hashCode] = true);
    }


    public boolean contains(String s) {
        return stream(s).allMatch(hashCode -> array[hashCode]);
    }

    private Stream<Integer> stream(String s) {
        return hashFunctions.stream()
                .map(hashFunction -> abs(hashFunction.hashString(s, StandardCharsets.UTF_8).hashCode()) % array.length);
    }
}
