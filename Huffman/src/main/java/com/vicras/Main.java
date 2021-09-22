package com.vicras;

import java.util.List;

public class Main {

    static List<Pair> input = List.of(
            new Pair("u1", 0.05),
            new Pair("u2", 0.1),
            new Pair("u3", 0.15),
            new Pair("u4", 0.2),
            new Pair("u5", 0.23),
            new Pair("u6", 0.27)
    );

    public static void main(String[] args) {
        Huffman huffman = new Huffman();

        HuffmanTree huffmanTree = huffman.generateTree(input);
        double entropy = huffman.countEntropy(input);
        double averageWordSize = huffman.countAverageWordSize(huffmanTree);

        System.out.println("Huffman code = \n" + huffmanTree);
        System.out.println("Entropy = " + entropy);
        System.out.println("Average word size = " + averageWordSize);

    }
}
