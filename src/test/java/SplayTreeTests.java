import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SplayTreeTests {

    private static final Random random = new Random();
    private static final int SIZE = 10;

    private Set<Integer> generateValues() {
        return IntStream.range(0, SIZE).mapToObj(i -> random.nextInt()).collect(Collectors.toSet());
    }

    private Integer differentValue(Set<Integer> values) {
        Integer value = random.nextInt();
        while (values.contains(value)) {
            value = random.nextInt();
        }
        return value;
    }

    private SplayTree<Integer> generateSplayTree(Set<Integer> values) {
        SplayTree<Integer> splayTree = new SplayTree<>();
        splayTree.addAll(values);
        return splayTree;
    }

    @Test
    void addTest() { //проверяем корректность добавления элементов
        Set<Integer> values = generateValues();
        SplayTree<Integer> splayTree = generateSplayTree(values);

        assertEquals(splayTree.size(), values.size());
    }

    @Test
    void removeTest() {
        Set<Integer> values = generateValues();
        SplayTree<Integer> splayTree = generateSplayTree(values);

        values.forEach(splayTree::remove);
        values.forEach(value -> assertFalse(splayTree.contains(value)));

    }

    @Test
    void containsTest() {
        Set<Integer> values = generateValues();
        SplayTree<Integer> splayTree = generateSplayTree(values);

        values.forEach(value -> assertTrue(splayTree.contains(value)));
    }

    @Test
    void iteratorTest() {
        Set<Integer> values = generateValues();
        SplayTree<Integer> splayTree = generateSplayTree(values);

        splayTree.forEach(value -> {
            assertTrue(values.contains(value));
            values.remove(value);
        });
    }

    @Test
    void minMaxElementTest() {
        Set<Integer> values = generateValues();
        SplayTree<Integer> splayTree = generateSplayTree(values);

        assertEquals(splayTree.first(), values.stream().min(Integer::compareTo).get());
        assertEquals(splayTree.last(), values.stream().max(Integer::compareTo).get());
    }

}
