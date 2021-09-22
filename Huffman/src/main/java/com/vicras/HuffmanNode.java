package com.vicras;

import lombok.Data;

@Data
class HuffmanNode {

    private String symbol;
    private double probability;

    private HuffmanNode left;
    private HuffmanNode right;


    public static HuffmanNode of(String symbol, double probability) {
        var node = new HuffmanNode();
        node.symbol = symbol;
        node.probability = probability;
        node.left = null;
        node.right = null;
        return node;
    }

    @Override
    public String toString() {
        return symbol + " : " + probability;
    }
}
