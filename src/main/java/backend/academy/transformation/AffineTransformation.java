package backend.academy.transformation;

import backend.academy.domain.Point;
import backend.academy.models.AffineCoefficient;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Класс для выполнения аффинного преобразования точек.
 * Использует параметры аффинного преобразования, генерируемые случайным образом.
 */
public class AffineTransformation implements Transformation {

    private final AffineCoefficient affineCoefficient;

    /**
     * Конструктор, инициализирующий аффинное преобразование с случайно сгенерированными коэффициентами.
     */
    public AffineTransformation() {
        this.affineCoefficient = AffineCoefficient.generate(ThreadLocalRandom.current());
    }

    /**
     * Применяет аффинное преобразование к заданной точке.
     *
     * @param point Точка, к которой применяется преобразование.
     * @return Новая точка, результат аффинного преобразования.
     */
    @Override
    public Point transform(Point point) {
        double x = point.x() * affineCoefficient.a() + point.y() * affineCoefficient.b() + affineCoefficient.c();
        double y = point.x() * affineCoefficient.d() + point.y() * affineCoefficient.e() + affineCoefficient.f();
        return new Point(x, y);
    }
}
