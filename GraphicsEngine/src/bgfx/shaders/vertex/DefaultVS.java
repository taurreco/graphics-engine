package bgfx.shaders.vertex;

import bgfx.primitives.*;
import bgfx.primitives.vertices.Vertex;
import bgfx.shaders.*;
import bmth.util.*;

public class DefaultVS extends VertexShader{
    
  public Vertex out(Vertex vertex) {
    Matrix vertexMatrix = vertex.wc.toMatrix();
    Vertex vertexOut = vertex.setWC(modelMatrix.multiply(vertexMatrix).toVector3());
    return vertexOut;
  }
}
 