package component;

import javax.swing.*;

public class PanelGame extends JComponent {

    private int width;
    private int height;
    private Thread thread;
    private boolean start = true;


    // Game FPS
    // 60 fps
    // 1 second = 1,000 milisecond
    // 1,000,000,000 nanosecond = 1 second
    // 1,000,000,000/60 = 16,666,666.666 nanosecond
    // Target time = 16,666,666.666
    private final int FPS = 60;
    private final int TARGET_TIME = 1000000000 / FPS;

    public void start() {
        width=getWidth();
        height=getHeight();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(start) {
                    long startTime = System.nanoTime();
                    drawBackground();
                    drawGame();
                    render();
                    long time=System.nanoTime() - startTime;
                    if(time<TARGET_TIME) {
                        long sleep = (TARGET_TIME - time) / 1000000;
                        sleep(sleep);
                        System.out.println("Game rerendering every "+sleep+" miliseconds");
                    }

                }
            }
        });
        thread.start();
    }

    private void drawBackground() {

    }
    private void drawGame() {

    }

    private void render(){

    }

    private void sleep (long speed) {
        try {
            Thread.sleep(speed);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }
}
