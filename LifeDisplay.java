import greenfoot.*;

public class LifeDisplay extends Actor {
    private final int maxLives = 3;
    private int lives = 3;
    private final int spacing = 45; // jarak antar hati sedikit dilebarkan
    private GreenfootImage heartFull;
    private GreenfootImage heartEmpty;

    public LifeDisplay() {
        heartFull = new GreenfootImage("heart.png");
        heartFull.scale(55, 55); // 🔺besar dari 28 ke 38 px

        heartEmpty = new GreenfootImage(heartFull);
        heartEmpty.setTransparency(70);

        updateImage();
    }

    public void loseLife() {
        if (lives > 0) {
            lives--;
            updateImage();
        }
    }

    public int getLives() { return lives; }

    private void updateImage() {
        int width = maxLives * spacing;
        GreenfootImage img = new GreenfootImage(width, 40);
        for (int i = 0; i < maxLives; i++) {
            if (i < lives) {
                img.drawImage(heartFull, i * spacing, 0);
            } else {
                img.drawImage(heartEmpty, i * spacing, 0);
            }
        }
        setImage(img);
    }
}


