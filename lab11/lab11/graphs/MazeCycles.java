package lab11.graphs;

import edu.princeton.cs.algs4.Stack;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    //private int[] parent; //记录父节点且不更改edge（不会画图）
    private int[] parentOf;      // 记录每个节点的父节点
    private boolean cycleFound;  // 是否找到环
    private int cycleStart;      // 环的起始节点


    public MazeCycles(Maze m) {
        super(m);
        /*
        parent = new int[maze.V()];
        for (int i = 0; i < maze.V(); i++) {
            parent[i] = -1;
        }
        */
        parentOf = new int[maze.V()];
        for (int i = 0; i < maze.V(); i++) {
            parentOf[i] = -1;  // 初始化为-1，表示没有父节点
        }
        cycleFound = false;
        cycleStart = -1;
    }

    @Override
    public void solve() {
        //dfsHelper(0);
        // 从起点开始DFS
        dfs(0);

        // 如果找到环，标记环的路径
        if (cycleFound) {
            markCycle();
        }
    }

    private void dfs(int v) {
        marked[v] = true;
        announce();

        // 遍历所有邻居
        for (int w : maze.adj(v)) {
            // 如果已经找到环，直接返回
            if (cycleFound) {
                return;
            }

            if (!marked[w]) {
                parentOf[w] = v;
                dfs(w);
            } else if (w != parentOf[v]) {
                cycleFound = true;
                cycleStart = w;  // 环的起始节点
                parentOf[w] = v; // 为标记环临时设置父节点
                return;
            }
        }
    }

    // 标记环的路径
    private void markCycle() {
        if (!cycleFound || cycleStart == -1) {
            return;
        }

        // 从环的起始节点开始，沿着父节点回溯标记环
        int current = cycleStart;
        int next = parentOf[current];

        while (next != -1 && next != cycleStart) {
            //edgeTo[next] = current;
            edgeTo[current] = next;

            current = next;
            next = parentOf[current];

            announce();
        }

        // 闭合环：连接最后一个节点回起始节点
        //edgeTo[cycleStart] = current;
        edgeTo[current] = cycleStart;
        announce();
    }

    // Helper methods go here
    /*
    private void dfsHelper(int start) {
        Stack<Integer> dfsStack = new Stack<>();
        Stack<Integer> loopStack = new Stack<>(); //辅助构建循环路径

        dfsStack.push(start);
        int curr;

        while(!dfsStack.isEmpty()) {
            curr = dfsStack.pop();
            marked[curr] = true;
            loopStack.push(curr);
            announce();

            for (int neighbor : maze.adj(curr)) {
                if (!marked[neighbor]) {
                    dfsStack.push(neighbor);
                    parent[neighbor] = curr;
                } else if (parent[curr] != neighbor) {
                    findCyle(loopStack, neighbor);
                    return;
                }
            }
        }
    }

    private void findCyle(Stack<Integer> loopStack, int duplicate) {
        int head = loopStack.peek();
        edgeTo[head] = duplicate;
        edgeTo[duplicate] = head; //双向连接用于可视化
        announce();

        while (!loopStack.isEmpty() && loopStack.peek() != duplicate) {
            int out = loopStack.pop();
            if (!loopStack.isEmpty()) {
                edgeTo[out] = loopStack.peek();
                announce();
            }
        }
    }

     */
}

