package lab11.graphs;

import java.util.ArrayDeque;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        ArrayDeque<Integer> bfsQueue = new ArrayDeque<>();
        bfsQueue.addLast(s);

        int curr;
        while (!bfsQueue.isEmpty()) {
            curr = bfsQueue.removeFirst();
            marked[curr] = true;

            for (int neighbor : maze.adj(curr)) {
                if (!marked[neighbor]) {
                    edgeTo[neighbor] = curr;
                    distTo[neighbor] = distTo[neighbor] + 1;
                    announce();
                    bfsQueue.addLast(neighbor);
                    if (targetFound) {
                        return;
                    }
                }
            }
            if (curr == t) {
                targetFound = true;
            }
            if (targetFound) {
                return;
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

