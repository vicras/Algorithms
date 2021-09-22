package com.vicras;

import java.util.List;
import java.util.PriorityQueue;

import static java.lang.Math.log;
import static java.util.Comparator.comparingDouble;


public class Huffman {

    public HuffmanTree generateTree(List<Pair> words) {
        var nodeQueue = new PriorityQueue<>(words.size(), comparingDouble(HuffmanNode::getProbability));
        words.forEach(pair -> nodeQueue.add(HuffmanNode.of(pair.symbol, pair.probability)));
        return new HuffmanTree(nodeQueue);
    }

    public double countEntropy(List<Pair> words) {
        return words.stream()
                .mapToDouble(Pair::getProbability)
                .map(count -> count * log(count) / log(2))
                .sum();
    }

    public double countAverageWordSize(HuffmanTree tree) {
        return tree.nodeAndCode.entrySet().stream()
                .mapToDouble((set) -> set.getKey().getProbability() * set.getValue().length())
                .sum();
    }
}
