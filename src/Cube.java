import java.awt.Color;

public class Cube {
    
  public TextureVertex[] vertices = new TextureVertex[14];

  public int[] indices = {
      0,13,1, 13,12,1,
      12,9,1, 1,9,4,
      12,11,10, 12,10,9,
      1,4,2, 2,4,3,
      4,9,5, 9,8,5,
      5,8,7, 5,7,6
  };
  
  public Cube(){
    vertices[0] = new TextureVertex(new Vector3(-0.5, -0.5, -0.5), new Vector2((double)0/4, (double)(1+1)/3));//
    vertices[1] = new TextureVertex(new Vector3(0.5, -0.5, -0.5), new Vector2((double)1/4, (double)(1+1)/3));//
    vertices[2] = new TextureVertex(new Vector3(-0.5, -0.5, -0.5), new Vector2((double)1/4, (double)(2+1)/3));//
    vertices[3] = new TextureVertex(new Vector3(-0.5, -0.5, 0.5), new Vector2((double)2/4, (double)(2+1)/3));//
    vertices[4] = new TextureVertex(new Vector3(0.5, -0.5, 0.5), new Vector2((double)2/4, (double)(1+1)/3));//
    vertices[5] = new TextureVertex(new Vector3(-0.5, -0.5, 0.5), new Vector2((double)3/4, (double)(1+1)/3));//
    vertices[6] = new TextureVertex(new Vector3(-0.5, -0.5, -0.5), new Vector2((double)4/4, (double)(1+1)/3));//
    vertices[7] = new TextureVertex(new Vector3(-0.5, 0.5, -0.5), new Vector2((double)4/4, (double)(0+1)/3));//
    vertices[8] = new TextureVertex(new Vector3(-0.5, 0.5, 0.5), new Vector2((double)3/4, (double)(0+1)/3));//
    vertices[9] = new TextureVertex(new Vector3(0.5, 0.5, 0.5), new Vector2((double)2/4, (double)(0+1)/3));//
    vertices[10] = new TextureVertex(new Vector3(-0.5, 0.5, 0.5), new Vector2((double)2/4, (double)(-1+1)/3));//
    vertices[11] = new TextureVertex(new Vector3(-0.5, 0.5, -0.5), new Vector2((double)1/4, (double)(-1+1)/3));//
    vertices[12] = new TextureVertex(new Vector3(0.5, 0.5, -0.5), new Vector2((double)1/4, (double)(0+1)/3));//
    vertices[13] = new TextureVertex(new Vector3(-0.5, 0.5, -0.5), new Vector2((double)0/4, (double)(0+1)/3));//     
  }
}
