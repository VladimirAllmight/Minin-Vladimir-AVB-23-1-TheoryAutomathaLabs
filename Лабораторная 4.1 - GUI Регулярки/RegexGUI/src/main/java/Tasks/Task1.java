package Tasks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class Task1 extends JFrame implements Runnable {
    private JTextField inputField;
    private JComboBox<RegexTask> taskSelector;
    private JTextArea resultArea;

    @Override
    public void run() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Регулярные выражения - Задание 1");
        setSize(700, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Основная панель
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Панель выбора задачи
        JPanel taskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        taskSelector = new JComboBox<>(RegexTask.values());
        taskPanel.add(new JLabel("Выберите задание:"));
        taskPanel.add(taskSelector);

        // Панель ввода строки
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(new JLabel("Введите строку для проверки:"), BorderLayout.NORTH);
        inputField = new JTextField();
        inputPanel.add(inputField, BorderLayout.CENTER);

        // Кнопка проверки
        JButton checkButton = new JButton("Проверить");
        checkButton.addActionListener(new CheckButtonListener());

        // Панель с кнопкой и вводом
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(inputPanel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(checkButton);

        // Область результатов
        resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Добавление всех компонентов
        mainPanel.add(taskPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private class CheckButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = inputField.getText();
            RegexTask selectedTask = (RegexTask) taskSelector.getSelectedItem();
            if (selectedTask == null) return;

            boolean isValid = Pattern.matches(selectedTask.pattern, input);

            resultArea.setText(String.format(
                    "Задание: %s\n\n" +
                            "Проверяемая строка: %s\n" +
                            "Регулярное выражение: %s\n" +
                            "Описание: %s\n\n" +
                            "Результат: %s",
                    selectedTask.label,
                    input,
                    selectedTask.pattern,
                    selectedTask.description,
                    isValid ? "Соответствует" : "Не соответствует"
            ));
        }
    }

    private enum RegexTask {
        TASK1("1) {a,b,c} с хотя бы одним 'a' и 'b'", "^(?=.*a)(?=.*b)[abc]+$", "Строка должна содержать хотя бы один 'a' и хотя бы один 'b'"),
        TASK2("2) 0/1 где 10-й справа символ = 1", "^.*1.{9}$", "10-й символ с конца должен быть '1' (строка должна быть длиной ≥10)"),
        TASK3("3) 0/1 не более одной пары последовательных 1", "^(?!.*11.*11)[01]*$", "Строка может содержать не более одной пары последовательных '1'");

        final String label;
        final String pattern;
        final String description;

        RegexTask(String label, String pattern, String description) {
            this.label = label;
            this.pattern = pattern;
            this.description = description;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Task1());
    }
}
