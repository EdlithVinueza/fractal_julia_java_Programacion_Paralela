package com.programacion.paralela;

public class FPSCounter {

    private long lastTime;
    private int fps;
    private int frames;

    public FPSCounter(){
        lastTime = System.currentTimeMillis();
        fps = 0;
        frames =0;
    }

    public void update(){
        frames++;
        long now = System.currentTimeMillis();
        if(now - lastTime >= 1000){
            fps = frames;
            frames =0;
            lastTime = now;

            System.out.println("***FPS: " + fps);
        }
    }
}
