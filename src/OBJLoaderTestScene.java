import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class OBJLoaderTestScene extends Scene{
    
  public final int SCREEN_WIDTH;
  public final int SCREEN_HEIGHT;
    
  public Pipeline pipeline;
    
  public File textureFile;
  public ScreenSpaceTransformer sst;
  public IndexedTriangleList triangles;  
  public String filename;
    
  public OBJLoaderTestScene(int SCREEN_WIDTH, int SCREEN_HEIGHT, String filename){      
    this.SCREEN_WIDTH = SCREEN_WIDTH;
    this.SCREEN_HEIGHT = SCREEN_HEIGHT;
    this.filename = filename;
    
    LameVertex[] lameVertices = null;
    int[] indices = null;
    
    try {
      BufferedReader objReader = new BufferedReader(new FileReader(filename));
      ArrayList[] returnList = new OBJFileLoader().read(objReader);
      
      int lameVerticesSize = returnList[0].size();
      int indicesSize = returnList[1].size();
      
      lameVertices = new LameVertex[lameVerticesSize];
      indices = new int[indicesSize];
      
      for (int i = 0; i < lameVerticesSize; i++){
        lameVertices[i] = (LameVertex) returnList[0].get(i);
      }
      
      for (int i = 0; i < indicesSize; i++){
        indices[i] = (Integer)returnList[1].get(i);
      }


    } catch (IOException e) {
      e.printStackTrace();
    }
        
    triangles = new IndexedTriangleList(lameVertices, indices);
    triangles.translate(new Vector3(0, 0, 2));        
    pipeline = new Pipeline(new VertexShader(), new ColorPS(), new DiffuseLightGS());
    
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
    
  }
}