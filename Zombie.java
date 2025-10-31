import greenfoot.*;

public abstract class Zombie extends Actor {
    protected double speed;     // piksel per tick (boleh < 1.0)
    protected int hp;

    protected GreenfootImage walkImg;
    protected GreenfootImage deadImg;

    // posisi presisi agar speed kecil tetap halus
    protected double xPrecise;

    protected boolean dying = false;
    protected int deathTimer = 30;

    public Zombie(String walk, String dead, int hp, double speed) {
        this.hp = hp;
        this.speed = speed;

        walkImg = new GreenfootImage(walk);
        deadImg = new GreenfootImage(dead);

        // skala biar pas grid
        int newH = 60;
        double k = (double) newH / walkImg.getHeight();
        walkImg.scale((int)(walkImg.getWidth() * k), (int)(walkImg.getHeight() * k));
        deadImg.scale((int)(deadImg.getWidth() * k), (int)(deadImg.getHeight() * k));

        setImage(walkImg);
    }

    @Override
    protected void addedToWorld(World w) {
        xPrecise = getX();
    }

    @Override
    public void act() {
        // === PAUSE GUARD ===
        World w = getWorld();
        if (w instanceof GameWorld && ((GameWorld) w).isPaused()) return;
        // ====================

        if (dying) {
            deathAnimation();
            return;
        }

        moveZombie();

        // kalau lewat divider → -1 heart
        if (w instanceof GameWorld) {
            GameWorld gw = (GameWorld) w;
            if (getX() <= gw.getDividerX()) {
                gw.loseLife();
                if (getWorld() != null) getWorld().removeObject(this);
            }
        }
    }

    // gerak pakai posisi presisi
    protected void moveZombie() {
        xPrecise -= speed;
        setLocation((int)Math.round(xPrecise), getY());
    }

    public void hit() {
        hp--;
        if (hp <= 0) die();
    }

    protected void die() {
        dying = true;
        setImage(deadImg);
    }

    private void deathAnimation() {
        if (--deathTimer <= 0) {
            World w = getWorld();
            if (w instanceof GameWorld) ((GameWorld) w).addScore(5);
            if (w != null) w.removeObject(this);
        }
    }

    // dipakai Spear: 1-hit kill tanpa animasi
    public void dieInstant() {
        World w = getWorld();
        if (w instanceof GameWorld) ((GameWorld) w).addScore(5);
        if (w != null) w.removeObject(this);
    }
}
