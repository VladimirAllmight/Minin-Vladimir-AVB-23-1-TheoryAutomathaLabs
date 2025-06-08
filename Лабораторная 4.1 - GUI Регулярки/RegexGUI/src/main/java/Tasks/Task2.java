package Tasks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class Task2 extends JFrame implements Runnable {
    private JTextField inputField;
    private JComboBox<String> taskSelector;
    private JTextArea resultArea;

    @Override
    public void run() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Регулярные выражения - Задание 2");
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Основная панель с вертикальной компоновкой
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Панель выбора задачи
        JPanel taskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        taskSelector = new JComboBox<>(new String[]{
                "а) Каждая пара 00 перед парой 11",
                "б) Число нулей кратно 5"
        });
        taskPanel.add(new JLabel("Выберите задание:"));
        taskPanel.add(taskSelector);

        // Панель ввода
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(new JLabel("Введите строку из 0 и 1 для проверки:"), BorderLayout.NORTH);
        inputField = new JTextField();
        inputPanel.add(inputField, BorderLayout.CENTER);

        // Кнопка проверки
        JButton checkButton = new JButton("Проверить");
        checkButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        checkButton.addActionListener(new CheckButtonListener());

        // Область результатов
        resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Сборка интерфейса
        mainPanel.add(taskPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(inputPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(checkButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(scrollPane);

        add(mainPanel);
        setVisible(true);
    }

    private class CheckButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = inputField.getText();
            int taskIndex = taskSelector.getSelectedIndex();
            boolean isValid = false;
            String pattern = "";
            String description = "";

            switch (taskIndex) {
                case 0:
                    // а) Каждая пара 00 перед парой 11
                    pattern = "^(1*01*|0+1+)*$";
                    description = "Все пары '00' должны встречаться до любой пары '11'\n" +
                            "Примеры допустимых: 0011, 000111, 010101, 001011\n" +
                            "Недопустимые: 1100, 0110, 1001";
                    break;
                case 1:
                    // б) Число нулей кратно 5
                    pattern = "^(1*01*){5}$|^1*$";
                    description = "Количество нулей в строке должно быть кратно 5\n" +
                            "Примеры: '11111' (0 нулей), '00000', '1010101010'";
                    break;
            }

            isValid = Pattern.matches(pattern, input);

            resultArea.setText(String.format(
                    "Задание: %s\n\n" +
                            "Проверяемая строка: %s\n" +
                            "Регулярное выражение: %s\n" +
                            "Описание: %s\n\n" +
                            "Результат: %s",
                    taskSelector.getSelectedItem(),
                    input,
                    pattern,
                    description,
                    isValid ? "Соответствует" : "Не соответствует"
            ));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Task2());
    }
}
