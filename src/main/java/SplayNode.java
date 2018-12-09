public class SplayNode implements Comparable {
    private SplayNode left, right, parent;
    private Object element;

    public SplayNode() {
        new SplayNode(0, null, null, null);
    }

    public SplayNode(int element) {
        new SplayNode(element, null, null, null);
    }

    public SplayNode(Object element, SplayNode left, SplayNode right, SplayNode parent) {
        this.left = left;
        this.right = right;
        this.parent = parent;
        this.element = element;
    }

    public int compareTo(Object anotherNode) {
        SplayNode node = (SplayNode) anotherNode;
        if (this.element.equals(node.element)) {
            return 0;
        }

        String className = element.getClass().getSimpleName();

        if (className.equals("int")) {
            return ((Integer) element).compareTo((Integer) node.element);
        }
        else if (className.equals("String")) {

            String el = (String) element;
            String anotherEl = (String) node.element;
            
            if (el.length() > anotherEl.length()) {
                return 1;
            }
            if (el.length() < anotherEl.length()) {
                return -1;
            }
        }

        return 0;
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
