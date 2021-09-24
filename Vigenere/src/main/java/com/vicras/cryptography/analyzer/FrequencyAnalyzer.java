package com.vicras.cryptography.analyzer;

import com.vicras.cryptography.text.Alphabet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Collections.frequency;
import static java.util.Comparator.comparing;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class FrequencyAnalyzer {

    public final Alphabet alphabet;

    public final Function<Map<Character, Double>,CharsStatistics> compareWithAll = this::compareAll;
    public final Function<Map<Character, Double>,CharsStatistics> compareWithMostFrequent = this::compareMostFrequent;

    public FrequencyAnalyzer(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    public Integer analyzeKey(List<Character> chars, Function<Map<Character, Double>,CharsStatistics> compareFunction) {
        Map<Character, Double> charsOccurrencesPercents = countCharsOccurrencesPercents(chars);

        CharsStatistics charsStatistics= compareFunction.apply(charsOccurrencesPercents);

        return findShift(charsStatistics);
    }

    private Map<Character, Double> countCharsOccurrencesPercents(@NotNull List<Character> chars) {
        return chars.stream()
                .distinct()
                .collect(toMap(
                        identity(),
                        c -> ((double) frequency(chars, c) / chars.size())
                ));
    }

    // find two chars (one from alphabet, one from text) with minimal frequency occurrences distance
    private CharsStatistics compareAll(Map<Character, Double> charsOccurrencesPercents) {
        return alphabet.statisticStream()
                .flatMap(alphStat -> compareWithAllCharsFromText(charsOccurrencesPercents, alphStat))
                .min(comparing(CharsStatistics::getFreqDifferenceAbs))
                .orElseThrow();
    }

    private Stream<CharsStatistics> compareWithAllCharsFromText(
            @NotNull Map<Character, Double> charsOccurrencesPercents,
            Map.Entry<Character, Double> alphStat) {
        return charsOccurrencesPercents.entrySet().stream()
                .map(textStat -> CharsStatistics.of(alphStat, textStat));
    }

    private @NotNull CharsStatistics compareMostFrequent(@NotNull Map<Character, Double> charsOccurrencesPercents) {
        Map.Entry<Character, Double> mostFrequentInText = findMostFrequent(charsOccurrencesPercents.entrySet().stream());
        Map.Entry<Character, Double> mostFrequentInAlphabet = findMostFrequent(alphabet.statisticStream());

        return CharsStatistics.of(
                mostFrequentInAlphabet,
                mostFrequentInText
        );
    }

    private Map.Entry<Character, Double> findMostFrequent(@NotNull Stream<Map.Entry<Character, Double>> charsOccurrencesPercentsEntry) {
        return charsOccurrencesPercentsEntry
                .max(Map.Entry.comparingByValue())
                .orElseThrow();
    }

    private int findShift(@NotNull CharsStatistics charsStatistics) {
        int diff = alphabet.getOrderByChar(charsStatistics.textChar) -
                alphabet.getOrderByChar(charsStatistics.alphabetChar);
        return diff >= 0 ? diff : diff + alphabet.size();
    }

    @Getter
    @AllArgsConstructor
    public static class CharsStatistics {
        private final Character alphabetChar;
        private final Character textChar;
        private final Double freqDifferenceAbs;

        @NotNull
        @Contract("_, _ -> new")
        static CharsStatistics of(Map.Entry<Character, Double> alphStat,
                                  Map.Entry<Character, Double> textStat
        ) {
            return new CharsStatistics(
                    alphStat.getKey(),
                    textStat.getKey(),
                    Math.abs(alphStat.getValue() - textStat.getValue())
            );
        }
    }
}
