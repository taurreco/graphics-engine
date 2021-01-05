
public class LameVertex extends Vertex<LameVertex>{

  public LameVertex(Vector3 wc) {
    super(wc);
  }
  
  public LameVertex setWC(Vector3 wc){
    return new LameVertex(wc);
  }
      
  public LameVertex rotate(double[][] rotationMatrix){
    return new LameVertex(
        EngineMath.matrixMultiplyVector3(rotationMatrix, wc));
  }
      
  public LameVertex translate(Vector3 translationVector){
    return new LameVertex(
        wc.add(translationVector));
  }
      
  public LameVertex add(LameVertex vertex){
    return new LameVertex(
        this.wc.add(vertex.wc));
  }
      
  public LameVertex subtract(LameVertex vertex){
    return new LameVertex(
        this.wc.subtract(vertex.wc));
  }
      
  public LameVertex subtract(Vector3 wc){
    return new LameVertex(
        this.wc.subtract(wc));
  }
      
  public LameVertex scale(double sf){
    return new LameVertex(
        this.wc.scale(sf));
  }
      
  public LameVertex interpolateTo(LameVertex destination, double alpha){
    return new LameVertex(
        this.wc.interpolateTo(destination.wc, alpha));
  }
      
  public LameVertex normalizeWC(){
    return new LameVertex(this.wc.normalize());
  }

}
