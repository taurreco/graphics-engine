
public abstract class Vertex<THIS extends Vertex>{
  
  public Vector3 wc;
        
  public Vertex(Vector3 wc){
    this.wc = wc;
  }
        
  public abstract THIS setWC(Vector3 wc);
  public abstract THIS rotate(double[][] rotationMatrix);
  public abstract THIS translate(Vector3 translationVector);
  public abstract THIS add(THIS vertex);
  public abstract THIS subtract(THIS vertex);
  public abstract THIS subtract(Vector3 wc);
  public abstract THIS scale(double sf);
  public abstract THIS interpolateTo(THIS destination, double alpha);
  public abstract THIS normalizeWC();
        
}