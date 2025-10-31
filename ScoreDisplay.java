import greenfoot.*;

public class ScoreDisplay extends Actor {
    private int score = 0;

    public ScoreDisplay() {
        redraw();
    }

    public void addScore(int amount) {
        score += amount;
        redraw();
    }

    public int getScore() { return score; }

    private void redraw() {
        GreenfootImage img = new GreenfootImage(160, 40);
        img.setColor(new Color(0,0,0,0)); // transparan
        img.clear();
        img.setColor(Color.WHITE);
        img.setFont(new Font("Arial", true, false, 28));
        img.drawString("Score: " + score, 5, 28);
        setImage(img);
    }
}
