package bgfx.shaders.geometry;
import bgfx.primitives.*;
import bgfx.primitives.vertices.Vertex;

public abstract class GeometryShader<OUT extends Vertex, IN extends Vertex> {
  
  public abstract OUT[] out(IN v0, IN v1, IN v2);

}