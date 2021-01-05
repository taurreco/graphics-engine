
public class Vector3 extends Vector2{
    
  public double z;
    
  public Vector3(double x, double y, double z) {
    super(x, y);
    this.z = z;
  }
    
  public Vector3 add(Vector3 v){
    return new Vector3(x+v.x, y+v.y, z+v.z);
  }
    
  public Vector3 subtract(Vector3 v){
    return new Vector3(x-v.x, y-v.y, z-v.z);
  }
    
  public Vector3 scale(double sf){
    return new Vector3(x*sf, y*sf, z*sf);
  }
    
  public double dot(Vector3 v){
    return x*v.x + y*v.y + z*v.z;
  }
    
  public Vector3 cross(Vector3 v){
    return new Vector3(
        y*v.z - z*v.y,
        z*v.x - x*v.z,
        x*v.y - y*v.x
        );
  }
    
    public Vector3 interpolateTo(Vector3 destination, double alpha){
        return (Vector3) (destination.subtract(this).scale(alpha).add(this));
    }
    
    public Vector3 normalize(){
        double magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z,  2));
        return new Vector3(x / magnitude, y / magnitude, z / magnitude);
    }
    
    
    public Vector3 opposite(){
        return new Vector3(-this.x, -this.y, -this.z);
    }

}
