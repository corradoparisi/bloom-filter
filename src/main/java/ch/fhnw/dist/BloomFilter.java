package ch.fhnw.dist;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BloomFilter {
    private final boolean[] array;
    private final List<HashFunction> hashFunctions;

    public BloomFilter(int m, int k) {
        array = new boolean[m];
        hashFunctions = IntStream.range(0, k).mapToObj(Hashing::murmur3_128).collect(Collectors.toUnmodifiableList());
    }

    static BloomFilter of(int n, double p) {
        //TODO calculate optimal m,k
        return new BloomFilter(42, 3);
    }

    public void add(String s) {
        stream(s).forEach(hashCode -> array[hashCode] = true);
    }


    public boolean contains(String s) {
        boolean match = stream(s).allMatch(hashCode -> array[hashCode]);
        return match;
    }

    private Stream<Integer> stream(String s) {
        return hashFunctions.stream()
                .map(hashFunction -> hashFunction.hashString(s, StandardCharsets.UTF_8).hashCode());
    }
}
