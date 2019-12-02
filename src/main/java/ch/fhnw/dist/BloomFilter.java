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

    public BloomFilter(int m, int k) {
        array = new boolean[m];
        hashFunctions = IntStream.range(0, k).mapToObj(Hashing::murmur3_128).collect(Collectors.toUnmodifiableList());
    }

    static BloomFilter of(double n, double p) {
        var m = (int) ceil((n * log(p)) / log(1 / pow(2, log(2))));
        var k = (int) round((m / n) * log(2));
        System.out.println("m:" + m);
        System.out.println("k:" + k);
        return new BloomFilter(m, k);
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
                .map(hashFunction -> abs(hashFunction.hashString(s, StandardCharsets.UTF_8).hashCode()) % array.length);
    }
}
