import java.io.*;
import java.util.*;

public class HuffmanCompress {
    protected static void CompressionText(String pathToFile) throws Exception {
        String string = readingDataTextFromInputFile(pathToFile);
        Map<String, Integer> symbolFrequency = buildSymbolFrequencyTable(string);
        Map<String, Node> symbolHuffmanTree = buildHuffmanTree(symbolFrequency);
        StringBuffer encodedString = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            String symbol = "" + string.charAt(i);
            encodedString.append(symbolHuffmanTree.get(symbol).code);
        }

        File out = new File(pathToFile);
        String nameFile = out.getName();

        try (FileOutputStream encodingText = new FileOutputStream("./src/files/" + nameFile + ".cmp")) {

            byte[] buffer = encodedString.toString().getBytes();

            encodingText.write(buffer, 0, buffer.length);
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }

        System.out.println("Encoding text :");
        System.out.println(encodedString.toString());
        System.out.println("Length of encoded string = " + encodedString.length());
    }

    protected static Map<String, Node> buildHuffmanTree(Map<String, Integer> symbolFrequency) {
        Map<String, Node> symbolHuffman = new HashMap<>();
        Queue<Node> nodeQueue = new PriorityQueue<>();
        for (Map.Entry<String, Integer> entry : symbolFrequency.entrySet()) {
            Node node = new Node(entry.getKey(), entry.getValue(), null, null);
            symbolHuffman.put(entry.getKey(), node);
            nodeQueue.add(node);
        }

        while (nodeQueue.size() > 1) {
            Node leftNode = nodeQueue.poll();
            Node rightNode = nodeQueue.poll();

            nodeQueue.offer(new Node(leftNode.getSymbol() + rightNode.getSymbol(),
                    leftNode.getFrequency() + rightNode.getFrequency(),
                    leftNode,
                    rightNode));
        }

        Node root = nodeQueue.poll();
        if (symbolFrequency.size() == 1) {
            root.code = "0";
        } else {
            root.buildCode("");
        }

        return symbolHuffman;
    }

    protected static Map<String, Integer> buildSymbolFrequencyTable(String string) {
        Map<String, Integer> symbolFrequency = new HashMap<>();
        String symbol;
        int frequency;
        for (int i = 0; i < string.length(); i++) {
            symbol = "" + string.charAt(i);
            if (symbolFrequency.containsKey(symbol)) {
                frequency = symbolFrequency.get(symbol) + 1;
                symbolFrequency.put(symbol, frequency);
            } else {
                symbolFrequency.put(symbol, 1);
            }
        }

        System.out.println("symbol : frequency");
        for (Map.Entry<String, Integer> entry : symbolFrequency.entrySet()) {
            System.out.println("   " + entry.getKey() + "   :     " + entry.getValue());
        }
        System.out.println();

        return symbolFrequency;
    }

    protected static String readingDataTextFromInputFile(String pathToFile) throws FileNotFoundException {
        FileReader input = new FileReader(pathToFile);
        Scanner scanner = new Scanner(input);
        String string = scanner.nextLine();
        System.out.println("Input data text :");
        System.out.println(string);
        System.out.println("Length of input string = " + string.length());
        System.out.println();

        return string;
    }
}
