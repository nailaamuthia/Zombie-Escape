import greenfoot.*;
import java.util.*;

public class GameWorld extends World {
    private final int level;
    private boolean paused = false, ended = false;
    private PauseButton pauseButton;

    private static final int DIVIDER_X_DEFAULT = 220;
    private static final String[] BUILDING_IMAGES = {
        "bangunan.png","bangunan5.png","bangunan6.png","bangunan7.png"
    };

    private final List<Building> buildings = new ArrayList<>();
    private ScoreDisplay scoreDisplay;
    private LifeDisplay  lifeDisplay;
    private int spawnTimer = 0, zombiesSpawned = 0, maxZombies = 0;
    private final int[] laneCd = new int[4];

    // ================= CONSTRUCTOR =================
    public GameWorld(int level) {
        super(975, 525, 1, false);
        this.level = level;

        // hentikan semua musik lain sebelum mulai
        Bgm.stop();

        // setup tampilan dan elemen level
        setLevelBackground();
        addDivider();
        addBuildings();
        spawnVillagers(4);
        addHUD();
        addPauseButton();
        addBackButtonIfAny();

        // tambahkan karakter utama (Zakky)
        addObject(new Zakky(), getDividerX() + 85, rowToY(1));

        prepareLevel();
        spawnTimer = 150;

        // mulai BGM pertempuran
        Bgm.playLoop("war.wav", 60);
    }

    public GameWorld() { this(1); }

    @Override public void stopped() {
        // otomatis hentikan musik saat world distop
        Bgm.stop();
    }

    // ================= STATIC METHOD UNTUK HENTIKAN BGM =================
    public static void stopWarBgm() {
        try {
            Bgm.stop();
        } catch (Exception e) {
            System.out.println("Gagal menghentikan BGM perang: " + e.getMessage());
        }
    }

    // ================= PAUSE HANDLING =================
    public boolean isPaused() { return paused; }
    public void setPaused(boolean v) { paused = v; }
    public void togglePaused() { paused = !paused; }

    private void addPauseButton() {
        pauseButton = new PauseButton();
        addObject(pauseButton, getWidth() - 60, 100);
        setPaintOrder(
            NextBtn.class, RetryBtn.class, ExitSmallBtn.class,
            WinPanel.class, GameOverPanel.class, DimBg.class,
            PauseButton.class, InGameBackButton.class,
            Divider.class, LifeDisplay.class, ScoreDisplay.class,
            Villager.class, Building.class
        );
    }

    private void addBackButtonIfAny() {
        try {
            addObject(new InGameBackButton(), getWidth() - 120, 100);
        } catch (Exception ignore) {}
    }

    // ================= BACKGROUND & BUILDINGS =================
    private void setLevelBackground() {
        String bg = (level == 2) ? "bg_level2.png" :
                    (level == 3) ? "bg_level3.png" : "bg_level1.png";
        setBackground(new GreenfootImage(bg));
    }

    private void addDivider() {
        int dividerX = getDividerX(), dividerHeight = 700, dividerY = getHeight()/2 + 75;
        addObject(new Divider(dividerHeight), dividerX, dividerY);
    }

    public int getDividerX() {
        switch (level) { 
            case 2: return 218; 
            case 3: return 222; 
            default: return DIVIDER_X_DEFAULT; 
        }
    }

    private void addBuildings() {
        int dividerX = getDividerX();
        final int[] H = {92, 90, 94, 90};
        final int[][] POS = { {70,215}, {dividerX-72,280}, {80,370}, {dividerX-68,455} };

        buildings.clear();
        for (int i = 0; i < 4; i++) {
            Building b = new Building(BUILDING_IMAGES[i], H[i]);
            int x = POS[i][0], baseline = POS[i][1];
            int y = baseline - b.getImage().getHeight()/2;
            addObject(b, x, y);
            buildings.add(b);
        }
    }

    private void spawnVillagers(int jumlah) {
        int n = Math.min(jumlah, Math.max(1, buildings.size()));
        for (int i = 0; i < n; i++) addObject(new Villager(buildings), 0, 0);
    }

    private void addHUD() {
        int hudY = 35;
        scoreDisplay = new ScoreDisplay(); addObject(scoreDisplay, 130, hudY);
        lifeDisplay  = new LifeDisplay();  addObject(lifeDisplay, getWidth() - 100, hudY);
    }

    // ================= SCORE & LIFE =================
    public void addScore(int amount) {
        if (scoreDisplay != null) scoreDisplay.addScore(amount);
    }

    public void loseLife() {
        if (lifeDisplay != null) {
            try { new GreenfootSound("heart.wav").play(); } catch (Exception ignore) {}
            lifeDisplay.loseLife();
            if (lifeDisplay.getLives() <= 0) gameOver();
        }
    }

    // ================= LEVEL / ZOMBIE SPAWN =================
    private void prepareLevel() {
        if (level == 1) maxZombies = 20;
        else if (level == 2) maxZombies = 30;
        else maxZombies = 40;
    }

    @Override
    public void act() {
        if (paused || ended) return;
        tickLaneCooldowns();

        if (zombiesSpawned < maxZombies) {
            if (spawnTimer > 0) spawnTimer--;
            else {
                if (zombiesSpawned >= 8) {
                    spawnBurst(2);
                    spawnTimer = (level==1)? Greenfoot.getRandomNumber(160)+260
                               : (level==2)? Greenfoot.getRandomNumber(130)+230
                                           : Greenfoot.getRandomNumber(110)+210;
                } else {
                    spawnOneZombie();
                    spawnTimer = (level==1)? Greenfoot.getRandomNumber(180)+300
                               : (level==2)? Greenfoot.getRandomNumber(120)+240
                                           : Greenfoot.getRandomNumber(90)+210;
                }
            }
        }

        if (!ended && zombiesSpawned >= maxZombies && getObjects(Zombie.class).isEmpty())
            onWin();
    }

    private void tickLaneCooldowns() {
        for (int i = 0; i < laneCd.length; i++) if (laneCd[i] > 0) laneCd[i]--;
    }

    private void spawnOneZombie() {
        List<Integer> available = new ArrayList<>();
        for (int r = 0; r < 4; r++) if (laneCd[r] == 0) available.add(r);
        if (available.isEmpty()) { spawnTimer = 60; return; }
        spawnAtRow(available.get(Greenfoot.getRandomNumber(available.size())));
    }

    private void spawnBurst(int count) {
        List<Integer> available = new ArrayList<>();
        for (int r = 0; r < 4; r++) if (laneCd[r] == 0) available.add(r);
        if (available.isEmpty()) { spawnTimer = 60; return; }
        Collections.shuffle(available);
        for (int i = 0; i < Math.min(count, available.size()) && zombiesSpawned < maxZombies; i++)
            spawnAtRow(available.get(i));
    }

    private void spawnAtRow(int row) {
        int y = rowToY(row), x = getWidth() - 60;
        Zombie z;
        if (level == 1) {
            z = (Greenfoot.getRandomNumber(100) < 70) ? new Zombie1() : new Zombie2();
        } else if (level == 2) {
            int r = Greenfoot.getRandomNumber(100);
            z = (r < 40) ? new Zombie1() : (r < 70 ? new Zombie2() : new Zombie3());
        } else {
            int r = Greenfoot.getRandomNumber(100);
            z = (r < 45) ? new Zombie1() : (r < 75 ? new Zombie2() : new Zombie3());
        }
        addObject(z, x, y); zombiesSpawned++;

        if (level == 1)      laneCd[row] = 200 + Greenfoot.getRandomNumber(100);
        else if (level == 2) laneCd[row] = 170 + Greenfoot.getRandomNumber(90);
        else                 laneCd[row] = 140 + Greenfoot.getRandomNumber(80);
    }

    private int rowToY(int row) { int top = 215, gap = 78; return top + row * gap; }

    // ================= WIN / GAME OVER =================
    private void onWin() {
        ended = true; paused = true;
        Bgm.stop();
        addObject(new DimBg(getWidth(), getHeight()), getWidth()/2, getHeight()/2);
        addObject(new WinPanel(), getWidth()/2, getHeight()/2);
        int by = getHeight()/2 + 180;
        addObject(new ExitSmallBtn(), getWidth()/2 - 90, by);
        addObject(new NextBtn(level), getWidth()/2 + 90, by);
        try { new GreenfootSound("missioncomplete.wav").play(); } catch (Exception ignore) {}
    }

    private void gameOver() {
        if (ended) return;
        ended = true; paused = true;
        Bgm.stop();
        addObject(new DimBg(getWidth(), getHeight()), getWidth()/2, getHeight()/2);
        addObject(new GameOverPanel(), getWidth()/2, getHeight()/2);
        int by = getHeight()/2 + 180;
        addObject(new RetryBtn(level), getWidth()/2 - 90, by);
        addObject(new ExitSmallBtn(),  getWidth()/2 + 90, by);
        try { new GreenfootSound("game_over.wav").play(); } catch (Exception ignore) {}
    }

    // ================= INNER UI CLASSES =================
    private static class DimBg extends Actor {
        public DimBg(int w, int h) {
            GreenfootImage img = new GreenfootImage(w, h);
            img.setColor(new Color(0,0,0,160)); img.fill();
            setImage(img);
        }
    }
    private static class WinPanel extends Actor {
        public WinPanel() { GreenfootImage img = new GreenfootImage("missionc.png"); scale(img, 520); setImage(img); }
    }
    private static class GameOverPanel extends Actor {
        public GameOverPanel() { GreenfootImage img = new GreenfootImage("go.png"); scale(img, 520); setImage(img); }
    }
    private static void scale(GreenfootImage img, int w) {
        double k = (double) w / img.getWidth(); img.scale((int)(img.getWidth()*k),(int)(img.getHeight()*k));
    }

    private static class NextBtn extends Button {
        private final int levelNow;
        public NextBtn(int levelNow){ this.levelNow=levelNow; setImage(scaleBtn("next.png")); }
        private GreenfootImage scaleBtn(String f){ GreenfootImage img=new GreenfootImage(f); double k=140.0/img.getWidth(); img.scale((int)(img.getWidth()*k),(int)(img.getHeight()*k)); return img; }
        protected void onClick(){ if(levelNow<3) Greenfoot.setWorld(new GameWorld(levelNow+1)); else Greenfoot.setWorld(new LevelSelectWorld()); }
    }
    private static class RetryBtn extends Button {
        private final int levelNow;
        public RetryBtn(int levelNow){ this.levelNow=levelNow; setImage(scaleBtn("rty.png")); }
        private GreenfootImage scaleBtn(String f){ GreenfootImage img=new GreenfootImage(f); double k=140.0/img.getWidth(); img.scale((int)(img.getWidth()*k),(int)(img.getHeight()*k)); return img; }
        protected void onClick(){ Greenfoot.setWorld(new GameWorld(levelNow)); }
    }
    private static class ExitSmallBtn extends Button {
        public ExitSmallBtn(){ setImage(scaleBtn("ex.png")); }
        private GreenfootImage scaleBtn(String f){ GreenfootImage img=new GreenfootImage(f); double k=140.0/img.getWidth(); img.scale((int)(img.getWidth()*k),(int)(img.getHeight()*k)); return img; }
        protected void onClick(){ Greenfoot.setWorld(new LevelSelectWorld()); }
    }
}
