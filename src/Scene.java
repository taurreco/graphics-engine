import javax.swing.JPanel;

public class Scene extends JPanel{
  public double[][] rotationMatrix;
  public Vector3 translationVector;
  
  public void update(double[][] rotationMatrix, Vector3 translationVector){
    this.rotationMatrix = rotationMatrix;
    this.translationVector = translationVector;
  }
}
