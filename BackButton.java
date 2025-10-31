import greenfoot.*;

public class BackButton extends Button {
    private final int targetH;

    public BackButton() {
        this(70);
    }

    public BackButton(int targetHeight) {
        this.targetH = targetHeight;
        setImageSafe("btn_back.png"); // tombol papan “Back”
    }

    @Override
    protected void onClick() {
        // matikan semua musik yang sedang aktif
        Bgm.stop();

        // kembali ke MenuWorld
        Greenfoot.setWorld(new MenuWorld());
    }

    private void setImageSafe(String file) {
        try {
            GreenfootImage img = new GreenfootImage(file);
            double k = (double) targetH / img.getHeight();
            img.scale((int)(img.getWidth() * k), targetH);
            setImage(img);
        } catch (IllegalArgumentException e) {
            GreenfootImage img = new GreenfootImage(200, 60);
            img.setColor(Color.GRAY);
            img.fill();
            img.setColor(Color.WHITE);
            img.drawString("Missing: " + file, 10, 35);
            setImage(img);
        }
    }
}
