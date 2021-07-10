package bgfx.shaders.pixel;
import java.awt.Color;

import bgfx.primitives.vertices.ColorVertex;


public class ColorPS extends PixelShader<ColorVertex>{
  public Color getColor(ColorVertex vertex){
    return vertex.c;
  }
}