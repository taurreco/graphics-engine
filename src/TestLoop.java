public abstract class TestLoop implements Runnable{
  
  private boolean running;  
  public TestLoop(){
    running = true;
  }
  
  @Override
  public void run(){
    loop();
  }
  
  public void loop(){
    final int MAX_FRAMES_PER_SECOND = 30;
    final long OPTIMAL_TIME = 1000000000
          / MAX_FRAMES_PER_SECOND;
    double deltaTime = 0;
    double elapsedTime = 0;
    double lastLoopTime = 0;
    int frames = 0;
    long startTime = System.nanoTime();
    while (running){
      long now = System.nanoTime();
      elapsedTime = now - startTime;      
      startTime = System.nanoTime();
      
      deltaTime = elapsedTime / OPTIMAL_TIME;
      lastLoopTime += elapsedTime;
      frames++;
      if (lastLoopTime >= 1000000000){
        lastLoopTime = 0;
        frames = 0;
      }
            
      update(deltaTime);
      render();
      try {
        Thread.sleep(Math.abs((startTime-System.nanoTime() 
            + OPTIMAL_TIME)/1000000));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
    
  abstract void update(double deltaTime);
  abstract void render();
}


