import greenfoot.*;

public class GameOverPanel extends Actor {
    public GameOverPanel() {
        GreenfootImage img = new GreenfootImage("go.png"); // file kamu
        scaleToWidth(img, 520);
        setImage(img);
    }
    private void scaleToWidth(GreenfootImage img, int w) {
        double k = (double) w / img.getWidth();
        img.scale((int)(img.getWidth()*k), (int)(img.getHeight()*k));
    }
}
