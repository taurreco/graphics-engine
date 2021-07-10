package bgfx.shaders.vertex;

import bgfx.primitives.vertices.Vertex;
import bmth.util.*;

public abstract class VertexShader{
  public Matrix modelMatrix;
 
  public abstract Vertex out(Vertex vertex);
  public void bindModelMatrix(Matrix modelMatrix) {
    this.modelMatrix = modelMatrix;
  };
}