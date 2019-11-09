import java.util.List;
import java.util.Objects;

public class Node implements Comparable<Node> {
    private final String symbol;
    private final Integer frequency;
    private Node leftChild;
    private Node rightChild;

    protected String code;

    void buildCode(String code) {
        this.code = code;
        if (leftChild != null) leftChild.buildCode(code + "0");
        if (rightChild != null) rightChild.buildCode(code + "1");
    }

    StringBuffer visitNode(Node node, List<Integer> codeBit, int index, StringBuffer text) {
        if (index >= codeBit.size()) return text;
        if (node.leftChild == null && node.rightChild == null) {
            text.append(node.symbol);
            return text;
        }
        if (index < codeBit.size() && node.leftChild != null && codeBit.get(index) == 0) {
            visitNode(node.leftChild, codeBit, ++index, text);
        }
        if (index < codeBit.size() && node.rightChild != null && codeBit.get(index) == 1) {
            visitNode(node.rightChild, codeBit, ++index, text);
        }
        return text;
    }

    public Node(String symbol, Integer frequency, Node leftChild, Node rightChild) {
        this.symbol = symbol;
        this.frequency = frequency;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public String getSymbol() {
        return symbol;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(symbol, node.symbol) &&
                Objects.equals(frequency, node.frequency) &&
                Objects.equals(leftChild, node.leftChild) &&
                Objects.equals(rightChild, node.rightChild);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, frequency, leftChild, rightChild);
    }

    @Override
    public String toString() {
        return "Node{" +
                "symbol='" + symbol + '\'' +
                ", frequency=" + frequency +
                ", leftChild=" + leftChild +
                ", rightChild=" + rightChild +
                ", code='" + code + '\'' +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        return frequency - o.frequency;
    }
}