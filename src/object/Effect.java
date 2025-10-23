package object;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Effect {
    private final double x;
    private final double y;

    private final double maxDistance;
    private final int maxSize;

    private final Color color;
    private final int totalEffect;
    private final float speed;

    private double currentDistance;
    private ModelBoom booms[];
    private float alpha = 1f;

    public Effect(double x, double y, int totalEffect, int maxSize, int maxDistance, float speed, Color color) {
        this.x = x;
        this.y = y;
        this.totalEffect = totalEffect;
        this.maxSize = maxSize;
        this.maxDistance = maxDistance;
        this.speed = speed;
        this.color = color;

        createRandom();
    }

    private void createRandom() {
        booms = new ModelBoom[totalEffect];
        Random random = new Random();
        float per = 360f / totalEffect;
        for (int i = 1; i <= totalEffect; i++) {
            int r = random.nextInt((int) per) + 1;
            int boomSize = random.nextInt(maxSize) + 1;
            float angle = i * per + r;
            booms[i-1] = new ModelBoom(boomSize, angle);
        }
    }

    public void draw(Graphics2D g2) {
        AffineTransform oldTransform = g2.getTransform();
        Composite oldcomposite = g2.getComposite();
        g2.setColor(color);
        g2.translate(x, y);
        for(ModelBoom b: booms) {
            double bx = Math.cos(Math.toRadians(b.getAngle())) * currentDistance;
            double by = Math.sin(Math.toRadians(b.getAngle())) * currentDistance;
            double boomSize = b.getSize();
            double space = boomSize / 2;
            if (currentDistance >= maxDistance - (maxDistance * 0.7f)) {
                alpha = (float) ((maxDistance - currentDistance) / (maxDistance * 0.7f));
            }
            if (alpha > 1) {
                alpha = 1;
            } else if (alpha < 0) {
                alpha = 0;
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fill(new Rectangle2D.Double(bx - space, by - space, boomSize, boomSize));
        }
        g2.setComposite(oldcomposite);
        g2.setTransform(oldTransform);
    }

    public void update() {
        currentDistance+=speed;
    }

    public boolean check() {
        return currentDistance < maxDistance;
    }
}
