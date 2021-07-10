package bgfx.shaders;

import bgfx.shaders.geometry.DiffuseLightGS;
import bgfx.shaders.pixel.DefaultPS;
import bgfx.shaders.pixel.PixelShader;
import bgfx.shaders.vertex.DefaultVS;
import bgfx.shaders.vertex.VertexShader;

public class DefaultEffect extends Effect{

  public DefaultEffect() {
    super(new DefaultVS(), new DefaultPS(), new DiffuseLightGS());
  }
  
  

}
