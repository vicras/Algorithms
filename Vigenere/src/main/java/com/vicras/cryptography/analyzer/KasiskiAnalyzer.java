package com.vicras.cryptography.analyzer;

import org.apache.commons.collections4.ListUtils;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class KasiskiAnalyzer {

    public Integer findKeywordLength(String text, Integer length) {
        HashMap<String, List<Integer>> subStringsMap = findDuplicatedSubstrings(text, length);

        Optional<BigInteger> gcd = subStringsMap.values().stream()
                .flatMap(distanceBetweenSubstrings()
                )
                .reduce(getOldIfMutuallyPrimeNumbersFunc());
        return gcd.orElse(BigInteger.ONE).intValue();
    }

    @NotNull
    private BinaryOperator<BigInteger> getOldIfMutuallyPrimeNumbersFunc() {
        return (r, n) -> {
            BigInteger newVal = r.gcd(n);
            if (newVal.equals(BigInteger.ONE)) {
                return r;
            } else {
                return newVal;
            }
        };
    }

    @NotNull
    private Function<List<Integer>, Stream<? extends BigInteger>> distanceBetweenSubstrings() {
        return indexList -> IntStream.range(0, indexList.size() - 1)
                .mapToObj(i -> BigInteger.valueOf(indexList.get(i + 1) - indexList.get(i)));
    }

    private @NotNull HashMap<String, List<Integer>> findDuplicatedSubstrings(@NotNull String text, Integer length) {
        HashMap<String, List<Integer>> subStrings = new HashMap<>();

        AtomicBoolean foundSubStr = new AtomicBoolean(false);
        int subStrLength = length;
        int j = subStrLength;
        int i = 0;

        while (j <= text.length()) {
            String sub = text.substring(i, j);

            subStrings.merge(sub, List.of(i), (oldVal, newVal) -> updateValue(foundSubStr, oldVal, newVal));

            if (j == text.length() && foundSubStr.get()) {
                foundSubStr.set(false);
                i = 0;
                subStrLength += 1;
                j = subStrLength;
            } else {
                i++;
                j++;
            }
        }
        return subStrings;
    }

    @NotNull
    private List<Integer> updateValue(AtomicBoolean foundSubStr, List<Integer> oldVal, List<Integer> newVal) {
        foundSubStr.set(true);
        return ListUtils.union(oldVal, newVal);
    }
}
