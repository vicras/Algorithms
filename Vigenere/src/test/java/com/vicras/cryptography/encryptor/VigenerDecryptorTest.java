package com.vicras.cryptography.encryptor;

import com.vicras.cryptography.text.Alphabet;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author viktar hraskou
 */
class VigenerDecryptorTest {

    public final Alphabet alphabet;

    public VigenerDecryptorTest() throws IOException {
        alphabet = Alphabet.createFromFile(getClass().getClassLoader().getResource("english_chars_statistics.txt"), " ");
    }

    @Test
    public void testDescriptor() throws IOException, URISyntaxException {
        int numberOfTexts = 10;
        List<Integer> lengths = List.of(1000, 5000, 10000);
        List<String> keys = List.of("FR", "DLC", "JCBF", "XNAHD", "HJKERL", "VMEWVYR", "JFLDSAJGD", "ASHFDKDSAHFS");
        for(var key: keys){
            for (var length : lengths){
                doTest(key, length, numberOfTexts);
            }
        }
    }

    private void doTest(String key, Integer length, int textNumbers) throws IOException, URISyntaxException {
        var vigenereEncryptor = new VigenereEncryptor(alphabet);
        var vigenerDecryptor = new VigenereDecryptor(alphabet);

        for (int i = 0; i < textNumbers; i++) {

            var text = prepareTextToEncryption(getText(length, i));
            String encrypted = vigenereEncryptor.encryptText(text, key);
            String broken = vigenerDecryptor.decryptText(encrypted, key);

            assertEquals(text, broken);
        }
    }

    private String prepareTextToEncryption(String text) {
        return text.replaceAll("[^a-zA-Z]", "").toUpperCase();
    }

    private String getText(Integer length, int i) throws IOException {
        var resource = Path.of(getClass().getClassLoader().getResource("texts/" + String.format("%dtext%d.txt", length, i + 1)).getPath());
        return Files.readString(resource);
    }

}