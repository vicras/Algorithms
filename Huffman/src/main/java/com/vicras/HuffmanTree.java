package com.vicras;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Getter
public class HuffmanTree {

    public HuffmanNode tree = null;
    public Map<HuffmanNode, String> nodeAndCode = new HashMap<>();

    public HuffmanTree(PriorityQueue<HuffmanNode> nodes) {
        assert nodes.size() > 2;

        generateTree(nodes);

        codeMap(tree, "");

    }

    private void generateTree(PriorityQueue<HuffmanNode> nodes) {
        while (nodes.size() > 1) {
            HuffmanNode x = nodes.poll();
            HuffmanNode y = nodes.poll();

            HuffmanNode newNode = HuffmanNode.of(null, x.getProbability() + y.getProbability());

            newNode.setLeft(x);
            newNode.setRight(y);

            tree = newNode;

            nodes.add(newNode);
        }
    }

    private void codeMap(HuffmanNode root, String s) {
        if (
                isNull(root.getLeft())
                && isNull(root.getRight())
                && nonNull(root.getSymbol())
        ) {
            nodeAndCode.put(root, s);
            return;
        }

        codeMap(root.getLeft(), s + "0");
        codeMap(root.getRight(), s + "1");
    }

    @Override
    public String toString() {
        return nodeAndCode.entrySet().stream()
                .map(Objects::toString)
                .collect(Collectors.joining("\n"));
    }
}
