package object;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class HpRender {
    private final HP hp;

    public HpRender(HP hp) {
        this.hp = hp;
    }

    protected void hpRender (Graphics2D g2, Shape shape, double y) {
        if (hp.getCurrentHP() != hp.getMaxHP()) {
            double hpY = shape.getBounds().getY() - y - 10;
            g2.setColor(new Color(70,70,70));
            g2.fill(new Rectangle2D.Double(0, hpY, Player.PLAYER_SIZE, 2));
            g2.setColor(new Color(253, 91, 91));
            double hpSize = hp.getCurrentHP()/hp.getMaxHP() * Player.PLAYER_SIZE;
            g2.fill(new Rectangle2D.Double(0, hpY, hpSize, 2));
        }
    }

    public boolean updateHP(double cutHP) {
        hp.setCurrentHP(hp.getCurrentHP() - cutHP);
        return hp.getCurrentHP() > 0;
    }

    public double getHp() {
        return hp.getCurrentHP();
    }

    public void resetHP() {
        hp.setCurrentHP(hp.getMaxHP());
    }
}
