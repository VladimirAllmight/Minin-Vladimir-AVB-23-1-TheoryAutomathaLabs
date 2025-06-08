package Tasks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class Task5 extends JFrame implements Runnable {
    private JTextField inputField;
    private JTextArea resultArea;

    @Override
    public void run() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Регулярные выражения - Задание 5");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Панель ввода
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(new JLabel("Введите текст объявления о работе:"), BorderLayout.NORTH);
        inputField = new JTextField();
        inputPanel.add(inputField, BorderLayout.CENTER);

        // Кнопка проверки
        JButton checkButton = new JButton("Проверить");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(checkButton);

        // Область результатов
        resultArea = new JTextArea(10, 50);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Сборка интерфейса
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        checkButton.addActionListener(new CheckButtonListener());
    }

    private class CheckButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = inputField.getText();
            boolean isValid;

            String pattern = "(?i)\\b(?:зарплата|оплата|з\\/п|зп)?\\s*[:\\-]?\\s*(?:от\\s*)?" +
                    "(?:\\$|₽|руб\\.?|usd|eur)?\\s*\\d{1,3}(?:[ \\u00A0]?\\d{3})*" +
                    "(?:[.,]\\d+)?\\s*(?:₽|руб\\.?|usd|eur|\\$)?\\s*(?:в\\s+)?" +
                    "(?:час|часа|часов|день|неделя|неделю|недели|месяц|мес|год)?\\b";

            String description = "Регулярное выражение ищет шаблоны зарплат с указанием валюты, суммы и периода (час, месяц и т.п.)";

            isValid = Pattern.compile(pattern).matcher(input).find();

            resultArea.setText(String.format(
                    "Введённый текст: %s\n\n" +
                            "Регулярное выражение:\n%s\n\n" +
                            "Описание:\n%s\n\n" +
                            "Результат: %s",
                    input,
                    pattern,
                    description,
                    isValid ? "Обнаружена зарплата" : "Зарплата не обнаружена"
            ));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Task5());
    }
}
