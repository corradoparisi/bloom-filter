package ch.fhnw.dist;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BloomFilter {
    private final boolean[] array;

    private final List<HashFunction> hashFunctions;

    public BloomFilter(int m, int k) {
        array = new int[m];
        hashFunctions = IntStream.range(0, 1).mapToObj(i -> Hashing.murmur3_128(i)).collect(Collectors.toUnmodifiableList());
    }

    public void add(String s) {
        hashFunctions.stream()
                .map(hashFunction -> hashFunction.hashString(s, StandardCharsets.UTF_8).hashCode())
                .forEach(hashCode -> array[hashCode] = true);
    }

    public void contains(String s) {

    }

    static BloomFilter of(int n, double p) {
        //TODO calculate optimal m,k
        return new BloomFilter(42, 42);
    }
}
