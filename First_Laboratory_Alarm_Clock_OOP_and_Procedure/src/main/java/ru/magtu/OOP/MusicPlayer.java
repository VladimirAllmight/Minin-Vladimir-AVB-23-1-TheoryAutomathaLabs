package ru.magtu.OOP;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayer {
    private List<String> playlist;
    private static final String MUSIC_DIRECTORY = "src/main/resources/music";
    private Clip clip;  // Для управления воспроизведением

    public MusicPlayer() {
        this.playlist = new ArrayList<>();
        loadTracksFromDirectory();
    }

    private void loadTracksFromDirectory() {
        Path dir = Paths.get(MUSIC_DIRECTORY);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path entry : stream) {
                String fileName = entry.getFileName().toString();
                if (fileName.endsWith(".wav") || fileName.endsWith(".au")) {
                    playlist.add(fileName);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке файлов: " + e.getMessage());
        }
    }

    public List<String> getPlaylist() {
        return playlist;
    }

    public void play(String fileName) {
        stop();  // Остановить предыдущее воспроизведение, если есть
        try {
            File audioFile = new File(MUSIC_DIRECTORY + "/" + fileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioStream);
            clip.start();

            System.out.println("Воспроизводится: " + fileName);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Ошибка воспроизведения: " + e.getMessage());
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
            System.out.println("Воспроизведение остановлено.");
        }
    }
}
