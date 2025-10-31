import greenfoot.*;

public class LevelButton extends Button {
    private final int level;
    private final int targetH;

    public LevelButton(int level) {
        this(level, 80);
    }

    public LevelButton(int level, int targetHeight) {
        this.level = level;
        this.targetH = targetHeight;
        setImageSafe("level" + level + ".png");
    }

    @Override
    protected void onClick() {
        // efek klik
        try {
            new GreenfootSound("chooselevelclick.wav").play();
        } catch (Exception ignore) {}

        // stop BGM choose level sebelum pindah ke game
        Bgm.stop();

        // pindah ke world Game
        Greenfoot.setWorld(new GameWorld(level));
    }

    private void setImageSafe(String file) {
        try {
            GreenfootImage img = new GreenfootImage(file);
            double k = (double) targetH / img.getHeight();
            img.scale((int)(img.getWidth() * k), targetH);
            setImage(img);
        } catch (IllegalArgumentException e) {
            GreenfootImage img = new GreenfootImage(80, 80);
            img.setColor(Color.GRAY);
            img.fill();
            img.setColor(Color.WHITE);
            img.drawString("Lv " + level, 18, 45);
            setImage(img);
        }
    }
}
