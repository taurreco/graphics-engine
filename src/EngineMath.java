
public class EngineMath {
    
  public static double[][] matrixMultiplyMatrix(double[][] matrixA, double[][] matrixB){
    if (matrixA[0].length == matrixB.length){
      double[][] product = new double[matrixA.length][matrixB[0].length];

      for (int row = 0; row < product.length; row++){
        for (int col = 0; col < product[row].length; col++){
          for (int i = 0; i < matrixA[row].length; i++){
            product[row][col] += matrixA[row][i] * matrixB[i][col];
          }
        }
      }
      return product;
    }
    System.out.println("ERROR cant multiply matricies");
    return null;
  }
    
  public static Vector3 matrixMultiplyVector3(double[][] m, Vector3 v){
        
    if (m.length == 3 && m[0].length == 3){
      Vector3 v0 = new Vector3(m[0][0], m[0][1], m[0][2]);
      Vector3 v1 = new Vector3(m[1][0], m[1][1], m[1][2]);
      Vector3 v2 = new Vector3(m[2][0], m[2][1], m[2][2]);
      return new Vector3(
          v.dot(v0),
          v.dot(v1),
          v.dot(v2));
    }
    return null;
  }
}
