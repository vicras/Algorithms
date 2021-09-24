package com.vicras.cryptography.text;

import lombok.NonNull;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;
import static java.util.Locale.ROOT;

/**
 * @author viktar hraskou
 */
public class Alphabet {
    private final Map<Character, Double> charsStatistics;
    private final Map<Character, Integer> char2order;
    private final List<Character> order2char;

    public static Alphabet createFromFile(Path path, String delimiter) throws IOException, NumberFormatException {
        Function<String, Character> parseKey = (text) -> text.trim().split(delimiter)[0].toUpperCase(ROOT).charAt(0);
        Function<String, Double> parseValue = (text) -> Double.valueOf(text.trim().split(delimiter)[1]);
        return new Alphabet(lines(path).collect(Collectors.toMap(parseKey, parseValue)));
    }

    public Alphabet(Map<Character, Double> charsStatistics) {
        this.charsStatistics = charsStatistics;
        this.order2char = charsStatistics.keySet().stream()
                .sorted()
                .collect(Collectors.toList());
        this.char2order = getCharacterToItNumberMap(order2char);
    }

    public static Alphabet createFromFile(@NonNull URL resource, String delimiter) throws IOException {
        return createFromFile(Path.of(resource.getPath()), delimiter);
    }

    private Map<Character, Integer> getCharacterToItNumberMap(List<Character> alphabetCharacters) {
        return IntStream.range(0, alphabetCharacters.size()).boxed()
                .collect(Collectors.toMap(alphabetCharacters::get, Function.identity()));
    }

    public int getOrderByChar(Character character) {
        return char2order.get(character);
    }

    public int getOrderByChar(int character) {
        return char2order.get((char) character);
    }

    public char getCharByOrder(int order) {
        return order2char.get(order);
    }

    public double getCharStatistic(Character character) {
        return charsStatistics.get(character);
    }

    public int size() {
        return order2char.size();
    }

    public Stream<Map.Entry<Character, Double>> statisticStream() {
        return charsStatistics.entrySet().stream();
    }

    public String integerRepresentationIntoText(Stream<Integer> stream) {
        return stream
                .map(this::getCharByOrder)
                .collect(Collector.of(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString));
    }
}
