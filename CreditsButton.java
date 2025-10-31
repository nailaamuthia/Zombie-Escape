import greenfoot.*;

public class CreditsButton extends Button {
    public CreditsButton() {
        setImageSafe("Credits.png", 60); // tinggi tombol ~60px
    }

    @Override
    protected void onClick() {
        Greenfoot.setWorld(new CreditsWorld());
    }

    /* util aman */
    private void setImageSafe(String file, int targetH) {
        try {
            GreenfootImage img = new GreenfootImage(file);
            double k = (double) targetH / img.getHeight();
            img.scale((int)(img.getWidth()*k), (int)(img.getHeight()*k));
            setImage(img);
        } catch (IllegalArgumentException e) {
            GreenfootImage img = new GreenfootImage(200, targetH);
            img.setColor(greenfoot.Color.DARK_GRAY); img.fill();
            img.setColor(greenfoot.Color.YELLOW);    img.drawString("Missing: " + file, 8, targetH/2);
            setImage(img);
        }
    }
}
