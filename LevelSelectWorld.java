import greenfoot.*;

public class LevelSelectWorld extends World {

    public LevelSelectWorld() {
        super(975, 525, 1, false);

        // hentikan semua BGM yang sedang main
        Bgm.stop();

        // background + overlay
        GreenfootImage bg = safeImage("title.png");
        bg.scale(getWidth(), getHeight());
        GreenfootImage overlay = new GreenfootImage(getWidth(), getHeight());
        overlay.setColor(new Color(0, 0, 0, 150)); // hitam transparan
        overlay.fill();
        bg.drawImage(overlay, 0, 0);
        setBackground(bg);

        // panel tengah
        GreenfootImage panel = safeImage("panel_level.png");
        int panelTargetH = 360;
        double s = (double) panelTargetH / panel.getHeight();
        panel.scale((int)(panel.getWidth() * s), panelTargetH);
        int panelX = (getWidth() - panel.getWidth()) / 2;
        int panelY = 70;
        getBackground().drawImage(panel, panelX, panelY);

        // tombol level (1,2,3)
        int centerX = getWidth() / 2;
        int buttonsY = panelY + panel.getHeight() - 145;
        int spacing = 95;
        addObject(new LevelButton(1, 100), centerX - spacing, buttonsY);
        addObject(new LevelButton(2, 100), centerX,            buttonsY);
        addObject(new LevelButton(3, 100), centerX + spacing,  buttonsY);

        // tombol back ke Menu
        int backY = panelY + panel.getHeight() - 8;
        addObject(new BackButton(150), centerX, backY);

        // mainkan BGM khusus level select
        Bgm.playLoop("chooselevel.wav", 60);
    }

    @Override
    public void stopped() {
        Bgm.stop();
    }

    private GreenfootImage safeImage(String file) {
        try {
            return new GreenfootImage(file);
        } catch (IllegalArgumentException e) {
            GreenfootImage img = new GreenfootImage(getWidth(), getHeight());
            img.setColor(Color.BLACK);
            img.fill();
            img.setColor(Color.WHITE);
            img.drawString("Missing: " + file, getWidth() / 2 - 60, getHeight() / 2);
            return img;
        }
    }
}
