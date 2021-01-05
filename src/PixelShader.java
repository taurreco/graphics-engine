import java.awt.Color;
import java.io.File;
import java.io.IOException;


class PixelShader <VERTEX extends Vertex>{
  public Color getColor(VERTEX vertex){
    return Color.WHITE;
  }
  public void bindTexture(File textureFile) throws IOException{}
}