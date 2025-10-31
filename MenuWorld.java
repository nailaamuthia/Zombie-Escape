import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot, and GreenfootSound)

/**
 * MenuWorld — tampilan awal game (menu utama)
 * Menampilkan tombol Start, Exit, dan Credits.
 * Memutar BGM intro saat berada di menu.
 */
public class MenuWorld extends World {

    private GreenfootSound menuBgm;  // ubah jadi non-static agar aman di web

    public MenuWorld() {
        super(975, 525, 1, false);

        // set background
        setBackground(safeBackground("title.png"));

        // mainkan intro BGM (pastikan intro.wav ada di folder sounds)
        try {
            menuBgm = new GreenfootSound("intro.wav");
            menuBgm.setVolume(60);
            menuBgm.playLoop();
        } catch (Exception e) {
            System.out.println("Gagal memutar BGM: " + e.getMessage());
        }

        // tambahkan tombol di tengah layar
        int centerX = getWidth() / 2;
        addObject(new StartButton(),   centerX, 330);
        addObject(new ExitButton(),    centerX, 410);
        addObject(new CreditsButton(), centerX, 465);
    }

    @Override
    public void stopped() {
        try { 
            if (menuBgm != null) menuBgm.stop(); 
        } catch (Exception ignore) {}
    }

    private GreenfootImage safeBackground(String file) {
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
