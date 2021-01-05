
class VertexShader{
  public Vector3 center = new Vector3(0, 0, 2);
  protected Vector3 translationVector = new Vector3(0, 0, 0);
  protected double[][] rotationMatrix = {
      {0, 0, 0},
      {0, 0, 0},
      {0, 0, 0}
      };
    
  public Vertex out(Vertex vertex) {
    Vertex vertexOut = vertex.translate(translationVector).subtract(center).rotate(rotationMatrix);
    vertexOut = vertexOut.translate(center);
    return vertexOut;
  }
  
  public void bindTranslationVector(Vector3 translationVector){
    this.translationVector = translationVector;
    center = center.add(translationVector); // accumulates translations from 0, 0, 0
  }
  
  public void bindRotationMatrix(double[][] rotationMatrix){
    this.rotationMatrix = rotationMatrix;
  }
}