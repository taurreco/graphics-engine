package bgfx.shaders.pixel;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

import bgfx.primitives.vertices.Vertex;

public abstract class PixelShader <VERTEX extends Vertex>{
  abstract public Color getColor(VERTEX vertex);
  public void bindTexture(File textureFile) throws IOException{}
}