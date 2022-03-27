
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println(Solution.solution(1, 2));
    }

    public class Solution {

        // Side dimension of the chess table
        static final int dim = 8;
        // Whole size of the chess table
        static int size = dim * dim;
        // Table array
        static int[][] table = new int[dim][dim];

        public static int solution(int src, int dest) {

            // If the source is the destination, then return zero
            if (src == dest) {
                return 0;
            }

            // Fill up the table values
            for (int i = 0; i < table.length; i++) {
                for (int j = 0; j < table[i].length; j++) {
                    table[i][j] = i * 8 + j;
                }
            }

            // Create the starting list
            List<Integer> path = new ArrayList<Integer>();
            path.add(src);
            // Call the algorithm to find shortest path
            return minKnightSteps(new ArrayList<Integer>(path), dest, Integer.MAX_VALUE);

        }

        // DFS like recursive implementation for finding the sortest path
        public static int minKnightSteps(List<Integer> path, int dest, int best) {
            // If the path lenght is shorter than the best length so far then continue
            if (path.size() - 1 < best) {
                // Check all the tiles
                for (int i = 0; i < size; i++) {
                    // If the tile is not in the path and is reachable with a single step then check
                    // whether the new tile is the destination, if yes, we are done otherwise
                    // continue the algorithm recursively
                    if (!path.contains(i) && oneStepReacheable(path.get(path.size() - 1), i)) {
                        if (i == dest) {
                            best = path.size();
                            break;
                        } else {
                            List<Integer> pathCopy = new ArrayList<>(path);
                            pathCopy.add(i);
                            best = minKnightSteps(pathCopy, dest, best);
                        }
                    }
                }
            }
            return best;
        }

        // Check whether we can step on the destination tile with the help of the
        // Euclidean distance between the two tiles. In case of a Knight the distance
        // must always be sqrt(5) for a valid step
        public static boolean oneStepReacheable(int start, int dest) {
            int startX = Integer.MAX_VALUE, startY = Integer.MAX_VALUE, destX = Integer.MAX_VALUE,
                    destY = Integer.MAX_VALUE;

            for (int i = 0; i < table.length; i++) {
                for (int j = 0; j < table[i].length; j++) {
                    if (table[i][j] == start) {
                        startX = j;
                        startY = i;
                    }
                    if (table[i][j] == dest) {
                        destX = j;
                        destY = i;
                    }
                }
            }

            return Math.sqrt((startX - destX) * (startX - destX) + (startY - destY) * (startY - destY)) == Math.sqrt(5);
        }
    }
}
