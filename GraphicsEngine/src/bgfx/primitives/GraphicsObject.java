package bgfx.primitives;

import bgfx.shaders.geometry.GeometryShader;
import bgfx.shaders.pixel.PixelShader;
import bgfx.shaders.vertex.VertexShader;

import java.io.File;

import bgfx.shaders.*;

public class GraphicsObject { // stores graphics information about some gameobject (the geometry and how it is rendered)
  
  public IndexedTriangleList triangles;
  public Effect effect;
  public File texture;
  
  public GraphicsObject(IndexedTriangleList triangles, Effect effect, File texture) {
    this.triangles = triangles;
    this.effect = effect;
    this.texture = texture;
  }
}
