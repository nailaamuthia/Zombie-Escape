import greenfoot.*;

public class WinPanel extends Actor {
    public WinPanel() {
        GreenfootImage img = new GreenfootImage("mission c.png"); // file kamu
        scaleToWidth(img, 520);
        setImage(img);
    }
    private void scaleToWidth(GreenfootImage img, int w) {
        double k = (double) w / img.getWidth();
        img.scale((int)(img.getWidth()*k), (int)(img.getHeight()*k));
    }
}
