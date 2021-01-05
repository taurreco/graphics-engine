import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class OBJFileLoader {

  ArrayList<Vector3> worldCoords;
  ArrayList<Vector3> normals;
  ArrayList<Vertex> vertices;
  ArrayList<Integer> indices;
  
  
  public OBJFileLoader(){
    
    worldCoords = new ArrayList<>();
    normals = new ArrayList<>();
    vertices = new ArrayList<>();
    indices = new ArrayList<>();
    
  }
  
  public ArrayList[] read(BufferedReader objReader) throws IOException{
    
    String strCurrentLine = objReader.readLine();
    boolean hasBuiltVertices = false;
    
    ArrayList[] returnList = new ArrayList[2];

    while (strCurrentLine != null) {
      if (strCurrentLine.charAt(0) == 'v' && strCurrentLine.charAt(1) == ' '){
        worldCoords.add(parseVector3(strCurrentLine, 8));
      }
      if (strCurrentLine.charAt(0) == 'v' && strCurrentLine.charAt(1) == 'n'  && strCurrentLine.charAt(2) == ' '){
        normals.add(parseVector3(strCurrentLine, 6));
      }
      if (strCurrentLine.charAt(0) == 'f' && strCurrentLine.charAt(1) == ' '){
        if (!hasBuiltVertices){
          buildVertices();
          hasBuiltVertices = true;
        }
        parseIndices(strCurrentLine);
      }
      strCurrentLine = objReader.readLine();
    }
    returnList[0] = vertices;
    returnList[1] = indices;
    return returnList;
  }
  
  public Vector3 parseVector3(String strCurrentLine, int coordLength){
    double x = 0;
    double y = 0;
    double z = 0;
    for (int i = 0; i < strCurrentLine.length(); i++){
      if (Character.isDigit(strCurrentLine.charAt(i))){
        if (i < coordLength){
          x = Double.parseDouble(strCurrentLine.substring(i, i + coordLength));
        } if (i >= coordLength && i < 2*coordLength){
          y = Double.parseDouble(strCurrentLine.substring(i, i + coordLength));
        } else if (i >= 2*coordLength && i < 3*coordLength){
          z = Double.parseDouble(strCurrentLine.substring(i, i + coordLength));
        }
        i += coordLength;
      } else if (strCurrentLine.charAt(i) == '-'){
        if (i < coordLength){
          x = Double.parseDouble(strCurrentLine.substring(i, i + coordLength + 1));
        } if (i >= coordLength && i < 2*coordLength){
          y = Double.parseDouble(strCurrentLine.substring(i, i + coordLength + 1));
        } else if (i >= 2*coordLength && i < 3*coordLength){
          z = Double.parseDouble(strCurrentLine.substring(i, i + coordLength + 1));
        }
        i += coordLength + 1;
      }
    }
    return new Vector3(x, y, z);
  }

  public ArrayList<Integer> parseIndices(String strCurrentLine){
    for (int i = 1; i < strCurrentLine.length(); i++){
      if (strCurrentLine.charAt(i - 1) == ' ' && Character.isDigit(strCurrentLine.charAt(i))){
        indices.add(Integer.parseInt(Character.toString(strCurrentLine.charAt(i))) - 1);
      }
    }
    return indices;
  }
  
  public void buildVertices(){
    for (int i = 0; i < worldCoords.size(); i++){
      vertices.add(new LameVertex(worldCoords.get(i)));
    }
  }
  
}
