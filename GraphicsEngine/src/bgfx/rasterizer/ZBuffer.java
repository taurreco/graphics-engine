package bgfx.rasterizer;

public class ZBuffer {
  private double[][] pixelDepths;
  
  public ZBuffer(int SCREEN_WIDTH, int SCREEN_HEIGHT){
    pixelDepths = new double[SCREEN_WIDTH][SCREEN_HEIGHT];
    resetDepths();
  }
    
  public void resetDepths(){
    for (int i = 0; i < pixelDepths.length; i++){
      for (int j = 0; j < pixelDepths[i].length; j++){
        pixelDepths[i][j] = Double.MAX_VALUE;
      }
    }
  }
    
  public boolean test(int x, int y, double z){
    if (pixelDepths[x][y] > z){
      pixelDepths[x][y] = z;
      return true;
    }
    return false;
  }
}