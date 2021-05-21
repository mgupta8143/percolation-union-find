import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats
{
  private static final double CONFIDENCE_95 = 1.96;
  private final int n;
  private final int trials;
  private double[] trialResults;
  private double mean;
  private double stddev;
  private double confidenceLo;
  private double confidenceHi;
  
  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
    this.n = n;
    this.trials = trials;
    trialResults = new double[trials];
    conductTrials();
  }
  
  private void conductTrials() {
    for (int i = 0; i < trials; ++i) {
      conductSingleTrial(i);
    }
    
    mean = StdStats.mean(trialResults);
    stddev = StdStats.stddev(trialResults);
    confidenceLo = mean - CONFIDENCE_95 * stddev / Math.sqrt(trials); 
    confidenceHi = mean + CONFIDENCE_95 * stddev / Math.sqrt(trials);

  }
  
  private void conductSingleTrial(int i) {
    Percolation grid = new Percolation(n);
    while (!grid.percolates()) {
      int row = StdRandom.uniform(1, n+1);
      int col = StdRandom.uniform(1, n+1);
      grid.open(row, col);
    }
    trialResults[i] = (double) grid.numberOfOpenSites()/(n*n);
    
  }
  
  public double mean() {
    return this.mean;
  }
  
  public double stddev() {
    return this.stddev;
  }
  
  public double confidenceLo() {
    return this.confidenceLo;
  }
  
  public double confidenceHi() {
    return this.confidenceHi;
  }
  
  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int t = Integer.parseInt(args[1]);

    System.out.println(String.format("%-20s= %d", "n", n));
    System.out.println(String.format("%-20s= %d", "T", t));
   
    PercolationStats statsOne = new PercolationStats(n, t);
    System.out.println(String.format("%-20s= %f", "mean", statsOne.mean()));
    System.out.println(String.format("%-20s= %f", "stddev", statsOne.stddev()));
    System.out.println(String.format("%-20s= %f, %f", "95% confidence interval", statsOne.confidenceLo(), statsOne.confidenceHi()));
  }
  
  

}
