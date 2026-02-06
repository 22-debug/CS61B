package lab11.graphs;

import java.util.Comparator;
import edu.princeton.cs.algs4.MinPQ;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(t)) + Math.abs(maze.toY(v) - maze.toY(t));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    private class Node {
        int vertex;
        int f;  // f = g + h

        Node(int vertex, int g) {
            this.vertex = vertex;
            this.f = g + h(vertex);
        }
    }
    private class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node n1, Node n2) {
            return n1.f - n2.f;
        }
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        MinPQ<Node> pq = new MinPQ<>(new NodeComparator());
        boolean[] visited = new boolean[maze.V()];
        int[] gScore = new int[maze.V()];  // 实际代价

        // 初始化
        for (int i = 0; i < maze.V(); i++) {
            gScore[i] = Integer.MAX_VALUE;
        }
        gScore[s] = 0;

        pq.insert(new Node(s, 0));

        while (!pq.isEmpty()) {
            Node current = pq.delMin(); //获取并删除最小值
            int curr = current.vertex;

            if (visited[curr]) {
                continue;
            }

            visited[curr] = true;
            marked[curr] = true;
            announce();

            if (curr == t) {
                return;  // 找到目标
            }

            for (int next : maze.adj(curr)) {
                if (!visited[next]) {
                    // 计算新的g值
                    int tentativeG = gScore[curr] + 1; //1为路长

                    if (tentativeG < gScore[next]) {
                        // 找到更好的路径
                        gScore[next] = tentativeG;
                        distTo[next] = tentativeG;
                        edgeTo[next] = curr;

                        // 插入新节点（或更新）
                        pq.insert(new Node(next, tentativeG));

                        announce();
                    }
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

