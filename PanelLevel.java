import greenfoot.*;
public class PanelLevel extends Actor {
    public PanelLevel(){
        GreenfootImage img = new GreenfootImage("panel_level.png");
        // Kalau terlalu besar, kecilkan agar muat rapi
        int w = img.getWidth(), h = img.getHeight();
        if (w > 720) img.scale(720, (int)(720.0 * h / w));
        setImage(img);
    }
}
