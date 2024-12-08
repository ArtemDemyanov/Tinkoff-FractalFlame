package backend.academy.models;

import java.util.Random;

/**
 * Запись, представляющая коэффициенты аффинного преобразования.
 * Предоставляет метод для генерации случайных наборов коэффициентов,
 * удовлетворяющих определённым условиям валидности.
 */
public record AffineCoefficient(double a, double b, double d, double e, double c, double f) {

    /**
     * Генерирует набор коэффициентов аффинного преобразования, который удовлетворяет условиям валидности.
     *
     * @param random экземпляр генератора случайных чисел для обеспечения разнообразия генерируемых коэффициентов.
     * @return новый экземпляр AffineCoefficient с случайно сгенерированными коэффициентами.
     */
    public static AffineCoefficient generate(Random random) {
        double a;
        double b;
        double c;
        double d;
        double e;
        double f;

        do {
            a = random.nextDouble(-1, 1);
            b = random.nextDouble(-1, 1);
            c = random.nextDouble(-1, 1);
            d = random.nextDouble(-1, 1);
            e = random.nextDouble(-1, 1);
            f = random.nextDouble(-1, 1);
        } while (!isValid(a, b, c, d));

        return new AffineCoefficient(a, b, c, d, e, f);
    }

    /**
     * Проверяет валидность комбинации коэффициентов на основе заданных математических условий.
     * Условия гарантируют, что преобразования не будут вызывать чрезмерного искажения или увеличения области.
     *
     * @param a коэффициент аффинного преобразования
     * @param b коэффициент аффинного преобразования
     * @param c коэффициент аффинного преобразования
     * @param d коэффициент аффинного преобразования
     * @return true, если набор коэффициентов валиден, иначе false.
     */
    private static  boolean isValid(double a, double b, double c, double d) {
        return (Math.pow(a, 2) + Math.pow(c, 2) < 1)
            && (Math.pow(b, 2) + Math.pow(d, 2) < 1)
            && (Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(c, 2) + Math.pow(d, 2) < 1 + Math.pow((a * d - b * c), 2));
    }
}
