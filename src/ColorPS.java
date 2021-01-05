import java.awt.Color;


public class ColorPS extends PixelShader<ColorVertex>{
  public Color getColor(ColorVertex vertex){
    return vertex.c;
  }
}
