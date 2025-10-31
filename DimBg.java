import greenfoot.*;

public class DimBg extends Actor {
    public DimBg(int w, int h) {
        GreenfootImage img = new GreenfootImage(w, h);
        img.setColor(new Color(0, 0, 0, 160)); // hitam transparan
        img.fill();
        setImage(img);
    }
}
