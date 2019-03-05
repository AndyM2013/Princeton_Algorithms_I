
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int T; // independent trails
    private double[] fraction;

    // perform trails independent experiments on an n-by-n grid
    public PercolationStats(int n, int trails){

        if (n <= 0 || trails <= 0){
            throw new IllegalArgumentException("n and trails must be greater than 0");
        }
        T = trails;
        fraction = new double[T];

        for (int count = 0; count < T; count++){
            Percolation p = new Percolation(n);
            int openSite = 0;
            while (!p.percolates()){
                int i = StdRandom.uniform(1, n+1);
                int j = StdRandom.uniform(1, n+1);
                if(!p.isOpen(i, j)){
                    p.open(i,j);
                    openSite++;
                }
            }
            fraction[count] = (double) openSite / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(fraction);
    }

    // sample standard deviation of percolation threshold
    public  double stddev(){
        return StdStats.stddev(fraction);
    }

    //low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    //high endpoint of 95% confidence interal
    public double confidenceHi(){
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

    //test client
    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, T);
        System.out.println("mean                   = " + ps.mean());
        System.out.println("stddev                 = " + ps.stddev());
        System.out.println("95% confidence interal = " + ps.confidenceLo() + ", " + ps.confidenceHi());

    }
}
