import greenfoot.*;

public class StartButton extends Button {
    public StartButton() { setImageSafe("btn_start.png"); }

    @Override
    protected void onClick() {
        try { 
            // hentikan semua musik sebelum pindah ke level select
            Bgm.stop(); 
        } catch (Exception ignore) {}

        Greenfoot.setWorld(new LevelSelectWorld());
    }

    private void setImageSafe(String file) {
        try {
            GreenfootImage img = new GreenfootImage(file);
            if (img.getHeight() > 150) {
                double k = 150.0 / img.getHeight();
                img.scale((int)(img.getWidth() * k), (int)(img.getHeight() * k));
            }
            setImage(img);
        } catch (IllegalArgumentException e) {
            GreenfootImage img = new GreenfootImage(200, 60);
            img.setColor(greenfoot.Color.GRAY); 
            img.fill();
            img.setColor(greenfoot.Color.WHITE); 
            img.drawString("Missing: " + file, 10, 35);
            setImage(img);
        }
    }
}
