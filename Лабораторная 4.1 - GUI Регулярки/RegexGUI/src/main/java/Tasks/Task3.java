package Tasks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class Task3 extends JFrame implements Runnable {
    private JTextField inputField;
    private JComboBox<String> taskSelector;
    private JTextArea resultArea;
    private JLabel exampleLabel;

    @Override
    public void run() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Регулярные выражения - Задание 3");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Основная панель с темным фоном
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(240, 240, 240));

        // Панель выбора задачи с иконкой
        JPanel taskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        taskPanel.setBackground(new Color(240, 240, 240));


        taskSelector = new JComboBox<>(new String[]{
                "1) Без подцепочки 101",
                "2) Поровну 0 и 1 с ограничениями",
                "3) Нулей кратно 5, единиц четно"
        });
        taskSelector.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        taskPanel.add(new JLabel("Выберите задание:"));
        taskPanel.add(taskSelector);

        // Панель ввода с подсказкой
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBackground(new Color(240, 240, 240));

        JLabel inputLabel = new JLabel("Введите бинарную строку (0 и 1):");
        inputLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        inputPanel.add(inputLabel, BorderLayout.NORTH);

        inputField = new JTextField();
        inputField.setFont(new Font("Consolas", Font.PLAIN, 16));
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 150, 150)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        inputPanel.add(inputField, BorderLayout.CENTER);

        // Панель с примерами
        exampleLabel = new JLabel("Примеры: 000111, 010101");
        exampleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        exampleLabel.setForeground(Color.GRAY);
        inputPanel.add(exampleLabel, BorderLayout.SOUTH);

        // Кнопка проверки с иконкой
        JButton checkButton = new JButton(" Проверить ");
        checkButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        checkButton.setBackground(new Color(70, 130, 180));
        checkButton.setForeground(Color.WHITE);
        checkButton.setFocusPainted(false);
        checkButton.addActionListener(new CheckButtonListener());

        // Область результатов с вкладками
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        resultArea.setBackground(new Color(250, 250, 250));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Результат", new JScrollPane(resultArea));

        // Добавляем дополнительную вкладку с пояснениями
        JTextArea helpArea = new JTextArea();
        helpArea.setEditable(false);
        helpArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        helpArea.setText(getHelpText());
        tabbedPane.addTab("Справка", new JScrollPane(helpArea));

        // Сборка интерфейса
        mainPanel.add(taskPanel, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(checkButton, BorderLayout.SOUTH);
        mainPanel.add(tabbedPane, BorderLayout.EAST);

        // Обработчик изменения выбранного задания
        taskSelector.addActionListener(e -> updateExamples());

        add(mainPanel);
        updateExamples();
        setVisible(true);
    }

    private void updateExamples() {
        int taskIndex = taskSelector.getSelectedIndex();
        String examples = "";

        switch (taskIndex) {
            case 0: examples = "Примеры: 000, 111, 0011, 0101 (но не 101)"; break;
            case 1: examples = "Примеры: 0101, 001111, 1100 (но не 000111)"; break;
            case 2: examples = "Примеры: 000001111, 111100000, 0101010101"; break;
        }

        exampleLabel.setText(examples);
    }

    private String getHelpText() {
        return "Справка по заданиям:\n\n" +
                "1) Без подцепочки 101\n" +
                "   - Строка не должна содержать '101'\n" +
                "   - Регулярка: ^(?!.*101)[01]+$\n\n" +
                "2) Поровну 0 и 1 с ограничениями\n" +
                "   - Одинаковое число 0 и 1\n" +
                "   - Ни один префикс не имеет разницы >2\n" +
                "   - Регулярка: ^(?!(.*0){2,}.*1|.*1(.*0){2,}|(.*1){2,}.*0|.*0(.*1){2,})[01]+$\n\n" +
                "3) Нулей кратно 5, единиц четно\n" +
                "   - Число 0 делится на 5\n" +
                "   - Число 1 четное\n" +
                "   - Регулярка: ^(1*01*){10}$|^(1*01*){5}1*$|^1*$";
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
                    // 1) Без подцепочки 101
                    pattern = "^(?!.*101)[01]+$";
                    description = "Строка не должна содержать подцепочку '101'\n" +
                            "Допустимые: 000, 111, 0011, 0101\n" +
                            "Недопустимые: 101, 1101, 1010";
                    break;
                case 1:
                    // 2) Поровну 0 и 1 с ограничениями
                    pattern = "^(?!(.*0){2,}.*1|.*1(.*0){2,}|(.*1){2,}.*0|.*0(.*1){2,})[01]+$";
                    description = "Одинаковое число 0 и 1\n" +
                            "Ни один префикс не имеет разницы >2 между 0 и 1\n" +
                            "Допустимые: 0101, 001111, 1100\n" +
                            "Недопустимые: 000111, 111000, 001";
                    break;
                case 2:
                    // 3) Нулей кратно 5, единиц четно
                    // pattern = "^(1*01*){10}$|^(1*01*){5}1*$|^1*$";
                    pattern = "^(?=([^0]*0[^0]*0[^0]*0[^0]*0[^0]*0)*[^0]*$)(?=([^1]*1[^1]*1)*[^1]*$)[01]*$";

                        description = "Число нулей делится на 5\n" +
                            "Число единиц четное\n" +
                            "Допустимые: 000001111, 111100000, \"\" (пустая строка)\n" +
                            "Недопустимые: 00000, 1111, 010101";
                    break;
            }

            isValid = Pattern.matches(pattern, input);

            resultArea.setText(String.format(
                    "=== Задание ===\n%s\n\n" +
                            "=== Входная строка ===\n%s\n\n" +
                            "=== Регулярное выражение ===\n%s\n\n" +
                            "=== Описание условия ===\n%s\n\n" +
                            "=== Результат проверки ===\n%s",
                    taskSelector.getSelectedItem(),
                    input,
                    pattern,
                    description,
                    isValid ? "✔ Соответствует" : "✖ Не соответствует"
            ));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Task3());
    }
}