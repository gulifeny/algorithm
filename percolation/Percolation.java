import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {


    // N * N matrix의 행 길이
    private int N;

    // matrix open, blocked
    private boolean[][] percMat;

    // ID Size

    // WeightedQuickUnionUF
    private WeightedQuickUnionUF UF;

    // neighborhood index array
    private int[][] nhIdx = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new java.lang.IllegalArgumentException("out error!");
        }
        this.N = n;

        this.UF = new WeightedQuickUnionUF(N * N + 2);
        this.percMat = new boolean[N][N];

    }

    // open site (row, col) if it is not open already
    // row, col 은 1부터 N까지의 수
    // open할때 union를 한다
    public void open(int row, int col) {
        if (row < 1 || row > N) {
            throw new java.lang.IllegalArgumentException("out error!");
        }
        if (col < 1 || col > N) {
            throw new java.lang.IllegalArgumentException("out error!");
        }

        percMat[row - 1][col - 1] = true;
        if (row == 1) {
            UF.union(0, locToIdx(row, col));
        }
        if (row == N && UF.connected(0, locToIdx(row, col))) {
            UF.union(N * N - 1, locToIdx(row, col));
        }

        int rowIdx;
        int colIdx;

        //System.out.println("open row : " + row + "col : " + col);
        for (int i = 0; i < nhIdx.length; i++) {
            rowIdx = row + nhIdx[i][0];
            colIdx = col + nhIdx[i][1];
            if (rowIdx < 1 || rowIdx > N) {
                continue;
            }
            if (colIdx < 1 || colIdx > N) {
                continue;
            }
            if (isOpen(rowIdx, colIdx)) {
                //System.out.println("row : " + rowIdx + "col : " + colIdx);
                UF.union(locToIdx(row, col), locToIdx(rowIdx, colIdx));
            }
        }
    }


    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > N) {
            throw new java.lang.IllegalArgumentException("out error!");
        }
        if (col < 1 || col > N) {
            throw new java.lang.IllegalArgumentException("out error!");
        }
        return percMat[row - 1][col - 1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > N) {
            throw new java.lang.IllegalArgumentException("out error!");
        }
        if (col < 1 || col > N) {
            throw new java.lang.IllegalArgumentException("out error!");
        }
        int idx = locToIdx(row, col);
        return UF.connected(0, idx);

    }

    // number of open sites
    public int numberOfOpenSites() {
        int re = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!percMat[i][j]) {
                    continue;
                }
                re++;
            }
        }
        return re;
    }

    // does the system pidSize
    public boolean percolates() {
        return UF.connected(0, N * N - 1);
    }

    private int locToIdx(int row, int col) {
        return N * (row - 1) + col;
    }


    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.open(1, 3);
        p.open(2, 3);
        p.open(3, 3);
        p.open(3, 1);
        // 1 3
        // 2 3
        // 3 3
        // 3 1
        // 2 1
        // 1 1


        System.out.println(p.isOpen(1, 3));
        System.out.println(p.isOpen(2, 3));
        System.out.println(p.isFull(3, 1));
        System.out.println("good boy");
    }

}
