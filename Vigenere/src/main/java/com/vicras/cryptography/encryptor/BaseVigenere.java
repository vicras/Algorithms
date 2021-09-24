package com.vicras.cryptography.encryptor;

import com.vicras.cryptography.text.Alphabet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author viktar hraskou
 */
public abstract class BaseVigenere {

    protected final Alphabet alphabet;

    protected BaseVigenere(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    protected String convertText(String text, String key) {

        List<Integer> textCodes = mapToSymbolsCode(alphabet, text);
        List<Integer> keyCodes = mapToSymbolsCode(alphabet, key);

        var encoded = makeShift(alphabet, textCodes, keyCodes);
        return alphabet.integerRepresentationIntoText(encoded.stream());
    }

    private List<Integer> mapToSymbolsCode(Alphabet alphabet, String text) {
        return text.chars()
                .mapToObj(alphabet::getOrderByChar)
                .collect(Collectors.toList());
    }

    private List<Integer> makeShift(Alphabet alphabet, List<Integer> textCodes, List<Integer> keyCodes) {
        var ans = new ArrayList<Integer>();
        for (int i = 0; i < textCodes.size(); i++) {
            ans.add(symbolConverter(textCodes, keyCodes, i) % alphabet.size());
        }
        return ans;
    }

    abstract int symbolConverter(List<Integer> textCodes, List<Integer> keyCodes, int i);

    protected Integer calculateCaesarKey(List<Integer> keyCodes, int i) {
        return keyCodes.get(i % keyCodes.size());
    }
}
