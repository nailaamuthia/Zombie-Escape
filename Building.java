import greenfoot.*;

/**
 * Class Building — menampilkan gambar bangunan dengan tinggi tertentu.
 * Gambar otomatis diskalakan proporsional agar tidak gepeng.
 * Digunakan untuk rumah / bangunan di belakang garis pembatas.
 */
public class Building extends Actor {

    public Building(String imageName, int targetHeight) {
        // Ambil gambar dari file di folder "images"
        GreenfootImage img = new GreenfootImage(imageName);

        // Cek validitas tinggi agar tidak error
        if (targetHeight > 0 && img.getHeight() > 0) {
            double scale = (double) targetHeight / img.getHeight();
            int newW = (int)(img.getWidth() * scale);
            int newH = (int)(img.getHeight() * scale);
            img.scale(newW, newH);
        }

        setImage(img);
    }

    /** Titik pintu bangunan (tengah bawah, sedikit di atas rumput). */
    public int getDoorX() {
        return getX();
    }

    public int getDoorY() {
        return getY() + getImage().getHeight() / 2 - 10;
    }
}
