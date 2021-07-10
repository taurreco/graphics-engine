package bgfx.shaders.pixel;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

import bgfx.primitives.vertices.Vertex;


public class DefaultPS<VERTEX extends Vertex> extends PixelShader<VERTEX>{
  public Color getColor(VERTEX vertex){
    return Color.WHITE;
  }
}