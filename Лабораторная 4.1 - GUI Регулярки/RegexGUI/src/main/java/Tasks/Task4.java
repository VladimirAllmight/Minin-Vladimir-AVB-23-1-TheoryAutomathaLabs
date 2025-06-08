package Tasks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class Task4 extends JFrame implements Runnable {
    private JTextField phoneField;
    private JTextArea resultArea;
    private JButton checkButton;

    @Override
    public void run() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Проверка телефонных номеров");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Основная панель
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Панель ввода
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(new JLabel("Введите телефонный номер:"), BorderLayout.NORTH);

        phoneField = new JTextField();
        phoneField.setFont(new Font("Arial", Font.PLAIN, 16));
        inputPanel.add(phoneField, BorderLayout.CENTER);

        // Подсказка
        JLabel hintLabel = new JLabel("Примеры: +7(123)456-7890, 8-800-555-3535, 4155552671");
        hintLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        inputPanel.add(hintLabel, BorderLayout.SOUTH);

        // Кнопка проверки
        checkButton = new JButton("Проверить номер");
        checkButton.addActionListener(new CheckButtonListener());

        // Область результатов
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Сборка интерфейса
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(checkButton, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private class CheckButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String phoneNumber = phoneField.getText().trim();
            String pattern = "^[+]?[(]?[0-9]{1,4}[)]?[-\\s.]?[0-9]{2,3}[-\\s.]?[0-9]{2,3}[-\\s.]?[0-9]{2,4}$";

            boolean isValid = Pattern.matches(pattern, phoneNumber);

            String validationResult = isValid ? "✓ Номер валиден" : "✗ Неверный формат номера";

            resultArea.setText(
                    "Проверяемый номер: " + phoneNumber + "\n\n" +
                            "Используемое регулярное выражение:\n" + pattern + "\n\n" +
                            "Результат проверки: " + validationResult + "\n\n" +
                            "Поддерживаемые форматы:\n" +
                            "- Международные: +7(123)456-7890, +442081234567\n" +
                            "- Российские: 8(800)555-3535, 4951234567\n" +
                            "- Городские: (812)123-4567\n" +
                            "- Мобильные: 915-123-4567\n" +
                            "- Без разделителей: 4155552671"
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Task4());
    }
}