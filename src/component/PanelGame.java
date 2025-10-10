package component;

import object.Player;

import javax.swing.JComponent;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class PanelGame extends JComponent {
    private Graphics2D g2;
    private BufferedImage image;
    private int width;
    private int height;
    private Thread thread;
    private boolean start = true;
    private Key key;


    // Game FPS
    // 60 fps
    // 1 second = 1,000 milisecond
    // 1,000,000,000 nanosecond = 1 second
    // 1,000,000,000/60 = 16,666,666.666 nanosecond
    // Target time = 16,666,666.666
    private final int FPS = 60;
    private final int TARGET_TIME = 1000000000 / FPS;

    // Game Object
    private Player player;

    public void start() {
        width = getWidth();
        height = getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
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
//                        System.out.println("Game rerendering every "+sleep+" miliseconds");
                    }

                }
            }
        });
        initObjectGame();
        initKeyboard();
        thread.start();
    }

    private void initObjectGame(){
        player = new Player();
        player.changeLocation(150, 150);
    }

    private void initKeyboard(){
        key = new Key();
        requestFocus();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    key.setKeyLeft(true);
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    key.setKeyRight(true);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    key.setKeySpace(true);
                } else if (e.getKeyCode() == KeyEvent.VK_J) {
                    key.setKeyJ(true);
                } else if (e.getKeyCode() == KeyEvent.VK_K) {
                    key.setKeyK(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    key.setKeyLeft(false);
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    key.setKeyRight(false);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    key.setKeySpace(false);
                } else if (e.getKeyCode() == KeyEvent.VK_J) {
                    key.setKeyJ(false);
                } else if (e.getKeyCode() == KeyEvent.VK_K) {
                    key.setKeyK(false);
                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                float s=0.5f; // for
                while (start) {
                    float angle = player.getAngle();
                    if(key.isKeyLeft()){
                        angle -= s;
                    }
                    if(key.isKeyRight()){
                        angle += s;
                    }
                    if(key.isKeySpace()){
                        player.speedUp();
                    } else {
                        player.speedDown();
                    }
                    player.update();
                    player.changeAngle(angle);
                    sleep(5);
                }
            }
        }).start();
    }


    private void drawBackground() {
        g2.setColor(new Color(30,30,30));
        g2.fillRect(0,0, width, height);
    }
    private void drawGame() {
        player.draw(g2);
    }

    private void render(){
        Graphics g = getGraphics();
        g.drawImage(image, 0, 0 , null);
        g.dispose();
    }

    private void sleep (long speed) {
        try {
            Thread.sleep(speed);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }
}
