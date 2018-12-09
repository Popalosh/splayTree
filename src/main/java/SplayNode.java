public class SplayNode <T extends Comparable<T>> implements Comparable <SplayNode<T>>{
    private SplayNode left, right, parent;
    T element;

    public SplayNode() {
        new SplayNode(null, null, null, null);
    }

    public SplayNode(T element) {
        new SplayNode(element, null, null, null);
    }

    public SplayNode(T element, SplayNode left, SplayNode right, SplayNode parent) {
        this.left = left;
        this.right = right;
        this.parent = parent;
        this.element = element;
    }

    public int compareTo(SplayNode<T> anotherNode) {
        return this.element.compareTo(anotherNode.element);
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

    public Object getElement() {
        return element;
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
