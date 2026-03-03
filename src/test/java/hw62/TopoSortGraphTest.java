package hw62;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopoSortGraphTest {

    @Test
    void testSimpleDAG() {
        int[][] matrix = {
                {0, 1, 1, 0},
                {0, 0, 0, 1},
                {0, 0, 0, 1},
                {0, 0, 0, 0}
        };

        List<Integer> order = TopoSortGraph.topologicalSort(matrix);

        assertTrue(order.indexOf(0) < order.indexOf(1));
        assertTrue(order.indexOf(0) < order.indexOf(2));
        assertTrue(order.indexOf(1) < order.indexOf(3));
        assertTrue(order.indexOf(2) < order.indexOf(3));
    }

    @Test
    void testSingleVertex() {
        int[][] matrix = {{0}};
        assertEquals(List.of(0), TopoSortGraph.topologicalSort(matrix));
    }

    @Test
    void testIndependentVertices() {
        int[][] matrix = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };

        List<Integer> order = TopoSortGraph.topologicalSort(matrix);

        assertEquals(3, order.size());
        assertTrue(order.containsAll(List.of(0, 1, 2)));
    }

    @Test
    void testCycle() {
        int[][] matrix = {
                {0, 1},
                {1, 0}
        };

        assertThrows(IllegalStateException.class,
                () -> TopoSortGraph.topologicalSort(matrix));
    }
}
