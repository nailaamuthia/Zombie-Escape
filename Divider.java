import greenfoot.*;

public class Divider extends Actor {
    public Divider(int heightPx) {
        // strip vertikal 14px lebar, setinggi world
        GreenfootImage img = new GreenfootImage(14, 373);

        // warna cokelat gelap agak transparan
        img.setColor(new greenfoot.Color(60, 40, 20, 220));
        img.fill();

        // garis pinggir biar terlihat tegas
        img.setColor(new greenfoot.Color(255, 255, 255, 120));
        img.drawRect(0, 0, img.getWidth() - 1, img.getHeight() - 1);

        setImage(img);
    }
}
