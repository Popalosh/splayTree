import java.util.*;

public class SplayTree<V extends Comparable<V>> implements SortedSet<V> {

    private int size;
    private SplayNode<V> root;

    public SplayTree() {
        this.root = null;
        setSize(0);
    }

    public SplayTree(SplayTree splayTreeree) {
        setSize(splayTreeree.size);
        this.root = splayTreeree.root;
    }


    @Override
    public int size() {
        return this.size;
    }

    private void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean isEmpty() {
        return getRoot() == null;
    }

    @Override
    public boolean contains(Object o) {
        if (this.root == null) return false;
        V value = (V) o;
        this.root = splay(this.root, value);
        return this.root.getValue().equals(value);
    }

    @Override
    public Iterator<V> iterator() {
        return new SubIterator<>(this);
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        for (V value : this) {
            array[i] = value;
            i++;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) this.toArray();
    }

    @Override
    public boolean add(V value) {
        if (this.root == null) {
            this.root = new SplayNode<>(value);
            setSize(1);
            return true;
        }

        root = splay(root, value);

        int comparation = value.compareTo(root.getValue());

        SplayNode<V> splayNode = new SplayNode<>(value);
        if (comparation < 0) {
            splayNode.setLeft(root.getLeft());
            splayNode.setRight(root);
            root.setLeft(null);
            root = splayNode;
            this.size++;
            return true;
        } else if (comparation > 0) {
            splayNode.setRight(root.getRight());
            splayNode.setLeft(root);
            root.setRight(null);
            root = splayNode;
            size++;
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        if (root == null) return false;

        V value = (V) o;
        root = splay(root, (V) o);

        if (value.equals(root.getValue())) {
            if (root.getLeft() == null) {
                root = root.getRight();
            } else {
                SplayNode<V> x = root.getRight();
                root = root.getLeft();
                splay(root, value);
                root.setRight(x);
            }
            size--;
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return c.stream().allMatch(this::contains);
    }

    @Override
    public boolean addAll(Collection<? extends V> c) {
        return c.stream().allMatch(this::add);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.stream().filter(v -> !c.contains(v)).allMatch(this::remove);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return c.stream().allMatch(this::remove);
    }

    @Override
    public void clear() {
        this.forEach(this::remove);
    }

    private SplayNode<V> splay(SplayNode<V> splayNode, V value) {
        if (splayNode == null) return null;

        if (value.compareTo(splayNode.getValue()) < 0) {
            if (splayNode.getLeft() == null) {
                return splayNode;
            }
            if (value.compareTo((V) splayNode.getLeft().getValue()) < 0) {
                splayNode.getLeft().setLeft(splay(splayNode.getLeft().getLeft(), value));
                splayNode = rotateRight(splayNode);
            } else if (value.compareTo((V) splayNode.getLeft().getValue()) > 0) {
                splayNode.getLeft().setRight(splay(splayNode.getLeft().getRight(), value));
                if (splayNode.getLeft().getRight() != null)
                    splayNode.setLeft(rotateLeft(splayNode.getLeft()));
            }

            return splayNode.getLeft() == null ? splayNode : rotateRight(splayNode);
        } else if (value.compareTo(splayNode.getValue()) > 0) {
            if (splayNode.getRight() == null) {
                return splayNode;
            }

            if (value.compareTo((V) splayNode.getRight().getValue()) < 0) {
                splayNode.getRight().setLeft(splay(splayNode.getRight().getLeft(), value));
                if (splayNode.getRight().getLeft() != null)
                    splayNode.setRight( rotateRight(splayNode.getRight()));
            } else if (value.compareTo((V) splayNode.getRight().getValue()) > 0) {
                splayNode.getRight().setRight(splay(splayNode.getRight().getRight(), value));
                splayNode = rotateLeft(splayNode);
            }

            return splayNode.getRight() == null ? splayNode : rotateLeft(splayNode);
        } else return splayNode;
    }

    private SplayNode<V> rotateRight(SplayNode<V> node) {
        SplayNode<V> newNode = node.getLeft();
        node.setLeft(newNode.getRight());
        newNode.setRight(node);
        return newNode;
    }

    private SplayNode<V> rotateLeft(SplayNode<V> node) {
        SplayNode<V> newNode = node.getRight();
        node.setRight(newNode.getLeft());
        newNode.setLeft(node);
        return newNode;
    }

    public SplayNode<V> getRoot() {
        return this.root;
    }

    @Override
    public Comparator<? super V> comparator() {
        return new SubComparator<>();
    }

    @Override
    public SortedSet<V> subSet(V fromElement, V toElement) {
        return subSet(findNode(this.root, fromElement), toElement, new SplayTree<>());
    }

    private SortedSet<V> subSet(SplayNode<V> splayNode, V to, SortedSet<V> sortedSet) {
        if (splayNode == null) return sortedSet;
        sortedSet.add(splayNode.getValue());

        if (splayNode.getValue().equals(to)) return sortedSet;
        if (splayNode.getLeft() != null) return subSet(splayNode.getLeft(), to, sortedSet);
        if (splayNode.getRight() != null) return subSet(splayNode.getRight(), to, sortedSet);
        return sortedSet;
    }

    @Override
    public SortedSet<V> headSet(V toElement) {
        return subSet(this.root.getValue(), toElement);
    }

    @Override
    public SortedSet<V> tailSet(V fromElement) {
        return subSet(fromElement, last());
    }

    @Override
    public V first() {
        return this.stream().min(Objects.requireNonNull(comparator())).get();
    }

    @Override
    public V last() {
        return this.stream().max(Objects.requireNonNull(comparator())).get();
    }

    private SplayNode<V> findNode(SplayNode<V> splayNode, V value) {
        if (splayNode.getValue().equals(value)) {
            return splayNode;
        }
        if (splayNode.getLeft() != null) return findNode(splayNode.getLeft(), value);
        if (splayNode.getRight() != null) return findNode(splayNode.getRight(), value);
        return null;
    }

    @Override
    public String toString() {
        return new ArrayList<>(this).toString();
    }

    public class SubComparator<V extends Comparable<V>> implements Comparator<V> {
        public int compare(V a, V b) {
            return a.compareTo(b);
        }
    }

    public class SubIterator<V extends Comparable<V>> implements Iterator<V> {

        private SplayTree splayTree;
        private Stack<SplayNode<V>> stack;

        public SubIterator(SplayTree splayTree) {

            this.splayTree = splayTree;

            stack = new Stack<>();
            SplayNode<V> root = this.splayTree.getRoot();
            while (root != null) {
                stack.push(root);
                root = root.getLeft();
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public V next() {
            if (!hasNext()) throw new NoSuchElementException();

            SplayNode<V> smallest = stack.pop();
            if (smallest.getRight() != null) {
                SplayNode<V> splayNode = smallest.getRight();
                while (splayNode != null) {
                    stack.push(splayNode);
                    splayNode = splayNode.getLeft();
                }
            }
            return smallest.getValue();
        }

        @Override
        public void remove() {
            splayTree.remove(next());
        }
    }
}
