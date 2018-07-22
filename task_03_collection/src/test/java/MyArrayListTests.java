import mylist.MyArrayList;
import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

public class MyArrayListTests {
    @Test
    public void collectionsAddAll() {
        List<String> list = new MyArrayList<>();
        Collections.addAll(list, "a", "b", "c");
        assert list.contains("a");
        assert list.contains("b");
        assert list.contains("c");
        assert !list.contains("d");
    }

    @Test
    public void collectionsCopy() {
        List<String> list = new MyArrayList<>();
        Collections.addAll(list, "a", "b", "c", "d");
        List<String> copy = new MyArrayList<>(4);
        Collections.copy(copy, list);

        assert copy.contains("a");
        assert copy.contains("b");
        assert copy.contains("c");
        assert !copy.contains("e");
    }

    @Test
    public void collectionsSort() {
        List<Integer> list = new MyArrayList<>();
        Collections.addAll(list, 1, 6, 22, 31, 8, 129);
        Object[] naturalOrder = {1, 6, 8, 22, 31, 129};
        Object[] reverseOrder = {129, 31, 22, 8, 6, 1};

        Collections.sort(list, Comparator.reverseOrder());
        assertArrayEquals(reverseOrder, list.toArray());

        Collections.sort(list, Comparator.naturalOrder());
        assertArrayEquals(naturalOrder, list.toArray());
    }
}
