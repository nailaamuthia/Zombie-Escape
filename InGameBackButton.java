import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot, and MouseInfo)

/**
 * InGameBackButton — tombol "kembali" dari dalam game ke Level Select.
 * Menghentikan BGM game dan kembali ke menu pemilihan level.
 */
public class InGameBackButton extends Button {

    /**
     * Konstruktor: set gambar tombol dengan aman
     */
    public InGameBackButton() {
        setImageSafe("back.png");
    }

    /**
     * Aksi saat tombol diklik.
     * Berfungsi untuk menghentikan BGM dan kembali ke LevelSelectWorld.
     */
    @Override
    protected void onClick() {
        try {
            GameWorld.stopWarBgm(); // hentikan BGM dari GameWorld
        } catch (Exception ignore) {}

        try {
            Bgm.stop(); // hentikan musik tambahan (kalau ada)
        } catch (Exception ignore) {}

        try {
            Greenfoot.setWorld(new LevelSelectWorld()); // pindah ke dunia pemilihan level
        } catch (Exception e) {
            System.out.println("Gagal kembali ke LevelSelectWorld: " + e.getMessage());
        }
    }

    /**
     * Memuat gambar tombol dengan perlindungan error.
     * Jika file hilang, akan diganti gambar placeholder dengan teks "Back".
     */
    private void setImageSafe(String file) {
        try {
            GreenfootImage img = new GreenfootImage(file);
            // jika gambar terlalu besar, resize ke tinggi maksimum 60px
            if (img.getHeight() > 60) {
                double scaleFactor = 60.0 / img.getHeight();
                img.scale((int)(img.getWidth() * scaleFactor), (int)(img.getHeight() * scaleFactor));
            }
            setImage(img);
        } catch (IllegalArgumentException e) {
            // fallback kalau gambar tidak ditemukan
            GreenfootImage img = new GreenfootImage(80, 60);
            img.setColor(Color.GRAY);
            img.fill();
            img.setColor(Color.WHITE);
            img.drawString("Back", 15, 35);
            setImage(img);
        }
    }
}
