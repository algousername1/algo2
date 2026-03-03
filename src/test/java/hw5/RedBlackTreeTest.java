package hw5;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class RedBlackTreeTest {
    @Test
    void insertionsShouldRotateAndFixDoubleRedTest() {
        RedBlackTree tree = new RedBlackTree();

        tree.insert(10);
        tree.validateRbInvariants();

        tree.insert(7);
        tree.validateRbInvariants();

        tree.insert(5);
        tree.validateRbInvariants();

        // Классический результат для RB-вставки: корень 7 (BLACK), дети 5 и 10 (RED)
        RedBlackTree.Node root = tree.root();
        assertNotNull(root);
        assertEquals(7, root.key);
        assertFalse(root.color, "root must be BLACK");

        assertNotNull(root.left);
        assertEquals(5, root.left.key);
        assertTrue(root.left.color, "left child should be RED");

        assertNotNull(root.right);
        assertEquals(10, root.right.key);
        assertTrue(root.right.color, "right child should be RED");
    }

    @Test
    void manyInsertsShouldKeepRbInvariantsAndSortedOrderTest() {
        RedBlackTree tree = new RedBlackTree();

        List<Integer> values = new ArrayList<>();
        for (int i = 1; i <= 10_000; i++) values.add(i);

        // перемешаем, чтобы нагрузить балансировку
        Collections.shuffle(values, new Random(42));

        Set<Integer> inserted = new HashSet<>();
        for (int v : values) {
            tree.insert(v);
            inserted.add(v);

            // главное: после каждой вставки дерево корректно
            tree.validateRbInvariants();
        }

        // Доп.проверка: inorder должен дать отсортированный набор
        List<Integer> inorder = inorder(tree.root());
        List<Integer> sorted = new ArrayList<>(inserted);
        Collections.sort(sorted);

        assertEquals(sorted, inorder);
    }

    private static List<Integer> inorder(RedBlackTree.Node root) {
        List<Integer> res = new ArrayList<>();
        Deque<RedBlackTree.Node> st = new ArrayDeque<>();
        RedBlackTree.Node cur = root;

        while (cur != null || !st.isEmpty()) {
            while (cur != null) {
                st.push(cur);
                cur = cur.left;
            }
            cur = st.pop();
            res.add(cur.key);
            cur = cur.right;
        }
        return res;
    }
}
