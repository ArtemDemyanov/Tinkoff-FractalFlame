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
        double a, b, d, e, c, f;

        do {
            a = random.nextDouble(-1, 1);
            b = random.nextDouble(-1, 1);
            d = random.nextDouble(-1, 1);
            e = random.nextDouble(-1, 1);
            c = random.nextDouble(-1, 1);
            f = random.nextDouble(-1, 1);
        } while (!IsValid(a, b, d, e));

        return new AffineCoefficient(a, b, d, e, c, f);
    }

    /**
     * Проверяет валидность комбинации коэффициентов на основе заданных математических условий.
     * Условия гарантируют, что преобразования не будут вызывать чрезмерного искажения или увеличения области.
     *
     * @param a коэффициент аффинного преобразования
     * @param b коэффициент аффинного преобразования
     * @param d коэффициент аффинного преобразования
     * @param e коэффициент аффинного преобразования
     * @return true, если набор коэффициентов валиден, иначе false.
     */
    private static  boolean IsValid(double a, double b, double d, double e) {
        return (Math.pow(a, 2) + Math.pow(d, 2) < 1) &&
            (Math.pow(b, 2) + Math.pow(e, 2) < 1) &&
            (Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(d, 2) + Math.pow(e, 2) < 1 + Math.pow((a * e - b * d), 2));
    }
}
