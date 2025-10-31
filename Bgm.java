import greenfoot.*;

public class Bgm {
    private static GreenfootSound current;

    public static void playLoop(String file, int volume) {
        stop();
        current = new GreenfootSound(file);
        current.setVolume(volume);
        current.playLoop();
    }

    public static void stop() {
        if (current != null) current.stop();
    }
}
