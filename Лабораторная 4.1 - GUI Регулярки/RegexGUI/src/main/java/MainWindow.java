import Tasks.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    public MainWindow() {
        initializeWindow();
        createButtons();
    }

    private void initializeWindow() {
        setTitle("Select task");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void createButtons() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 5, 5)); // 5 строк, 1 столбец, отступы 5px

        // Создаем 5 кнопок
        JButton button1 = new JButton("Задача 1");
        JButton button2 = new JButton("Задача 2");
        JButton button3 = new JButton("Задача 3");
        JButton button4 = new JButton("Задача 4");
        JButton button5 = new JButton("Задача 5");

        // Добавляем обработчики событий (заглушки)
        button1.addActionListener(e -> new Thread(new Task1()).start() );

        button2.addActionListener(e -> new Thread(new Task2()).start() );

        button3.addActionListener(e -> new Thread(new Task3()).start() );

        button4.addActionListener(e -> new Thread(new Task4()).start() );

        button5.addActionListener(e -> new Thread(new Task5()).start() );

        // Добавляем кнопки на панель
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        panel.add(button5);

        // Добавляем панель на фрейм
        add(panel, BorderLayout.CENTER);
    }

    public void showWindow() {
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindow().showWindow();
        });
    }
}