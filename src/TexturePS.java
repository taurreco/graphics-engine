import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TexturePS extends PixelShader{
  
  public int textureWidth;
  public int textureHeight;
  public int textureClampX;
  public int textureClampY;
      
  public BufferedImage texture;
           
  public Color getColor(Vertex vertex){
    return new Color(texture.getRGB((int)(Math.min(((TextureVertex) vertex).tc.x * textureWidth, textureClampX)), 
        (int)Math.min(((TextureVertex)vertex).tc.y * textureHeight, textureClampY)));
  }
      
  public void bindTexture(File textureFile) throws IOException{
    this.texture = ImageIO.read(textureFile);
    textureWidth = texture.getWidth();
    textureHeight = texture.getHeight();
    textureClampX = textureWidth - 1;
    textureClampY = textureHeight - 1;
  } 
}
