package sound;

import java.applet.Applet;
import java.applet.AudioClip;
import java.util.HashMap;

public class GameSound {

    public static GameSound instance;

    public static final String POINT = "point.wav";
    public static final String WING = "wing.wav";
    public static final String HIT = "hit.wav";
    public static final String DIE ="die.wav";
    public static final String SWOOSH ="swoosh.wav";
    public static final String MENU ="menu.wav";

    private HashMap<String, AudioClip> audioMap;

    public GameSound() {
        audioMap = new HashMap<>();
        loadAllAudio();
    }

    public static GameSound getInstance() {
        if (instance == null) {
            instance = new GameSound();
        }

        return instance;
    }

    public void loadAllAudio() {
        putAudio(POINT);
        putAudio(WING);
        putAudio(HIT);
        putAudio(DIE);
        putAudio(SWOOSH);
        putAudio(MENU);
    }

    public void stop() {
        getAudio(POINT).stop();
        getAudio(WING).stop();
        getAudio(HIT).stop();
        getAudio(DIE).stop();
        getAudio(SWOOSH).stop();
        getAudio(MENU).stop();
    }

    public void putAudio(String name) {
        AudioClip auClip = Applet.newAudioClip(GameSound.class.getResource(name));
        audioMap.put(name, auClip);
    }

    public AudioClip getAudio(String name) {
        return audioMap.get(name);
    }
}
