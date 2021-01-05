
public class TextureVertex extends Vertex<TextureVertex>{
  
  public Vector2 tc;
  
  public TextureVertex(Vector3 wc, Vector2 tc){
    super(wc);
    this.tc = tc;
  }
      
  public TextureVertex setWC(Vector3 wc){
    return new TextureVertex(wc, this.tc);
  }
      
  public TextureVertex rotate(double[][] rotationMatrix){
    return new TextureVertex(
        EngineMath.matrixMultiplyVector3(rotationMatrix, wc),
        tc);
  }
      
  public TextureVertex translate(Vector3 translationVector){
    return new TextureVertex(
        wc.add(translationVector),
        tc);
  }
      
  public TextureVertex add(TextureVertex vertex){
    return new TextureVertex(
        this.wc.add(vertex.wc),
        this.tc.add(vertex.tc));
  }
      
  public TextureVertex subtract(TextureVertex vertex){
    return new TextureVertex(
        this.wc.subtract(vertex.wc),
        this.tc.subtract(vertex.tc));
  }
      
  public TextureVertex subtract(Vector3 wc){
    return new TextureVertex(
        this.wc.subtract(wc),
        this.tc);
  }
      
  public TextureVertex scale(double sf){
    return new TextureVertex(
        this.wc.scale(sf),
        this.tc.scale(sf));
  }
      
  public TextureVertex interpolateTo(TextureVertex destination, double alpha){
    return new TextureVertex(
        this.wc.interpolateTo(destination.wc, alpha),
        this.tc.interpolateTo(destination.tc, alpha));
  }
      
  public TextureVertex normalizeWC(){
    return new TextureVertex(this.wc.normalize(), this.tc);
  }

}
