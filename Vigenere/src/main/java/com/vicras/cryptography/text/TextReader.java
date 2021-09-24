package com.vicras.cryptography.text;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.nio.file.Paths;

public class TextReader {

    public String readTextAsString(String fileName) throws IOException, URISyntaxException {
        Path filePath = Paths.get("/home/user/Leverx/Temp/Vigenere/src/main/resources/texts", fileName);
        return Files.readString(filePath);
    }
}
