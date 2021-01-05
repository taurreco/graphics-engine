import java.awt.Color;


public class DiffuseLightGS extends GeometryShader<ColorVertex, Vertex>{

  @Override
  public ColorVertex[] out(Vertex v0, Vertex v1, Vertex v2) {
    Color c = getColor(v0, v1, v2);
    ColorVertex[] outTriangle = new ColorVertex[3];
    outTriangle[0] = new ColorVertex(v0.wc, c);
    outTriangle[1] = new ColorVertex(v1.wc, c);
    outTriangle[2] = new ColorVertex(v2.wc, c);
    
    return outTriangle;
  }
  
  Color getColor(Vertex v0, Vertex v1, Vertex v2){
    Vector3 a = v1.wc.subtract(v0.wc);
    Vector3 b = v2.wc.subtract(v0.wc);
    Vector3 normal = a.cross(b).normalize();
    Vector3 lightDir = new Vector3(0, 0, -1);
    double diff = Math.max(normal.dot(lightDir), 0);
    int lightVal = (int)(255 * diff);
    Color lightColor = new Color(lightVal, lightVal, lightVal);
    return lightColor;
  }

}
