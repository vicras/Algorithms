package com.vicras.cryptography;


import com.vicras.cryptography.encryptor.VigenereBreaker;
import com.vicras.cryptography.encryptor.VigenereEncryptor;
import com.vicras.cryptography.text.Alphabet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReportGenerator {
    public final Alphabet alphabet;
    PrintStream out;


        public ReportGenerator() throws IOException {
        alphabet = Alphabet.createFromFile(getClass().getClassLoader().getResource("english_chars_statistics.txt"), " ");

        out = new PrintStream(new FileOutputStream(new File("report.txt")));
    }


    public static void main(String[] args) throws IOException, URISyntaxException {
        new ReportGenerator().test();
    }

    private void test() throws IOException, URISyntaxException {
        int numberOfTexts = 10;
        List<Integer> lengths = List.of(1000, 5000, 10000);
        List<String> keys = List.of("FR", "DLC", "JCBF", "XNAHD", "HJKERL", "VMEWVYR", "JFLDSAJGD", "ASHFDKDSAHFS");

        out.println("Text length dependence test");
        for (var length : lengths) {
            doTest(keys.get(0), length, numberOfTexts);
        }

        out.println("\n\nKey length dependence test");
        for (var key : keys) {
            doTest(key, 1000, numberOfTexts);
        }

    }

    private void doTest(String key, Integer length, int textNumbers) throws IOException, URISyntaxException {
        var vigenereEncryptor = new VigenereEncryptor(alphabet);
        var vigenereBreaker = new VigenereBreaker();

        int success = 0;

        out.print("Length | Success | Key size | Key\n");
        out.print("___________________________________________\n");

        for (int i = 0; i < textNumbers; i++) {

            var text = prepareTextToEncryption(getText(length, i));
            String encrypted = vigenereEncryptor.encryptText(text, key);
            String broken = vigenereBreaker.tryBreakCipher(encrypted, alphabet);

            if (broken.equals(text)) success++;

            out.printf("%6d | %7s | %8d | %12s|\n", length, broken.equals(text), key.length(), key);
        }

        out.print("___________________________________________\n");
        out.printf("Text length: %d%nKey length: %d%nSuccess: %d%nTotal: %d%n%n", length,key.length(), success, textNumbers);

    }

    private String prepareTextToEncryption(String text) {
        return text.replaceAll("[^a-zA-Z]", "").toUpperCase();
    }

    private String getText(Integer length, int i) throws IOException {
        var path = getClass().getClassLoader().getResource(String.format("texts/%dtext%d.txt", length, i + 1)).getPath();
        return Files.readString(Path.of(path));
    }
}
