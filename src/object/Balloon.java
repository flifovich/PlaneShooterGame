package object;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

public class Balloon extends HpRender {
    public static final double BALLOON_SIZE=50;
    private double x;
    private double y;
    private final float speed=0.3f;
    private float angle=0;
    private final Image image;
    private final Area ballonShape;

    public Balloon(){
        super(new HP(20, 20));
        this.image = new ImageIcon(getClass().getResource("/image/balloon.png")).getImage();
        double shrinkFactor = 0.70; // size compared to png
        double ellipseWidth = BALLOON_SIZE * shrinkFactor;
        double ellipseHeight = BALLOON_SIZE * 1.1 * shrinkFactor;

        double offsetX = (BALLOON_SIZE - ellipseWidth) / 2;
        double offsetY = (BALLOON_SIZE - ellipseHeight) / 2;

        Ellipse2D.Double ellipse = new Ellipse2D.Double(
                offsetX + BALLOON_SIZE * 0.1,
                offsetY - BALLOON_SIZE * 0.1,
                ellipseWidth,
                ellipseHeight
        );

        ballonShape = new Area(ellipse);
    }

    public void changeLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        x += Math.cos(Math.toRadians(angle)) * speed;
        y += Math.sin(Math.toRadians(angle)) * speed;
    }

    public void changeAngle(float angle) {
        if(angle<0) {
            angle = 359;
        }else if (angle > 359) {
            angle = 0;
        }

        this.angle = angle;
    }

    public void draw(Graphics2D g2) {
        AffineTransform oldTransform = g2.getTransform();
        g2.translate(x, y);
        AffineTransform tran = new AffineTransform();
        g2.drawImage(image, tran, null);
        Shape shape = getShape();
        hpRender(g2, shape, y);
        g2.setTransform(oldTransform);

        // Test for shape
//        g2.setColor(Color.green);
//        g2.draw(shape);
//        g2.draw(shape.getBounds2D());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public float getAngle() {
        return angle;
    }

    public Area getShape() {
        AffineTransform afx = new AffineTransform();
        afx.translate(x, y);

        return new Area(afx.createTransformedShape(ballonShape));
    }

    public boolean check(int width, int height) {
        Rectangle size = getShape().getBounds();
        if(x <= -size.getWidth() || y < -size.getHeight() || x > width || y > height) {
            return false;
        } else {
            return true;
        }
    }
}
