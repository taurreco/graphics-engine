package bgfx.primitives.vertices;

import java.awt.Color;
import bgfx.primitives.*;
import bmth.util.EngineMath;
import bmth.util.Vector3;

public class ColorVertex extends Vertex<ColorVertex>{
  
  public Color c;
  
  public ColorVertex(Vector3 wc, Color c) {
    super(wc);
    this.c = c;
  }
  
  public ColorVertex setWC(Vector3 wc){
    return new ColorVertex(wc, this.c);
  }
      
  public ColorVertex rotate(double[][] rotationMatrix){
    return new ColorVertex(
        EngineMath.matrixMultiplyVector3(rotationMatrix, wc), this.c);
  }
      
  public ColorVertex translate(Vector3 translationVector){
    return new ColorVertex(
        wc.add(translationVector), this.c);
  }
      
  public ColorVertex add(ColorVertex vertex){
    return new ColorVertex(
        this.wc.add(vertex.wc), this.c);
  }
      
  public ColorVertex subtract(ColorVertex vertex){
    return new ColorVertex(
        this.wc.subtract(vertex.wc), this.c);
  }
      
  public ColorVertex subtract(Vector3 wc){
    return new ColorVertex(
        this.wc.subtract(wc), this.c);
  }
      
  public ColorVertex scale(double sf){
    return new ColorVertex(
        this.wc.scale(sf), this.c);
  }
      
  public ColorVertex interpolateTo(ColorVertex destination, double alpha){
    return new ColorVertex(
        this.wc.interpolateTo(destination.wc, alpha), this.c);
  }
      
  public ColorVertex normalizeWC(){
    return new ColorVertex(this.wc.normalize(), this.c);
  }
}