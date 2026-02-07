package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver {
    private int minMoves;
    private List<WorldState> solution;
    private MinPQ<SearchNode> pq;

    private class SearchNode implements Comparable<SearchNode> {
        WorldState node;
        int moves;
        SearchNode prev;
        int h;

        SearchNode(WorldState initial) {
            this.node = initial;
            this.prev = null;
            this.moves = 0;
            this.h = this.moves + this.node.estimatedDistanceToGoal();
        }

        SearchNode(WorldState insert, SearchNode previous) {
            this.node = insert;
            this.prev = previous;
            this.moves = prev.moves + 1;
            this.h = this.moves + this.node.estimatedDistanceToGoal();
        }

        @Override
        public int compareTo(SearchNode other) {
            return Integer.compare(h, other.h);
        }
    }

    public Solver(WorldState initial) {
        pq = new MinPQ<>();
        solution = new ArrayList<>();
        SearchNode initialNode = new SearchNode(initial);
        pq.insert(initialNode);
        while(!pq.isEmpty()) {
            SearchNode best = pq.delMin();
            if (best.node.isGoal()) {
                getMovesAndSolution(best);
                return;
            } else {
                for (WorldState neighbor : best.node.neighbors()) {
                    if (best.prev != null && neighbor.equals(best.prev.node)) {
                        continue;
                    }
                    pq.insert(new SearchNode(neighbor, best));
                }
            }
        }
    }
    private void getMovesAndSolution(SearchNode goalNode) {
        minMoves = goalNode.moves;
        SearchNode searchNode = goalNode;
        while (searchNode != null) {
            solution.add(searchNode.node);
            searchNode = searchNode.prev;
        }
        Collections.reverse(solution);
    }

    public int moves() {
        return minMoves;
    }

    public Iterable<WorldState> solution() {
        return solution;
    }
}
