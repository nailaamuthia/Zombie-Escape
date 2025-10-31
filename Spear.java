import greenfoot.*;

public class Spear extends Actor {
    private static final String IMG = "zakky_panah.png";
    private final int vx;

    public Spear(int vx) {
        this.vx = vx;
        GreenfootImage img = new GreenfootImage(IMG);
        if (img.getHeight() > 36) {
            double k = 36.0 / img.getHeight();
            img.scale((int)(img.getWidth()*k), (int)(img.getHeight()*k));
        }
        setImage(img);
    }

    @Override
    public void act() {
        World w = getWorld();
        if (w instanceof GameWorld && ((GameWorld) w).isPaused()) return;

        setLocation(getX() + vx, getY());

        // tabrak zombie → kurangi HP, efek, lalu panah hilang
        Zombie z = (Zombie) getOneIntersectingObject(Zombie.class);
        if (z != null) {
            if (getWorld() != null) getWorld().addObject(new HitEffect(), z.getX(), z.getY());
            z.hit();            // <<<<<< kurangi HP (bukan 1-hit kill)
            removeSelf();
            return;
        }

        if (isAtEdge()) removeSelf();
    }

    private void removeSelf() {
        if (getWorld() != null) getWorld().removeObject(this);
    }
}
