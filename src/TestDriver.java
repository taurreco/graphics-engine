import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestDriver extends TestLoop{
  
  private final int WIDTH = 400;
  private final int HEIGHT = 400;
  double xTheta = 0;
  double yTheta = 0;
  double zTheta = 0;
  double xThetaSpeed = 0;
  double yThetaSpeed = 0;
  double zThetaSpeed = 0;
  double zOffset = 0;
  double[][] rotationMatrix;
  Vector3 translationVector;
  JFrame frame = new JFrame();
  Scene[] scenes = {
      new CubeDemoScene(WIDTH, HEIGHT),
      new OBJLoaderTestScene(WIDTH, HEIGHT, "tetrahedron.obj"),
      new OBJLoaderTestScene(WIDTH, HEIGHT, "trungle.obj"),
      new OBJLoaderTestScene(WIDTH, HEIGHT, "trungle_2.obj"),
  };
  int sceneIndex = 0;
  
  BufferedReader objReader;
  
  public TestDriver(){
    translationVector = new Vector3(0, 0, 0);
    frame.add(scenes[sceneIndex]);
    frame.addKeyListener(new RotationListener());
    frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.pack();
    runSim();    
  }
    
  public void runSim(){
    Thread loop = new Thread(this);
    loop.start();
  }
  
  @Override
  void update(double deltaTime) {}
  
  @Override
  void render() {
    xTheta += xThetaSpeed;
    yTheta += yThetaSpeed;
    zTheta += zThetaSpeed;
    double[][] zRotationMatrix = {
        {Math.cos(zTheta), -Math.sin(zTheta), 0},
        {Math.sin(zTheta), Math.cos(zTheta), 0},
        {0, 0, 1}
    };
    double[][] yRotationMatrix = {
        {Math.cos(yTheta), 0, Math.sin(yTheta)},
        {0, 1, 0},
        {-Math.sin(yTheta), 0, Math.cos(yTheta)}
    };
    double[][] xRotationMatrix = {
        {1, 0, 0},
        {0, Math.cos(xTheta), -Math.sin(xTheta)},
        {0, Math.sin(xTheta), Math.cos(xTheta)}
    };           
    double[][] rotationMatrix = EngineMath.matrixMultiplyMatrix(zRotationMatrix, yRotationMatrix);
    rotationMatrix = EngineMath.matrixMultiplyMatrix(rotationMatrix, xRotationMatrix);
    scenes[sceneIndex].update(rotationMatrix, translationVector);
    scenes[sceneIndex].repaint();
  }
    
  public static void main(String[] args){
    new TestDriver();
  }
  
  
  class RotationListener implements KeyListener{
    @Override
    public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == 38){
        xThetaSpeed = 0.01;
      }
      if (e.getKeyCode() == 40){
        xThetaSpeed = -0.01;
      }
      if (e.getKeyCode() == 37){
        yThetaSpeed = 0.01;
      }
      if (e.getKeyCode() == 39){
        yThetaSpeed = -0.01;
      }
      if (e.getKeyCode() == 87){
        zThetaSpeed = 0.01;
      }
      if (e.getKeyCode() == 83){
        zThetaSpeed = -0.01;
      } 
      if (e.getKeyCode() == 32){
        translationVector = translationVector.add(new Vector3(0, 0, 0.1));  
      }
      if (e.getKeyCode() == 86){
        translationVector = translationVector.add(new Vector3(0, 0, -0.1));
      }
      if (e.getKeyCode() == 81){
        if (sceneIndex + 1 < scenes.length){
          sceneIndex += 1;
        } else {
          sceneIndex = 0;
        }
        frame.add(scenes[sceneIndex]);
        scenes[sceneIndex].setVisible(true);
        for (JPanel scene : scenes){
          if (scene != scenes[sceneIndex]){
            scene.setVisible(false);
          }
        }
      }   
    }
        
    @Override
    public void keyReleased(KeyEvent e) {
      if (e.getKeyCode() == 38 || e.getKeyCode() == 40){
        xThetaSpeed = 0;
        xTheta = 0;
      }
      if (e.getKeyCode() == 37 || e.getKeyCode() == 39){
        yThetaSpeed = 0;
        yTheta = 0;
      }
      if (e.getKeyCode() == 87 || e.getKeyCode() == 83){
        zThetaSpeed = 0;
        zTheta = 0;
      }
      if (e.getKeyCode() == 32 || e.getKeyCode() == 86){
        translationVector = new Vector3(0, 0, 0);
      }   
    }
        
    @Override
    public void keyTyped(KeyEvent arg0) {}   
  }
}
