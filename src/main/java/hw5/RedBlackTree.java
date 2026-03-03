package hw5;

import java.util.ArrayDeque;
import java.util.Deque;

public class RedBlackTree {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    static final class Node {
        int key;
        Node left, right, parent;
        boolean color = RED; // новый узел всегда красный

        Node(int key, Node parent) {
            this.key = key;
            this.parent = parent;
        }
    }

    private Node root;

    public Node root() {
        return root;
    }

    public void insert(int key) {
        if (root == null) {
            root = new Node(key, null);
            root.color = BLACK; // корень всегда черный
            return;
        }

        Node p = root;
        Node parent = null;

        // обычная вставка в BST
        while (p != null) {
            parent = p;
            if (key < p.key) {
                p = p.left;
            } else if (key > p.key) {
                p = p.right;
            } else {
                // дубликаты не вставляем
                return;
            }
        }

        Node x = new Node(key, parent);
        if (key < parent.key) parent.left = x;
        else parent.right = x;

        fixAfterInsertion(x);
    }

    private void fixAfterInsertion(Node x) {
        // пока нарушено правило "нет двух красных подряд"
        while (x != null && x != root && colorOf(parentOf(x)) == RED) {
            Node p = parentOf(x);
            Node g = parentOf(p);

            if (p == leftOf(g)) {
                Node u = rightOf(g); // uncle
                if (colorOf(u) == RED) {
                    // Case 1: дядя красный -> перекраска
                    setColor(p, BLACK);
                    setColor(u, BLACK);
                    setColor(g, RED);
                    x = g;
                } else {
                    // Case 2/3: дядя черный -> повороты
                    if (x == rightOf(p)) {
                        // Case 2: "ломаная" -> малый поворот
                        x = p;
                        rotateLeft(x);
                        p = parentOf(x);
                        g = parentOf(p);
                    }
                    // Case 3: "линейная" -> большой поворот
                    setColor(p, BLACK);
                    setColor(g, RED);
                    rotateRight(g);
                }
            } else {
                // симметрично
                Node u = leftOf(g);
                if (colorOf(u) == RED) {
                    setColor(p, BLACK);
                    setColor(u, BLACK);
                    setColor(g, RED);
                    x = g;
                } else {
                    if (x == leftOf(p)) {
                        x = p;
                        rotateRight(x);
                        p = parentOf(x);
                        g = parentOf(p);
                    }
                    setColor(p, BLACK);
                    setColor(g, RED);
                    rotateLeft(g);
                }
            }
        }

        root.color = BLACK;
    }

    private void rotateLeft(Node x) {
        if (x == null) return;
        Node r = x.right;
        if (r == null) return;

        x.right = r.left;
        if (r.left != null) r.left.parent = x;

        r.parent = x.parent;
        if (x.parent == null) {
            root = r;
        } else if (x == x.parent.left) {
            x.parent.left = r;
        } else {
            x.parent.right = r;
        }

        r.left = x;
        x.parent = r;
    }

    private void rotateRight(Node x) {
        if (x == null) return;
        Node l = x.left;
        if (l == null) return;

        x.left = l.right;
        if (l.right != null) l.right.parent = x;

        l.parent = x.parent;
        if (x.parent == null) {
            root = l;
        } else if (x == x.parent.right) {
            x.parent.right = l;
        } else {
            x.parent.left = l;
        }

        l.right = x;
        x.parent = l;
    }

    // Валидация инвариантов

    public void validateRbInvariants() {
        if (root == null) return;

        // 1) корень черный
        if (root.color != BLACK) {
            throw new IllegalStateException("RB violation: root is not BLACK");
        }

        // 2) BST-свойство
        validateBst(root, null, null);

        // 3) no red-red + 4) одинаковая black-height
        validateNoRedRed(root);
        int bh = blackHeight(root);
        if (bh <= 0) {
            throw new IllegalStateException("RB violation: black-height invalid");
        }
    }

    private void validateBst(Node n, Integer minExclusive, Integer maxExclusive) {
        if (n == null) return;

        if (minExclusive != null && n.key <= minExclusive) {
            throw new IllegalStateException("BST violation at key=" + n.key);
        }
        if (maxExclusive != null && n.key >= maxExclusive) {
            throw new IllegalStateException("BST violation at key=" + n.key);
        }

        validateBst(n.left, minExclusive, n.key);
        validateBst(n.right, n.key, maxExclusive);
    }

    private void validateNoRedRed(Node root) {
        Deque<Node> st = new ArrayDeque<>();
        st.push(root);

        while (!st.isEmpty()) {
            Node n = st.pop();
            if (n.color == RED) {
                if (colorOf(n.left) == RED || colorOf(n.right) == RED) {
                    throw new IllegalStateException("RB violation: red node has red child at key=" + n.key);
                }
            }
            if (n.left != null) st.push(n.left);
            if (n.right != null) st.push(n.right);
        }
    }

    // возвращает black-height, либо бросает исключение при расхождении
    private int blackHeight(Node n) {
        if (n == null) return 1; // null-лист считаем черным

        int left = blackHeight(n.left);
        int right = blackHeight(n.right);

        if (left != right) {
            throw new IllegalStateException("RB violation: black-height mismatch at key=" + n.key
                    + " left=" + left + " right=" + right);
        }
        return left + (n.color == BLACK ? 1 : 0);
    }

    // ===== helpers =====
    private static Node parentOf(Node n) { return n == null ? null : n.parent; }
    private static Node leftOf(Node n) { return n == null ? null : n.left; }
    private static Node rightOf(Node n) { return n == null ? null : n.right; }
    private static boolean colorOf(Node n) { return n == null ? BLACK : n.color; }
    private static void setColor(Node n, boolean c) { if (n != null) n.color = c; }
}
