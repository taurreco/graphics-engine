import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class CubeDemoScene extends Scene{
    
  public final int SCREEN_WIDTH;
  public final int SCREEN_HEIGHT;
    
  public Pipeline pipeline;
    
  public File textureFile;
  public ScreenSpaceTransformer sst;
  public IndexedTriangleList triangles;  
    
  public CubeDemoScene(int SCREEN_WIDTH, int SCREEN_HEIGHT){      
    this.SCREEN_WIDTH = SCREEN_WIDTH;
    this.SCREEN_HEIGHT = SCREEN_HEIGHT;
        
    triangles = new IndexedTriangleList(new Cube().vertices, new Cube().indices);
    triangles.translate(new Vector3(0, 0, 2));        
    pipeline = new Pipeline(new VertexShader(), new TexturePS());
    textureFile = new File("textured_cube.jpg");
    
    sst = new ScreenSpaceTransformer(SCREEN_WIDTH, SCREEN_HEIGHT);
    pipeline.bindSST(sst);
  }
    
  public void paintComponent(Graphics g){
        
    pipeline.beginFrame();
    g.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    if (translationVector!=null && rotationMatrix != null){
      bind();
      pipeline.draw(triangles, g);
    }
  }
    
  public void bind(){
    pipeline.vs.bindRotationMatrix(rotationMatrix);
    pipeline.vs.bindTranslationVector(translationVector);
    try {
      pipeline.ps.bindTexture(textureFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}