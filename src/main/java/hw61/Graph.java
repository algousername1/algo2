package hw61;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    public static List<Edge> transformMatrixToEdgeList(int[][] matrix) {
        List<Edge> edges = new ArrayList<>();

        int n = matrix.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0) {
                    edges.add(new Edge(i, j, matrix[i][j]));
                }
            }
        }

        return edges;
    }
}
