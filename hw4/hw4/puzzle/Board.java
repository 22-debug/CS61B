package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    private int[][] titles;
    private int size;
    private int hamming = -1; //未计算
    private int manhattan = -1;
    private boolean USING_MANHATTAN = true;

    public Board(int[][] tiles) {
        if (tiles == null || tiles[0] == null || tiles.length != tiles[0].length) {
            throw new IllegalArgumentException();
        }
        this.size = tiles.length;
        this.titles = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.titles[i][j] = tiles[i][j];
            }
        }
    }

    public int tileAt(int i, int j) {
        if (i < 0 || i >= size || j < 0 || j >= size) {
            throw new IndexOutOfBoundsException("invalid position");
        }
        return titles[i][j];
    }

    public int size() {
        return size;
    }

    /**
     * Returns the neighbors of the current board
     * @source: https://joshh.ug/neighbors.html
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1; //blank i
        int zug = -1; //blank j
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) { //neighbor of blank
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug]; //restore
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        if (hamming == -1) {
            hamming = computeHamming();
        }
        return hamming;
    }
    private int computeHamming() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == size - 1 && j == size - 1) { //遍历每个位置，判断该位置上是不是对应的数字，跳过最后一个位置
                    continue;
                }
                count += (tileAt(i, j) == i * size + j + 1) ? 0 : 1;
            }
        }
        return count;
    }

    public int manhattan() {
        if (manhattan == -1) {
            manhattan = computeManhattan();
        }
        return manhattan;
    }
    private int computeManhattan() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tileAt(i, j) == 0) {
                    continue;
                }
                count += findGoal(tileAt(i, j), i, j);
            }
        }
        return count;
    }
    private int findGoal(int number, int i, int j) {
        int goalX = (number - 1) / size;
        int goalY = (number - 1) % size;
        return Math.abs(goalX - i) + Math.abs(goalY - j);
    }

    @Override
    public int estimatedDistanceToGoal() {
        if (USING_MANHATTAN) {
            return manhattan();
        } else {
            return hamming();
        }
    }

    @Override
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null || this.getClass() != y.getClass()) {
            return false;
        }
        Board o = (Board) y;
        if (this.size != o.size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (this.tileAt(i, j) != o.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return manhattan();
    }
    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
