package bgfx.shaders;

import bgfx.shaders.pixel.PixelShader;
import bgfx.shaders.pixel.TexturePS;
import bgfx.shaders.vertex.DefaultVS;
import bgfx.shaders.vertex.VertexShader;

public class TextureEffect extends Effect{

  public TextureEffect() {
    super(new DefaultVS(), new TexturePS());
  }
}
