import greenfoot.*;

public class PauseButton extends Button {
    private GreenfootImage imgPause;
    private GreenfootImage imgPlay;

    public PauseButton() {
        // pakai nama file yang ADA di folder images
        imgPause = loadScaled("btn_pause.png", 64); // tinggi ~48 px
        imgPlay  = loadScaled("btn_play.png",  64);

        setImage(imgPause);
    }

    @Override
    protected void onClick() {
        World w = getWorld();
        if (!(w instanceof GameWorld)) return;

        GameWorld gw = (GameWorld) w;
        gw.togglePaused();                          // ubah state pause di world

        // ganti icon sesuai status
        setImage(gw.isPaused() ? imgPlay : imgPause);
    }

    // ===== util aman =====
    private GreenfootImage loadScaled(String file, int targetH) {
        try {
            GreenfootImage img = new GreenfootImage(file);
            double k = (double) targetH / img.getHeight();
            img.scale((int)(img.getWidth() * k), (int)(img.getHeight() * k));
            return img;
        } catch (IllegalArgumentException e) {
            GreenfootImage img = new GreenfootImage(80, targetH);
            img.setColor(greenfoot.Color.GRAY); img.fill();
            img.setColor(greenfoot.Color.WHITE); img.drawString("Missing: " + file, 2, targetH/2);
            return img;
        }
    }
}
