public class SplayNode<V> {

    private SplayNode<V> left, right, parent;

    private V value;

    public SplayNode(V value) {
        this.value = value;
        this.left = null;
        this.right = null;
        this.parent = null;
    }

    public SplayNode getLeft() {
        return left;
    }

    public SplayNode getRight() {
        return right;
    }

    public SplayNode getParent() {
        return parent;
    }

    public V getValue() {
        return value;
    }

    public void setLeft(SplayNode left) {
        this.left = left;
    }

    public void setRight(SplayNode right) {
        this.right = right;
    }

    public void setParent(SplayNode parent) {
        this.parent = parent;
    }
}
