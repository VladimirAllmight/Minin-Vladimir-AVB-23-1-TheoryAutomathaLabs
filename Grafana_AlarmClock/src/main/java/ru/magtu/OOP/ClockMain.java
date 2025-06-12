package ru.magtu.OOP;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class ClockMain extends JFrame {

    private LocalTime alarmTime = LocalTime.of(0, 0);
    private DefaultListModel<String> alarmListModel = new DefaultListModel<>();
    private String selectedTrack = "";


    public ClockMain() {
        setTitle("Clock application");
        setSize(700, 520);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);

        setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());

        BackgroundPanel backgroundPanel = new BackgroundPanel();
        setContentPane(backgroundPanel);

        // ===== Панель времени =====
        AcrylicPanel timePanel = new AcrylicPanel();
        timePanel.setBounds(20, 20, 300, 120);
        timePanel.setLayout(null);

        JLabel timeLabel = new JLabel();
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 32));
        timeLabel.setBounds(80, 10, 200, 40);
        timePanel.add(timeLabel);

        JLabel dateLabel = new JLabel();
        dateLabel.setForeground(Color.LIGHT_GRAY);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        dateLabel.setBounds(50, 60, 220, 30);
        timePanel.add(dateLabel);

        backgroundPanel.add(timePanel);

        // ===== Панель кнопок =====
        AcrylicPanel buttonsPanel = new AcrylicPanel();
        buttonsPanel.setBounds(20, 160, 300, 280);
        buttonsPanel.setLayout(new BorderLayout(10, 10));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // ===== Отображение установленного времени =====
        AcrylicPanel alarmTimePanel = new AcrylicPanel();
        alarmTimePanel.setLayout(new BorderLayout());
        alarmTimePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel alarmTimeLabel = new JLabel(alarmTime.format(DateTimeFormatter.ofPattern("HH:mm")), SwingConstants.CENTER);
        alarmTimeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        alarmTimeLabel.setForeground(Color.WHITE);
        alarmTimePanel.add(alarmTimeLabel, BorderLayout.CENTER);
        buttonsPanel.add(alarmTimePanel, BorderLayout.NORTH);

        // ===== Кнопки =====
        JPanel buttonsGridPanel = new JPanel();
        buttonsGridPanel.setOpaque(false);
        buttonsGridPanel.setLayout(new BoxLayout(buttonsGridPanel, BoxLayout.Y_AXIS));

        JButton button1 = new JButton("H+");
        JButton button2 = new JButton("H-");
        JButton button3 = new JButton("M+");
        JButton button4 = new JButton("M-");
        JButton button5 = new JButton("Set Alarm");

        // Применяем стили
        styleButton(button1);
        styleButton(button2);
        styleButton(button3);
        styleButton(button4);
        styleButton(button5);

        button5.addActionListener(e -> {
            String alarmStr = alarmTime.format(DateTimeFormatter.ofPattern("HH:mm"));
            if (!alarmListModel.contains(alarmStr)) {
                alarmListModel.addElement(alarmStr);
                DBLogger.log("INFO", "Alarm set to: " + alarmTime);
            } else {
                JOptionPane.showMessageDialog(this, "Будильник на " + alarmStr + " уже добавлен.", "Инфо", JOptionPane.INFORMATION_MESSAGE);
                DBLogger.log("ERROR", "Alarm already set up");
            }
        });

        // Музыка
        MusicPlayer player = new MusicPlayer();
        JComboBox<String> musicBox = new JComboBox<>();
        musicBox.setMaximumSize(new Dimension(160, 30));
        musicBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Подгружаем треки в ComboBox
        for (String track : player.getPlaylist()) {
            musicBox.addItem(track);
        }
        musicBox.addActionListener(e -> {
            selectedTrack = (String) musicBox.getSelectedItem();
            DBLogger.log("INFO", "Set up music");
        });



        // Обновление метки
        Runnable updateAlarmLabel = () -> alarmTimeLabel.setText(alarmTime.format(DateTimeFormatter.ofPattern("HH:mm")));

        // Обработчики кнопок
        button1.addActionListener(e -> {
            alarmTime = alarmTime.plusHours(1);
            updateAlarmLabel.run();
            DBLogger.log("INFO", "The plus hour button is pressed");
        });
        button2.addActionListener(e -> {
            alarmTime = alarmTime.minusHours(1);
            updateAlarmLabel.run();
            DBLogger.log("INFO", "The minus hour button is pressed");
        });
        button3.addActionListener(e -> {
            alarmTime = alarmTime.plusMinutes(1);
            updateAlarmLabel.run();
            DBLogger.log("INFO", "The plus minute button is pressed");
        });
        button4.addActionListener(e -> {
            alarmTime = alarmTime.minusMinutes(1);
            updateAlarmLabel.run();
            DBLogger.log("INFO", "The minus minute button is pressed");
        });

        // Добавление компонентов
        buttonsGridPanel.add(Box.createVerticalStrut(5));
        buttonsGridPanel.add(musicBox);
        buttonsGridPanel.add(Box.createVerticalStrut(10));
        buttonsGridPanel.add(button1);
        buttonsGridPanel.add(Box.createVerticalStrut(10));
        buttonsGridPanel.add(button2);
        buttonsGridPanel.add(Box.createVerticalStrut(10));
        buttonsGridPanel.add(button3);
        buttonsGridPanel.add(Box.createVerticalStrut(10));
        buttonsGridPanel.add(button4);
        buttonsGridPanel.add(Box.createVerticalStrut(10));
        buttonsGridPanel.add(button5);
        buttonsGridPanel.add(Box.createVerticalStrut(10));

        buttonsPanel.add(buttonsGridPanel, BorderLayout.CENTER);
        backgroundPanel.add(buttonsPanel);

        // ===== Панель будильников =====
        AcrylicPanel alarmsPanel = new AcrylicPanel();
        alarmsPanel.setBounds(340, 20, 330, 400);
        alarmsPanel.setLayout(null);

        JLabel alarmsTitle = new JLabel("My Alarms:");
        alarmsTitle.setBounds(20, 10, 200, 25);
        alarmsTitle.setForeground(Color.WHITE);
        alarmsTitle.setFont(new Font("Arial", Font.BOLD, 18));
        alarmsPanel.add(alarmsTitle);

        JList<String> alarmList = new JList<>(alarmListModel);
        alarmList.setFont(new Font("Arial", Font.PLAIN, 16));
        alarmList.setBackground(new Color(40, 40, 40));
        alarmList.setForeground(Color.CYAN);

        JScrollPane scrollPane = new JScrollPane(alarmList);
        scrollPane.setBounds(20, 40, 290, 330);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        alarmsPanel.add(scrollPane);

        backgroundPanel.add(alarmsPanel);

        // ===== Таймер обновления времени =====
        Timer timer = new Timer(1000, e -> {
            updateDateTime(timeLabel, dateLabel);

            LocalTime now = LocalTime.now().withSecond(0).withNano(0); // округляем
            for (int i = 0; i < alarmListModel.size(); i++) {
                String timeStr = alarmListModel.get(i);
                LocalTime alarm = LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm"));
                if (now.equals(alarm)) {
                    // Удалить сработавший будильник
                    alarmListModel.removeElementAt(i);

                    // Проиграть музыку
                    player.play(selectedTrack);
                    DBLogger.log("INFO", "Alarm played");

                    // Показать сообщение
                    JOptionPane.showMessageDialog(this,
                            "Будильник на " + timeStr + " сработал!",
                            "Будильник", JOptionPane.INFORMATION_MESSAGE);
                    DBLogger.log("INFO", "Massage is showed");
                    // Остановить музыку
                    player.stop();

                    break; // важен, чтобы избежать ConcurrentModificationException
                }
            }
        });

        timer.start();

        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(70, 70, 70));
        button.setForeground(Color.CYAN);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(160, 35));
        button.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 1));
    }
    private void updateDateTime(JLabel timeLabel, JLabel dateLabel) {
        LocalTime currentTime = LocalTime.now();
        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        timeLabel.setText(currentTime.format(timeFormatter));

        String dayOfWeek = currentDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        dateLabel.setText(dayOfWeek + ", " + currentDate.format(dateFormatter));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClockMain::new);
    }

    // ===== Фоновая панель =====
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                backgroundImage = ImageIO.read(getClass().getResourceAsStream("/background/image1.png"));
            } catch (IOException | IllegalArgumentException e) {
                e.printStackTrace();
            }
            setLayout(null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
    // ===== Акриловая панель =====
    class AcrylicPanel extends JPanel {
        public AcrylicPanel() {
            setOpaque(false);
            setBackground(new Color(50, 50, 50, 150));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setComposite(AlphaComposite.SrcOver.derive(0.7f));
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}
