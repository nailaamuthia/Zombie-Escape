import greenfoot.*;

public class CreditsWorld extends World {

    public CreditsWorld() {
        super(975, 525, 1, false);

        // pakai background level biar konsisten, bebas ganti sesuai selera
        setBackground(new GreenfootImage("bg_level1.png"));

        // gelapkan sedikit belakang
        addObject(new DimBg(getWidth(), getHeight()), getWidth()/2, getHeight()/2);

        // papan credits di tengah
        addObject(new CreditsPanel(680), getWidth()/2, getHeight()/2); // lebar target 680px

        // tombol kembali ke Menu
        addObject(new BackToMenuButton(), getWidth()/2, getHeight()/2 + 150);

        // urutan render
        setPaintOrder(BackToMenuButton.class, CreditsPanel.class, DimBg.class);
    }

    /* overlay gelap */
    private static class DimBg extends Actor {
        public DimBg(int w, int h) {
            GreenfootImage img = new GreenfootImage(w, h);
            img.setColor(new Color(0,0,0,160));
            img.fill();
            setImage(img);
        }
    }

    /* papan credits */
    private static class CreditsPanel extends Actor {
        public CreditsPanel(int targetW) {
            try {
                GreenfootImage img = new GreenfootImage("isiCredits.png");
                double k = (double) targetW / img.getWidth();
                img.scale((int)(img.getWidth()*k), (int)(img.getHeight()*k));
                setImage(img);
            } catch (IllegalArgumentException e) {
                GreenfootImage img = new GreenfootImage(700, 380);
                img.setColor(new Color(60,40,20)); img.fill();
                img.setColor(Color.WHITE); img.drawString("Missing: credits_panel.png", 20, 40);
                setImage(img);
            }
        }
    }

    /* tombol back ke MenuWorld */
    private static class BackToMenuButton extends Button {
        public BackToMenuButton() {
            setImageSafe("ex.png", 60); // boleh pakai gambar "EXIT" kecil sebagai Back
        }
        @Override
        protected void onClick() {
            Greenfoot.setWorld(new MenuWorld());
        }

        private void setImageSafe(String file, int targetH) {
            try {
                GreenfootImage img = new GreenfootImage(file);
                double k = (double) targetH / img.getHeight();
                img.scale((int)(img.getWidth()*k), (int)(img.getHeight()*k));
                setImage(img);
            } catch (IllegalArgumentException e) {
                GreenfootImage img = new GreenfootImage(180, targetH);
                img.setColor(greenfoot.Color.GRAY); img.fill();
                img.setColor(greenfoot.Color.WHITE); img.drawString("Back", 70, targetH/2);
                setImage(img);
            }
        }
    }
}
