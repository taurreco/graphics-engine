package bgfx.rasterizer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bgfx.primitives.*;
import bgfx.primitives.vertices.*;
import bgfx.shaders.pixel.*;
import bgfx.shaders.vertex.*;
import bgfx.shaders.geometry.*;
import bmth.util.*;

public class Pipeline{
    
  public Graphics graphics;
  
  private BufferedImage texture;
    
  private ScreenSpaceTransformer sst;
  private double[][] rotationMatrix;
  private Vector3 translationVector;
  private ZBuffer zBuffer;
  
 public VertexShader vs;
 public PixelShader ps;
 public GeometryShader gs;
  
  public Pipeline(){
  }
  
  public Pipeline(VertexShader vs){
    this.vs = vs;
    this.ps = new DefaultPS();
  }
  
  public Pipeline(PixelShader ps) {
    this.vs = new DefaultVS();
    this.ps = ps;
  }
  
  public Pipeline(VertexShader vs, PixelShader ps){
    this.vs = vs;
    this.ps = ps;
  }
  
  public Pipeline(VertexShader vs, GeometryShader gs){
    this(vs);
    this.gs = gs;
  }
  
  public Pipeline(PixelShader ps, GeometryShader gs){
    this(ps);
    this.gs = gs;
  }
  
  public Pipeline(VertexShader vs, PixelShader ps, GeometryShader gs){
    this(vs, ps);
    this.gs = gs;
  }
  
  public void beginFrame(){
    zBuffer.resetDepths();
  }
    
  public void bindSST(ScreenSpaceTransformer sst){
    this.sst = sst;
    this.zBuffer = new ZBuffer(sst.SCREEN_WIDTH, sst.SCREEN_HEIGHT);
  }
  
  private void bindTexture(File textureFile) throws IOException{
    this.texture = ImageIO.read(textureFile);
  }
  
  private Color sampleTexture(Vertex vertex){
    int textureWidth = this.texture.getWidth();
    int textureHeight = this.texture.getHeight();
    int textureClampX = textureWidth - 1;
    int textureClampY = textureHeight - 1;
    return new Color(texture.getRGB((int)(Math.min(((TextureVertex) vertex).tc.x * textureWidth, textureClampX)), 
        (int)Math.min(((TextureVertex)vertex).tc.y * textureHeight, textureClampY)));
  }
    
  public void draw(IndexedTriangleList triangles, File texture, Graphics g){
    graphics = g;
    try {
      bindTexture(texture);
    } catch (IOException e) {
      e.printStackTrace();
    }
    processVertices(triangles.vertices, triangles.indices);
  }

  private void processVertices(Vertex[] vertices, int[] indices){ 
    Vertex[] verticesOut = new Vertex[vertices.length];
    for (int i = 0; i < vertices.length; i++){
      verticesOut[i] = vs.out(vertices[i]);
    }
    assembleTriangles(verticesOut, indices);
  }
    
  private void assembleTriangles(Vertex[] vertices, int[] indices){  
    for (int i = 0; i < indices.length; i += 3){
      // grab triangle vertices
      Vertex v0 = vertices[indices[i]];
      Vertex v1 = vertices[indices[i+1]];
      Vertex v2 = vertices[indices[i+2]];
      if (gs != null) {
        Vertex[] out = gs.out(v0, v1, v2);
        v0 = out[0];
        v1 = out[1];
        v2 = out[2];
      }
      
     

      // perform backface cull test
      Vector3 a = v1.wc.subtract(v0.wc);
      Vector3 b = v2.wc.subtract(v0.wc);
      Vector3 cross = a.cross(b);   // triangle normal

      if (!(cross.dot(v0.wc) >= 0)){

        processTriangle(v0, v1, v2); 
      }
    }        
  }
    
  private void processTriangle(Vertex v0, Vertex v1, Vertex v2){ 
    
    postProcessTriangleVertices(new Triangle(v0, v1, v2));
  }
    
  private void postProcessTriangleVertices(Triangle triangle){   
    Vertex v0 = sst.transform(triangle.v0);
    Vertex v1 = sst.transform(triangle.v1);
    Vertex v2 = sst.transform(triangle.v2);
    drawTriangle(new Triangle(v0,v1,v2));
  }
    
  public void drawTriangle(Triangle triangle){  
    
    Vertex v0 = triangle.v0;
    Vertex v1 = triangle.v1;
    Vertex v2 = triangle.v2;

    // Step one, sort points by height
    if (v1.wc.y < v0.wc.y){
      Vertex temp = v0;
      v0 = v1;
      v1 = temp;
    }
    if (v2.wc.y < v1.wc.y){
      Vertex temp = v1;
      v1 = v2;
      v2 = temp;        
    }
    if (v1.wc.y < v0.wc.y){
      Vertex temp = v0;
      v0 = v1;
      v1 = temp;
    }
    if (v0.wc.y == v1.wc.y){ // natural flat top triangle
      // sort by x (left to right)
      if (v1.wc.x < v0.wc.x){
        Vertex temp = v0;
        v0 = v1;
        v1 = temp;
      }
      drawFlatTopTriangle(new Triangle(v0, v1, v2));
    } else if (v1.wc.y == v2.wc.y){ // natural flat bottom triangle
      // sort by x (left to right)
      if (v2.wc.x < v1.wc.x){
        Vertex temp = v1;
        v1 = v2;
        v2 = temp;
      }
      drawFlatBottomTriangle(new Triangle(v0, v1, v2));
    } else { // It must be a general triangle, so we have to split it
      double alphaSlice = (v1.wc.y - v0.wc.y)/(v2.wc.y - v0.wc.y);
      Vertex vi = v0.interpolateTo(v2, alphaSlice);
      if (v1.wc.x < vi.wc.x){ // major right
        drawFlatBottomTriangle(new Triangle(v0, v1, vi));
        drawFlatTopTriangle(new Triangle(v1, vi, v2));
      } else { // major left
        drawFlatBottomTriangle(new Triangle(v0, vi, v1));
        drawFlatTopTriangle(new Triangle(vi, v1, v2));
      }
    }
  }
    
  private void drawFlatTopTriangle(Triangle triangle){
    Vertex v0 = triangle.v0;
    Vertex v1 = triangle.v1;
    Vertex v2 = triangle.v2;
        
    double deltaY = v2.wc.y - v0.wc.y;
    Vertex edgeStepL = v2.subtract(v0).scale(1/deltaY); // left edge step
    Vertex edgeStepR = v2.subtract(v1).scale(1/deltaY); // right edge step
 
    Vertex edgeR = v1;
    drawFlatTriangle(new Triangle(v0, v1, v2), edgeStepL, edgeStepR, edgeR);
  }

  private void drawFlatBottomTriangle(Triangle triangle){
    Vertex v0 = triangle.v0;
    Vertex v1 = triangle.v1;
    Vertex v2 = triangle.v2;
    
    Vertex bottomEdgeL = v1;
    Vertex bottomEdgeR = v2;
        
    double deltaY = v2.wc.y - v0.wc.y;
    Vertex edgeStepL = bottomEdgeL.subtract(v0).scale(1/deltaY); // left edge step
    Vertex edgeStepR = bottomEdgeR.subtract(v0).scale(1/deltaY); // right edge step
 
    Vertex edgeR = v0;
        
    drawFlatTriangle(new Triangle(v0, v1, v2), edgeStepL, edgeStepR, edgeR);
  }

  private void drawFlatTriangle(Triangle triangle, Vertex edgeStepL, Vertex edgeStepR, Vertex edgeR){
    Vertex v0 = triangle.v0;
    Vertex v1 = triangle.v1;
    Vertex v2 = triangle.v2;

    int yStart = (int)Math.ceil(v0.wc.y - 0.5); 
    int yEnd = (int)Math.ceil(v2.wc.y - 0.5);
        
    Vertex edgeL = v0;
        
    edgeL = edgeL.add(edgeStepL.scale((yStart + 0.5 - v0.wc.y)));
    edgeR = edgeR.add(edgeStepR.scale((yStart + 0.5 - v0.wc.y)));

    // init clamp values
    for (int y = yStart; y < yEnd; y++){
      int xStart = (int)Math.ceil(edgeL.wc.x - 0.5);
      int xEnd = (int)Math.ceil(edgeR.wc.x - 0.5);     
      Vertex scanLineStep = (edgeR.subtract(edgeL)).scale(1/(edgeR.wc.x - edgeL.wc.x));      
      Vertex tc = edgeL.add(scanLineStep.scale(xStart + 0.5 - edgeL.wc.x));
      for (int x = xStart; x < xEnd; x++){ 
        double z = 1 / tc.wc.z;
        if (zBuffer.test(x, y, z)){
          Vertex sampler = tc.scale(z);
          graphics.setColor(sampleTexture(sampler));
     //   graphics.setColor(effect.ps.getColor(sampler));
          graphics.drawLine(x, y, x, y);
        }
        tc = tc.add(scanLineStep);
      }
      edgeL = edgeL.add(edgeStepL);
      edgeR = edgeR.add(edgeStepR);
    }
  }
}