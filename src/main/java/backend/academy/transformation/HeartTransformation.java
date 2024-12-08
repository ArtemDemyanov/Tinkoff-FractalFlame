package backend.academy.transformation;

import backend.academy.domain.Point;

/**
 * Класс, реализующий трансформацию "Heart".
 * Эта трансформация преобразует точки по формуле сердечной кривой.
 */
public class HeartTransformation implements Transformation {

    /**
     * Преобразует точку с использованием сердечной кривой.
     * Формула преобразования включает в себя вычисления на основе полярных координат.
     *
     * @param p Исходная точка, которую необходимо преобразовать.
     * @return Новая точка после применения сердечной трансформации.
     */
    @Override
    public Point transform(Point p) {
        double r = Math.sqrt(p.x() * p.x() + p.y() * p.y());
        double theta = Math.atan2(p.y(), p.x());
        return new Point(r * Math.sin(theta * r), -r * Math.cos(theta * r));
    }
}
