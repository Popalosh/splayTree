public class SplayTree extends SplayNode {
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

    public <T> SplayNode getNode (T element) {
        return findNode(element);
    }

    public <T> boolean remove(T element) {
        return remove(findNode(element));
    }

    public <T> boolean contanins(T element) {
        return findNode(element) != null;
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

    public <T> boolean add(T element) {
        SplayNode node = this.root;
        SplayNode parent = null;
        SplayNode abstractNode = new SplayNode((Comparable) element, null, null, null);

        while (node != null) {
            parent = root;

            if (node.compareTo(abstractNode) < 0) {
                node = parent.getRight();
            } else node = parent.getLeft();
        }

        node = new SplayNode((Comparable) element, null, null, parent);

        if (parent == null) {
            root = node;
        } else if (node.compareTo(abstractNode) < 0) {
            parent.setRight(node);
        } else parent.setLeft(node);

        this.size++;
        splay(node);
        return true;
    }

    private <T> SplayNode findNode(T element) {
        SplayNode previousNode = null;
        SplayNode currentNode = root;
        SplayNode abstractNode = new SplayNode((Comparable) element, null, null, null);

        while (currentNode != null) {
            previousNode = currentNode;
            if (abstractNode.compareTo(currentNode) > 0) {
                currentNode = currentNode.getRight();
            } else if (abstractNode.compareTo(currentNode) < 0) {
                currentNode = currentNode.getLeft();
            } else if (abstractNode.compareTo(currentNode) == 0) {
                splay(currentNode);
                return currentNode;
            }
        }

        if (previousNode != null) {
            splay(previousNode);
            return null;
        }
        return null;
    }

    private boolean remove(SplayNode node) {
        if (node == null) {
            return false;
        }

        splay(node);

        if (node.getLeft() != null && node.getRight() != null) {
            SplayNode minimum = node.getLeft();

            while (minimum.getRight() != null) {
                minimum = minimum.getRight();
            }

            minimum.setRight(node.getRight());
            node.getRight().setParent(minimum);
            node.getLeft().setParent(null);
            root = node.getLeft();

        } else if (node.getRight() != null) {
            node.getRight().setParent(null);
            root = node.getRight();

        } else if (node.getLeft() != null) {
            node.getLeft().setParent(null);
            {
                root = node.getLeft();
            }

        } else {
            root = null;
        }

        node.setParent(null);
        node.setLeft(null);
        node.setParent(null);
        node = null;
        size--;
        return true;
    }

    private void rightChildToParent(SplayNode child, SplayNode parent) {
        if (parent.getParent() != null) {
            if (parent == parent.getParent().getLeft()) {
                parent.getParent().setLeft(child);

            } else parent.getParent().setRight(child);
        }

        if (child.getLeft() != null) {
            child.getLeft().setParent(parent);
        }

        child.setParent(parent.getParent());
        parent.setParent(child);
        parent.setRight(child.getLeft());
        child.setLeft(parent);
    }

    private void leftChildToParent(SplayNode child, SplayNode parent) {
        if (parent.getParent() != null) {
            if (parent == parent.getParent().getLeft()) {
                parent.getParent().setLeft(child);

            } else parent.getParent().setRight(child);
        }

        if (child.getRight() != null) {
            child.getRight().setParent(parent);
        }

        child.setParent(parent.getParent());
        parent.setParent(child);
        parent.setLeft(child.getRight());
        child.setRight(parent);
    }

    private void splay(SplayNode node) {
        while (node.getParent() != null) {
            SplayNode parent = node.getParent();
            SplayNode grandParent = parent.getParent();

            if (grandParent != null) {
                if (node == parent.getLeft()) {
                    leftChildToParent(node, parent);

                } else rightChildToParent(node, parent);

            } else {
                if (node == parent.getLeft()) {
                    if (parent == grandParent.getLeft()) {
                        leftChildToParent(parent, grandParent);
                        leftChildToParent(node, parent);

                    } else {
                        leftChildToParent(node, node.getParent());
                        rightChildToParent(node, node.getParent());
                    }

                } else {
                    if (parent == grandParent.getLeft()) {
                        rightChildToParent(node, node.getParent());
                        leftChildToParent(node, node.getParent());

                    } else {
                        rightChildToParent(parent, grandParent);
                        rightChildToParent(node, parent);
                    }
                }
            }
        }
        root = node;
    }
}
