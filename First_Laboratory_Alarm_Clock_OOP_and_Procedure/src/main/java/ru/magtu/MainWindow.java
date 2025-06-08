package ru.magtu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import ru.magtu.OOP.ClockMain;
import ru.magtu.procedure.Main;

public class MainWindow extends JFrame {
    public void showMainWindow() {
        setTitle("Hello!");
        setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(340, 175);

        // Установка красивого фона
        getContentPane().setBackground(new Color(240, 240, 240));

        // Создаем панель для кнопок с отступами
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(240, 240, 240));

        // Создаем стилизованные кнопки
        JButton button1 = createStyledButton("Показать ООП реализацию");
        JButton button2 = createStyledButton("Показать проц. реализацию");

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClockMain();
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Main();
            }
        });

        buttonPanel.add(button1);
        buttonPanel.add(button2);

        // Добавляем панель с кнопками в основное окно
        add(buttonPanel, BorderLayout.CENTER);

        // Центрируем окно на экране
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                if (!isOpaque()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Рисуем скругленный прямоугольник
                    g2.setColor(getBackground());
                    g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));

                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };

        // Стилизация кнопки
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 130, 180)); // SteelBlue
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(200, 40));

        // Эффект при наведении
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(65, 105, 225)); // RoyalBlue
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180)); // SteelBlue
            }
        });

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow().showMainWindow());
    }
}

//hello