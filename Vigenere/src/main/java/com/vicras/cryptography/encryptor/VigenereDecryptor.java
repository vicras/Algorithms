package com.vicras.cryptography.encryptor;

import com.vicras.cryptography.text.Alphabet;

import java.util.List;

/**
 * @author viktar hraskou
 */
public class VigenereDecryptor extends BaseVigenere {
    public VigenereDecryptor(Alphabet alphabet) {
        super(alphabet);
    }

    public String decryptText(String text, String key) {
        return convertText(text, key);
    }

    @Override
    int symbolConverter(List<Integer> textCodes, List<Integer> keyCodes, int i) {
        return textCodes.get(i) - calculateCaesarKey(keyCodes, i) + alphabet.size();
    }
}
