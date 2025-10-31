import greenfoot.*;

public class RetryButton extends Button {
    private final int level;
    public RetryButton(int levelNow) {
        this.level = levelNow;
        setImage(scaled("rty.png"));
    }
    private GreenfootImage scaled(String f) {
        GreenfootImage img = new GreenfootImage(f);
        double k = 140.0 / img.getWidth();
        img.scale((int)(img.getWidth()*k), (int)(img.getHeight()*k));
        return img;
    }
    @Override protected void onClick() { Greenfoot.setWorld(new GameWorld(level)); }
}
