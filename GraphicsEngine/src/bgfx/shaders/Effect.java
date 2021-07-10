package bgfx.shaders;

import bgfx.primitives.IndexedTriangleList;
import bgfx.shaders.geometry.GeometryShader;
import bgfx.shaders.pixel.PixelShader;
import bgfx.shaders.vertex.VertexShader;

public abstract class Effect {
  
  public VertexShader vs;
  public PixelShader ps;
  public GeometryShader gs;
  
  public Effect (VertexShader vs, PixelShader ps) {
    this.vs = vs;
    this.ps = ps;
  }
  public Effect (VertexShader vs, PixelShader ps, GeometryShader gs) {
    this(vs, ps);
    this.gs = gs;
  }

}
