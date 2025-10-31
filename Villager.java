import greenfoot.*;
import java.util.*;

public class Villager extends Actor {
    private int targetX, targetY;
    private int speed;
    private int waitTimer = 0;
    private Building home;

    private GreenfootImage[] framesRight = new GreenfootImage[2];
    private GreenfootImage[] framesLeft  = new GreenfootImage[2];
    private int frameIndex = 0;
    private int animDelay = 8;
    private int animTimer = animDelay;
    private int faceDirX = 1;

    public Villager(List<Building> homes) {
        if (homes != null && !homes.isEmpty()) {
            home = homes.get(Greenfoot.getRandomNumber(homes.size()));
        }

        String[][] pairs = {
            {"rakyat1.png", "rakyat2.png"},
            {"rakyat3.png", "rakyat4.png"}
        };
        String[] chosenPair = pairs[Greenfoot.getRandomNumber(pairs.length)];

        GreenfootImage f0 = new GreenfootImage(chosenPair[0]);
        GreenfootImage f1 = new GreenfootImage(chosenPair[1]);

        int targetH = 65;
        scaleToHeight(f0, targetH);
        scaleToHeight(f1, targetH);

        framesRight[0] = f0; framesRight[1] = f1;
        framesLeft[0]  = new GreenfootImage(f0); framesLeft[0].mirrorHorizontally();
        framesLeft[1]  = new GreenfootImage(f1); framesLeft[1].mirrorHorizontally();

        setImage(framesRight[0]);
        speed = 1 + Greenfoot.getRandomNumber(2);
    }

    private void scaleToHeight(GreenfootImage img, int targetH) {
        if (img.getHeight() <= 0 || targetH <= 0) return;
        double s = (double) targetH / img.getHeight();
        img.scale((int)(img.getWidth() * s), (int)(img.getHeight() * s));
    }

    @Override
    protected void addedToWorld(World w) {
        if (home != null) {
            setLocation(
                home.getX() + Greenfoot.getRandomNumber(30) - 15,
                home.getY() + Greenfoot.getRandomNumber(40) - 20
            );
            setNewTarget();
        }
    }

    @Override
    public void act() {
        World w = getWorld();
        if (w instanceof GameWorld && ((GameWorld) w).isPaused()) return;

        if (waitTimer > 0) { waitTimer--; return; }

        moveTowardsTarget();
        animateWalk();

        if (Math.abs(getX() - targetX) < 4 && Math.abs(getY() - targetY) < 4) {
            waitTimer = 40 + Greenfoot.getRandomNumber(60);
            setNewTarget();
        }
    }

    private void moveTowardsTarget() {
        int dx = targetX - getX();
        int dy = targetY - getY();

        if (dx != 0) faceDirX = (dx > 0) ? 1 : -1;

        int stepX = (dx == 0) ? 0 : (dx > 0 ? speed : -speed);
        int stepY = (dy == 0) ? 0 : (dy > 0 ? speed : -speed);

        setLocation(getX() + stepX, getY() + stepY);
    }

    private void animateWalk() {
        if (--animTimer <= 0) {
            animTimer = animDelay;
            frameIndex ^= 1;
            setImage(faceDirX < 0 ? framesLeft[frameIndex] : framesRight[frameIndex]);
        }
    }

    private void setNewTarget() {
        if (home == null) return;
        int rangeX = 50, rangeY = 30;
        targetX = home.getX() + Greenfoot.getRandomNumber(rangeX * 2) - rangeX;
        targetY = home.getY() + Greenfoot.getRandomNumber(rangeY * 2) - rangeY;
    }
}
