
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int gridSize;                       // size of grid
    private boolean[] openSite;                 // a boolean array for whether a site is open or close
    private WeightedQuickUnionUF wquf;          // weighted Quick Union Union-find object



    // create n-by-n grid, with all sites blocked
    public Percolation(int n){
        if (n <= 0){
            throw new IllegalArgumentException("n must be greater than 1");
        }
        gridSize = n;
        wquf = new WeightedQuickUnionUF(n * n + 2);
        openSite = new boolean[n * n + 2];       // 2 extra sites for virtual top and bottom
        for (int i = 0; i <= n * n; i++){
            openSite[i] = false;                 // all sites are blocked
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (IndicesNotValid(row, col)){
            throw new IndexOutOfBoundsException("Index is not in between 1 and n");
        }
        int index = getIndexOfGrid(row, col);
        if (!openSite[index]){
            openSite[index] = true;              // if the grid is blocked, turn it open
        }

        if (row == 1){
            wquf.union(index, 0);                // connect to virtual top
        }
        if (row == gridSize) {
            wquf.union(index, (gridSize * gridSize + 1));     // connect to virtual bottom
        }
    }

    // check if indices are in between 0 and 1
    private boolean IndicesNotValid(int i, int j){
        return !(i >= 1 && i <= gridSize && j >= 1 && j <= gridSize);
    }

    /*
        Covert 2D array to 1D
        the length of 1D array is n since our grid is n * n,
        the index for each element in the 2D array will be: int index = (row * n) + col
        In this problem, we have two virtual site top and bottom.
        virtual top site is 0, and virtual bottom site is n * n + 1
     */
    private int getIndexOfGrid(int i, int j){
        if (IndicesNotValid(i, j)){
            throw new IndexOutOfBoundsException("Index is not in between 1 and n");
        }
        return (i - 1) * gridSize + j;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col){
        if (IndicesNotValid(row, col)){
            throw new IndexOutOfBoundsException("Index is not in between 1 and n");
        }
        int index = getIndexOfGrid(row, col);
        return openSite[index];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (IndicesNotValid(row, col)){
            throw new IndexOutOfBoundsException("Index is not in between 1 and n");
        }
        int index = getIndexOfGrid(row, col);
        return wquf.connected(index, 0);
    }


    // does the system percolate?
    public boolean percolates(){
        return wquf.connected((gridSize * gridSize + 1), 0);
    }

    // test client (optional)
    public static void main(String[] args){

    }
}
