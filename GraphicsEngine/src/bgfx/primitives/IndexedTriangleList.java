package bgfx.primitives;

import bgfx.primitives.vertices.Vertex;
import bmth.util.*;

public class IndexedTriangleList {
  
  public Vertex[] vertices;
  public Vertex[] verticesWC;
  public int[] indices;
  public Vector3 center;  
  
  public IndexedTriangleList(Vertex[] vertices, int[] indices){
    this.vertices = vertices;
    this.verticesWC = new Vertex[vertices.length];
    this.indices = indices;
    this.center = new Vector3(0, 0, 0);
    for (int i = 0; i < vertices.length; i++){
      center = center.add(vertices[i].wc);
    }
    center = center.scale(1 / vertices.length);
  }
    
  public void translate(Vector3 translationVector){
    center = center.add(translationVector);
    for (int i = 0; i < vertices.length; i++){
      verticesWC[i] = vertices[i].translate(center);
    }
  }
    
  public void rotate(double[][] rotationMatrix){
    for (int i = 0; i < vertices.length; i++){
      vertices[i] = vertices[i].rotate(rotationMatrix);
    }
  }
}