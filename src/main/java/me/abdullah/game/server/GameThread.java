package me.abdullah.game.server;

import me.abdullah.game.handlers.Handler;

public class GameThread implements Runnable {

    private Thread thread;
    private boolean running;

    private final Handler handler;

    public GameThread(Handler handler){
        this.handler = handler;
    }

    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * Thread loop
     */
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1){
                tick();
                delta--;
            }
        }
        stop();
    }

    private void tick(){
        handler.tick();
    }
}
