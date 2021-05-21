import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
  private boolean[][] arr;
  private final int n;
  private final WeightedQuickUnionUF uf;
  private int numOpenSites;
  private boolean percolates;
  private boolean[] isConnectedTop;
  private boolean[] isConnectedBottom;
  
  public Percolation(int n) {
    if (n <= 0) throw new IllegalArgumentException();

    this.n = n;
    numOpenSites = 0;
    arr = new boolean[n][n];  
    uf = new WeightedQuickUnionUF(n*n);
    percolates = false;
    isConnectedTop = new boolean[n*n];
    isConnectedBottom = new boolean[n*n];
  }
  
  public void open(int row, int col) {
    handleException(row, col);
    if (!isOpen(row, col)) {
      arr[row-1][col-1] = true;
 
      int temp = uf.find(convert(row, col));
      if (row == 1) isConnectedTop[temp] = true;
      if (row == n) isConnectedBottom[temp] = true;
      
      if (row > 1 && isOpen(row-1, col)) openHelper(row, col, -1, 0);
      if (row < n && isOpen(row+1, col)) openHelper(row, col, 1, 0);
      if (col > 1 && isOpen(row, col-1)) openHelper(row, col, 0, -1);
      if (col < n && isOpen(row, col+1)) openHelper(row, col, 0, 1);
      
      temp = uf.find(convert(row, col));
      if (isConnectedBottom[temp] && isConnectedTop[temp]) percolates = true;
      
      ++numOpenSites;
    }
  }
  
  private void openHelper(int row, int col, int rowOffset, int colOffset) {
    boolean ct = false;
    boolean cb = false;
    int x = uf.find(convert(row, col));
    int y = uf.find(convert(row + rowOffset, col + colOffset));
    
    if (isConnectedTop[x] || isConnectedTop[y]) ct = true;
    if (isConnectedBottom[x] || isConnectedBottom[y]) cb = true;
    
    uf.union(convert(row + rowOffset, col + colOffset), convert(row, col));
    
    int z = uf.find(convert(row, col));
    isConnectedTop[z] = ct;
    isConnectedBottom[z] = cb;
  }
  
  
  public boolean isOpen(int row, int col) {
    handleException(row, col);
    return arr[row-1][col-1];
  }
  
  public boolean isFull(int row, int col) {
    handleException(row, col);
    return isConnectedTop[uf.find(convert(row, col))];
  }
  
  public int numberOfOpenSites() {
    return numOpenSites;
  }
  
  public boolean percolates() {
    return percolates;
  }
  
  private void handleException(int row, int col) {
    if (row < 1 || col < 1 || row > n || col > n) {
      throw new IllegalArgumentException();
    }
  }
  
  private int convert(int row, int col) {
    handleException(row, col);
    return (row - 1) * n + (col - 1);
  }
  
  
 
}
