import greenfoot.*;

public class Zakky extends Actor {
    private static final int TARGET_H = 70;
    private static final int OFFSET_FROM_DIVIDER = 95;

    private GreenfootImage stand, attack1, attack2;
    private int attackAnimTimer = 0;
    private int shootCd = 0;
    private static final int SHOOT_CD_FRAMES = 14;

    @Override
    protected void addedToWorld(World w) {
        stand   = scaleToHeight(new GreenfootImage("zakky1.png"), TARGET_H);
        attack1 = scaleToHeight(new GreenfootImage("zakky2.png"), TARGET_H);
        attack2 = scaleToHeight(new GreenfootImage("zakky3.png"), TARGET_H);
        setImage(stand);

        if (w instanceof GameWorld) {
            int x = ((GameWorld) w).getDividerX() + OFFSET_FROM_DIVIDER;
            int y = (getY() == 0) ? w.getHeight() - 130 : getY();
            setLocation(x, y);
        }
    }

    private GreenfootImage scaleToHeight(GreenfootImage img, int h) {
        double k = (double) h / img.getHeight();
        img.scale((int)(img.getWidth()*k),(int)(img.getHeight()*k));
        return img;
    }

    @Override
    public void act() {
        World w = getWorld();
        if (!(w instanceof GameWorld) || ((GameWorld) w).isPaused()) return;

        int ny = getY();
        if (Greenfoot.isKeyDown("up"))   ny -= 3;
        if (Greenfoot.isKeyDown("down")) ny += 3;
        ny = Math.max(210, Math.min(ny, w.getHeight() - 85));
        setLocation(getX(), ny);

        if (shootCd > 0) shootCd--;

        if (Greenfoot.isKeyDown("space") && shootCd == 0) {
            shoot();
            shootCd = SHOOT_CD_FRAMES;
            attackAnimTimer = 8;
            setImage(attack1);
        } else if (attackAnimTimer > 0) {
            attackAnimTimer--;
            if (attackAnimTimer == 4) setImage(attack2);
            if (attackAnimTimer == 0) setImage(stand);
        }
    }

    private void shoot() {
        int vx = 8;
        Spear s = new Spear(vx);
        getWorld().addObject(s, getX() + 28, getY() - 6);
        try { new GreenfootSound("zombieshooter.wav").play(); } catch (Exception ignore) {}
    }
}
