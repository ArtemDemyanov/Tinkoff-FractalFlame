package backend.academy.transformation;

import backend.academy.domain.Point;

/**
 * Класс трансформации в полярные координаты.
 * Эта трансформация переводит декартовы координаты точки в полярные координаты.
 */
public class PolarTransformation implements Transformation {

    /**
     * Преобразует точку из декартовых координат в полярные координаты.
     * В результате трансформации координата X становится углом в радианах, делённым на π,
     * а координата Y становится радиусом минус один.
     *
     * @param p Исходная точка в декартовых координатах.
     * @return Новая точка, представленная в полярных координатах.
     */
    @Override
    public Point transform(Point p) {
        double r = Math.sqrt(p.x() * p.x() + p.y() * p.y());
        double theta = Math.atan2(p.y(), p.x());
        return new Point(theta / Math.PI, r - 1);
    }
}
