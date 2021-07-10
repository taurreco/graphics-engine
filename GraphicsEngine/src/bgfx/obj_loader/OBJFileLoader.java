package bgfx.obj_loader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import bgfx.primitives.*;
import bgfx.primitives.vertices.*;

import bmth.util.*;

public class OBJFileLoader {

  ArrayList<Vector3> worldCoords;
  ArrayList<Vector3> normals;
  ArrayList<LameVertex> vertices;
  ArrayList<Integer> indices;
  
  
  public OBJFileLoader(){
    
    worldCoords = new ArrayList<>();
    normals = new ArrayList<>();
    vertices = new ArrayList<>();
    indices = new ArrayList<>();
    
  }
  
  public IndexedTriangleList load(String filename) {
    LameVertex[] lameVertices = null;
    int[] indices = null;
    try {
      BufferedReader objReader = new BufferedReader(new FileReader(filename));
      ArrayList[] returnList = this.read(objReader);
      
      int lameVerticesSize = returnList[0].size();
      int indicesSize = returnList[1].size();
      
      lameVertices = new LameVertex[lameVerticesSize];
      indices = new int[indicesSize];
      
      for (int i = 0; i < lameVerticesSize; i++){
        lameVertices[i] = (LameVertex) returnList[0].get(i);
      }
      
      for (int i = 0; i < indicesSize; i++){
        indices[i] = (Integer)returnList[1].get(i);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
        
    return new IndexedTriangleList(lameVertices, indices);
  }
  
  private ArrayList[] read(BufferedReader objReader) throws IOException{
    
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
  
  private Vector3 parseVector3(String strCurrentLine, int coordLength){
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

  private ArrayList<Integer> parseIndices(String strCurrentLine){
    for (int i = 1; i < strCurrentLine.length(); i++){
      if (strCurrentLine.charAt(i - 1) == ' ' && Character.isDigit(strCurrentLine.charAt(i))){
        indices.add(Integer.parseInt(Character.toString(strCurrentLine.charAt(i))) - 1);
      }
    }
    return indices;
  }
  
  private void buildVertices(){
    for (int i = 0; i < worldCoords.size(); i++){
      vertices.add(new LameVertex(worldCoords.get(i)));
    }
  }
}