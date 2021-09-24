package com.vicras.cryptography.encryptor;


import com.vicras.cryptography.analyzer.FrequencyAnalyzer;
import com.vicras.cryptography.analyzer.KasiskiAnalyzer;
import com.vicras.cryptography.text.Alphabet;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class VigenereBreaker {

    public String tryBreakCipher(String encrypted, Alphabet alphabet) {
        KasiskiAnalyzer kasiskiAnalyzer = new KasiskiAnalyzer();
        Integer keywordLength = kasiskiAnalyzer.findKeywordLength(encrypted, 5);

        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer(alphabet);
        int[] offsets = findOffsets(encrypted, keywordLength, frequencyAnalyzer);


        return alphabet.integerRepresentationIntoText(IntStream.range(0, encrypted.length())
                .map(i -> {
                    int newIndex = alphabet.getOrderByChar(encrypted.charAt(i)) - offsets[i % keywordLength];
                    return newIndex >= 0 ? newIndex : newIndex + alphabet.size();
                }).boxed());

    }

    private int[] findOffsets(String encrypted, Integer keywordLength, FrequencyAnalyzer frequencyAnalyzer) {
        int[] offsets = new int[keywordLength];
        ArrayList<Character> characters = new ArrayList<>();

        for (int i = 0; i < keywordLength; i++) {
            int j = i;
            for (; j < encrypted.length(); j += keywordLength) {
                characters.add(encrypted.charAt(j));
            }
            offsets[i] = frequencyAnalyzer.analyzeKey(characters, frequencyAnalyzer.compareWithMostFrequent);
            characters.clear();
        }
        return offsets;
    }
}
