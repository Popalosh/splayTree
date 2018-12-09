public class SplayTree{
    private SplayNode root;
    private int size;

    public SplayTree() {
        this.root = null;
        this.size = 0;
    }

    public SplayTree(SplayTree tree) {
        this.size = tree.size;
        this.root = tree.root;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void clear() {
        if (!this.isEmpty()) {
            this.root = null;
            this.size = 0;
        }
    }

    public void add(int element) {
        SplayNode node = this.root;
        SplayNode parent = null;

        while (node != null) {
            parent = root;

            if (/*  переданный элемент больше */) {
                node = parent.getRight();
            } else node = parent.getLeft();
        }

        node = new SplayNode(element, null, null, parent);

        if (parent == null) {
            root = node;
        } else if (/* переданный элемент больше */) {
            parent.setRight(node);
        } else parent.setLeft(node);

        this.size++;
    }

}
