
public class ScreenSpaceTransformer {
    
  public final int SCREEN_WIDTH;
  public final int SCREEN_HEIGHT;
    
  public ScreenSpaceTransformer(int SCREEN_WIDTH, int SCREEN_HEIGHT){
    this.SCREEN_WIDTH = SCREEN_WIDTH;
    this.SCREEN_HEIGHT = SCREEN_HEIGHT;
  }

  public Vertex transform(Vertex vertex){
    if (vertex.wc.z != 0){
      double zInverse = 1 / vertex.wc.z;
      Vertex output = vertex.scale(zInverse);
      output.wc.x = (output.wc.x + 1) * (SCREEN_WIDTH/2);
      output.wc.y = (-output.wc.y + 1) * (SCREEN_HEIGHT/2); 
      output.wc.z = zInverse;
      return output;
    }
    return null;
  }
}
