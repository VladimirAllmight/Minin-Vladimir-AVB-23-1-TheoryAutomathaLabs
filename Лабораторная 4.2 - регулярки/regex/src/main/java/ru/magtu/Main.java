package ru.magtu;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Программа для проверки строк по регулярным выражениям");

        while (true) {
            printMenu();
            int choice = getIntInput("Выберите действие: ");

            switch (choice) {
                case 1 -> checkPredefinedPatterns();
                case 2 -> checkCustomPattern();
                case 3 -> {
                    System.out.println("Выход из программы");
                    return;
                }
                default -> System.out.println("Неверный выбор, попробуйте снова");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\nМеню:");
        System.out.println("1. Проверить по предопределенным шаблонам");
        System.out.println("2. Проверить по пользовательскому шаблону");
        System.out.println("3. Выход");
    }

    private static void checkPredefinedPatterns() {
        String input = getValidInput();
        if (input == null) return;

        System.out.println("\nРезультаты проверки:");
        System.out.println("1. 0*1*: " + checkPattern1(input));
        System.out.println("2. (0+1)01: " + checkPattern2(input));
        System.out.println("3. 00(0+1)*: " + checkPattern3(input));
    }

    private static void checkCustomPattern() {
        String input = getValidInput();
        if (input == null) return;

        System.out.println("Введите регулярное выражение для проверки:");
        String regex = scanner.nextLine();

        try {
            boolean matches = Pattern.matches(regex, input);
            System.out.println("Строка " + (matches ? "соответствует" : "не соответствует") +
                    " регулярному выражению: " + regex);
        } catch (PatternSyntaxException e) {
            System.out.println("Ошибка в регулярном выражении: " + e.getMessage());
        }
    }

    private static String getValidInput() {
        while (true) {
            System.out.println("\nВведите последовательность (только 0 и 1), или 'q' для выхода:");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                return null;
            }

            if (input.isEmpty()) {
                System.out.println("Ошибка: введена пустая строка");
                continue;
            }

            if (!input.matches("[01]+")) {
                System.out.println("Ошибка: ввод содержит недопустимые символы (разрешены только 0 и 1)");
                continue;
            }

            return input;
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число");
            }
        }
    }

    // Методы для проверки каждого шаблона
    private static String checkPattern1(String input) {
        return Pattern.matches("0*1*", input) ? "Соответствует" : "Не соответствует";
    }

    private static String checkPattern2(String input) {
        return Pattern.matches("[01]01", input) ? "Соответствует" : "Не соответствует";
    }

    private static String checkPattern3(String input) {
        return Pattern.matches("00[01]*", input) ? "Соответствует" : "Не соответствует";
    }
}