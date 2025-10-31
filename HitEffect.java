import greenfoot.*;

public class HitEffect extends Actor {
    private int timer = 12; // durasi tampil

    public HitEffect() {
        GreenfootImage img = new GreenfootImage("efek.png");
        double k = 80.0 / img.getHeight();  // tinggi target 80px
        img.scale((int)(img.getWidth() * k), (int)(img.getHeight() * k));
        setImage(img);
    }

    @Override
    public void act() {
        World w = getWorld();
        if (w instanceof GameWorld && ((GameWorld) w).isPaused()) return;

        if (--timer <= 0 && getWorld() != null) {
            getWorld().removeObject(this);
        }
    }
}
