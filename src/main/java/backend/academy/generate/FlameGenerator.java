package backend.academy.generate;

import backend.academy.domain.Color;
import backend.academy.domain.Point;
import backend.academy.render.Renderer;
import backend.academy.transformation.AffineTransformation;
import backend.academy.transformation.Transformation;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Генератор фракталов, использующий аффинные и другие виды трансформаций.
 * Этот класс отвечает за генерацию фрактальных изображений на основе заданных трансформаций.
 */
public class FlameGenerator {

    private final List<AffineTransformation> affineTransformations;
    private final List<Transformation> transformations;

    /**
     * Конструктор создает генератор фракталов с указанными аффинными преобразованиями и дополнительными трансформациями.
     *
     * @param affineTransformations список аффинных преобразований
     * @param transformations список других видов трансформаций
     */
    public FlameGenerator(List<AffineTransformation> affineTransformations, List<Transformation> transformations) {
        this.affineTransformations = affineTransformations;
        this.transformations = transformations;
    }

    /**
     * Осуществляет процесс генерации фракталов, применяя последовательно выбранные трансформации к точкам.
     *
     * @param renderer объект рендерера, который отвечает за отрисовку точек на изображении.
     * @param iterations количество итераций генерации.
     */
    public void generate(Renderer renderer, int iterations) {
        Random random = ThreadLocalRandom.current();
        Point currentPoint = new Point(random.nextDouble() * 2 - 1, random.nextDouble() * 2 - 1);
        for (int i = 0; i < iterations; i++) {
            AffineTransformation affineTransformation = affineTransformations
                .get(random.nextInt(affineTransformations.size()));
            Transformation transformation = transformations.get(random.nextInt(transformations.size()));
            currentPoint = affineTransformation.transform(currentPoint);
            currentPoint = transformation.transform(currentPoint);
            renderer.renderPoint(currentPoint, getPointColor(currentPoint));
        }
    }

    /**
     * Вычисляет цвет точки на основе её координат, применяя специфические математические функции.
     *
     * @param point точка, для которой вычисляется цвет.
     * @return объект Color, содержащий значения RGB.
     */
    private Color getPointColor(Point point) {
        double r2 = point.x() * point.x() + point.y() * point.y();
        int r = (int) (255 * Math.abs(Math.sin(r2)));
        int g = (int) (255 * Math.abs(Math.sin(point.x() * Math.PI)));
        int b = (int) (255 * Math.abs(Math.sin(point.y() * Math.PI)));

        return new Color(r, g, b);
    }
}
