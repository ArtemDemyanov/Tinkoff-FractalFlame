package backend.academy.config;

import backend.academy.transformation.HeartTransformation;
import backend.academy.transformation.PolarTransformation;
import backend.academy.transformation.SinusoidalTransformation;
import backend.academy.transformation.SphericalTransformation;
import backend.academy.transformation.SwirlTransformation;
import backend.academy.transformation.Transformation;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class InputHandler {

    /**
     * Читает из сканера целое число или возвращает значение по умолчанию.
     *
     * @param scanner сканер для чтения ввода.
     * @param defaultValue значение по умолчанию.
     * @return считанное число или значение по умолчанию, если ввод был пустым.
     */
    public static int getInt(Scanner scanner, int defaultValue) {
        String input = scanner.nextLine();
        return input.isEmpty() ? defaultValue : Integer.parseInt(input);
    }

    /**
     * Читает из сканера булево значение или возвращает значение по умолчанию.
     *
     * @param scanner сканер для чтения ввода.
     * @param defaultValue значение по умолчанию.
     * @return считанное булево значение или значение по умолчанию, если ввод был пустым.
     */
    public static boolean getBoolean(Scanner scanner, boolean defaultValue) {
        String input = scanner.nextLine();
        return input.isEmpty() ? defaultValue : Boolean.parseBoolean(input);
    }

    /**
     * Читает из сканера число с плавающей точкой или возвращает значение по умолчанию при ошибке формата.
     *
     * @param scanner сканер для чтения ввода.
     * @param defaultValue значение по умолчанию.
     * @return считанное число или значение по умолчанию, если ввод некорректен.
     */
    public static double getDouble(Scanner scanner, double defaultValue) {
        String input = scanner.nextLine();
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод. Используется значение по умолчанию: " + defaultValue);
            return defaultValue;
        }
    }

    /**
     * Получает список названий трансформаций из строки, разделенной запятыми.
     *
     * @param scanner сканер для чтения ввода.
     * @return список названий трансформаций.
     */
    public static List<String> getTransformations(Scanner scanner) {
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            return List.of("spherical", "heart", "polar", "swirl", "sinusoidal");
        }
        String[] transformations = input.split(",");
        List<String> result = new ArrayList<>();
        for (String t : transformations) {
            result.add(t.trim());
        }
        return result;
    }

    /**
     * Создает объекты трансформаций на основе списка названий.
     *
     * @param names список названий трансформаций.
     * @return список объектов трансформаций.
     */
    public static List<Transformation> createTransformations(List<String> names) {
        List<Transformation> transformations = new ArrayList<>();
        for (String name : names) {
            switch (name.toLowerCase()) {
                case "spherical" -> transformations.add(new SphericalTransformation());
                case "heart" -> transformations.add(new HeartTransformation());
                case "polar" -> transformations.add(new PolarTransformation());
                case "swirl" -> transformations.add(new SwirlTransformation());
                case "sinusoidal" -> transformations.add(new SinusoidalTransformation());
                default -> throw new IllegalArgumentException("Неизвестная трансформация: " + name);
            }
        }
        return transformations;
    }
}
