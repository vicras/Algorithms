package com.vicras.cryptography.encryptor;

import com.vicras.cryptography.text.Alphabet;

import java.util.List;

public class VigenereEncryptor extends BaseVigenere {


    public VigenereEncryptor(Alphabet alphabet) {
        super(alphabet);
    }

    public String encryptText(String text, String key) {
        return convertText(text, key);
    }

    @Override
    int symbolConverter(List<Integer> textCodes, List<Integer> keyCodes, int i) {
        return textCodes.get(i) + calculateCaesarKey(keyCodes, i);
    }
}