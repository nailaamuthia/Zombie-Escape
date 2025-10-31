import greenfoot.*;

public class ExitButtonSmall extends Button {
    public ExitButtonSmall() {
        setImage(scaled("ex.png"));
    }
    private GreenfootImage scaled(String f) {
        GreenfootImage img = new GreenfootImage(f);
        double k = 140.0 / img.getWidth();
        img.scale((int)(img.getWidth()*k), (int)(img.getHeight()*k));
        return img;
    }
    @Override protected void onClick() { Greenfoot.setWorld(new LevelSelectWorld()); }
}
