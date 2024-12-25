package backend.academy.config;

import backend.academy.transformation.HeartTransformation;
import backend.academy.transformation.PolarTransformation;
import backend.academy.transformation.SinusoidalTransformation;
import backend.academy.transformation.SphericalTransformation;
import backend.academy.transformation.SwirlTransformation;
import backend.academy.transformation.Transformation;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Класс для обработки пользовательского ввода.
 */
public final class InputHandler {
    private static final PrintStream OUT = System.out;
    private static final String SPHERICAL = "spherical";
    private static final String HEART = "heart";
    private static final String POLAR = "polar";
    private static final String SWIRL = "swirl";
    private static final String SINUSOIDAL = "sinusoidal";
    private static final List<String> DEFAULT_TRANSFORMATIONS = List.of(SPHERICAL, HEART, POLAR);

    private static final String INVALID_INPUT_MSG = "Некорректный ввод. Используется значение по умолчанию: ";
    private static final String UNKNOWN_TRANSFORMATION_MSG = "Неизвестная трансформация: ";

    private InputHandler() {
        // Предотвращение создания экземпляра утилитного класса
    }

    /**
     * Читает целочисленное значение из сканера или использует значение по умолчанию.
     *
     * @param scanner сканер для чтения ввода
     * @param defaultValue значение по умолчанию
     * @return считанное значение или значение по умолчанию, если ввод некорректен
     */
    public static int getInt(Scanner scanner, int defaultValue) {
        String input = scanner.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            OUT.println(INVALID_INPUT_MSG + defaultValue);
            return defaultValue;
        }
    }

    /**
     * Читает логическое значение из сканера или использует значение по умолчанию.
     *
     * @param scanner сканер для чтения ввода
     * @param defaultValue значение по умолчанию
     * @return считанное значение или значение по умолчанию, если ввод некорректен
     */
    public static boolean getBoolean(Scanner scanner, boolean defaultValue) {
        String input = scanner.nextLine();
        return input.isEmpty() ? defaultValue : Boolean.parseBoolean(input);
    }

    /**
     * Читает значение с плавающей точкой из сканера или использует значение по умолчанию.
     *
     * @param scanner сканер для чтения ввода
     * @param defaultValue значение по умолчанию
     * @return считанное значение или значение по умолчанию, если ввод некорректен
     */
    public static double getDouble(Scanner scanner, double defaultValue) {
        String input = scanner.nextLine();
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            OUT.println(INVALID_INPUT_MSG + defaultValue);
            return defaultValue;
        }
    }

    /**
     * Получает список трансформаций из строки, разделенной запятыми.
     *
     * @param scanner сканер для чтения ввода
     * @return список трансформаций
     */
    public static List<String> getTransformations(Scanner scanner) {
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            return new ArrayList<>(DEFAULT_TRANSFORMATIONS);
        }
        String[] transformations = input.split(",");
        List<String> result = new ArrayList<>();
        for (String t : transformations) {
            result.add(t.trim());
        }
        return result;
    }

    /**
     * Создает список объектов трансформации на основе переданных названий.
     *
     * @param names список названий трансформаций
     * @return список объектов трансформаций
     */
    public static List<Transformation> createTransformations(List<String> names) {
        List<Transformation> transformations = new ArrayList<>();
        for (String name : names) {
            switch (name.toLowerCase()) {
                case SPHERICAL:
                    transformations.add(new SphericalTransformation());
                    continue;
                case HEART:
                    transformations.add(new HeartTransformation());
                    continue;
                case POLAR:
                    transformations.add(new PolarTransformation());
                    continue;
                case SWIRL:
                    transformations.add(new SwirlTransformation());
                    continue;
                case SINUSOIDAL:
                    transformations.add(new SinusoidalTransformation());
                    break;
                default:
                    OUT.println(UNKNOWN_TRANSFORMATION_MSG + name);
                    throw new IllegalArgumentException(UNKNOWN_TRANSFORMATION_MSG + name);
            }
        }
        return transformations;
    }
}
