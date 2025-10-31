import greenfoot.*;

public abstract class Button extends Actor {
    private static final GreenfootSound CLICK = new GreenfootSound("click.wav");

    protected void onClick() {}   // override di turunan kalau butuh aksi

    @Override
    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            try { CLICK.play(); } catch (Exception ignore) {}
            onClick();
        }
    }
}
