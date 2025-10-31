import greenfoot.*;

public class NextButton extends Button {
    private final int level;
    public NextButton(int levelNow) {
        this.level = levelNow;
        setImage(scaled("next.png"));
    }
    private GreenfootImage scaled(String f) {
        GreenfootImage img = new GreenfootImage(f);
        double k = 140.0 / img.getWidth();
        img.scale((int)(img.getWidth()*k), (int)(img.getHeight()*k));
        return img;
    }
    @Override protected void onClick() {
        if (level < 3) Greenfoot.setWorld(new GameWorld(level + 1));
        else           Greenfoot.setWorld(new LevelSelectWorld());
    }
}
