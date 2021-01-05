
public class Vector2 {
  
  public double x;
  public double y;
  
  public Vector2(double x, double y){
    this.x = x;
    this.y = y;
  }
    
  public Vector2 add(Vector2 v){
    return new Vector2(x+v.x, y+v.y);
  }
   
  public Vector2 subtract(Vector2 v){
    return new Vector2(x-v.x, y-v.y);
  }
    
  public Vector2 scale(double s){
    return new Vector2(s*x, s*y);
  }
    
  public double dot(Vector2 v){
    return x*v.x + y*v.y;
  }
    
  public Vector2 interpolateTo(Vector2 destination, double alpha){
    return (destination.subtract(this).scale(alpha).add(this));
  }
  
  public Vector2 normalize(){
    double magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    return new Vector2(x / magnitude, y / magnitude);
  }
    
  public Vector2 opposite(){
    return new Vector2(-this.x, -this.y);
  }
}