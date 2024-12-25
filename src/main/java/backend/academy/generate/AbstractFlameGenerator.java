package backend.academy.generate;

import backend.academy.config.InputConfig;
import backend.academy.domain.PixelColor;
import backend.academy.domain.Point;
import backend.academy.render.Renderer;
import backend.academy.transformation.AffineTransformation;
import backend.academy.transformation.Transformation;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Абстрактный класс для генераторов фрактального пламени.
 * Служит основой для создания конкретных реализаций генераторов.
 */
public abstract class AbstractFlameGenerator {

    protected final InputConfig config;
    protected final Renderer renderer;
    protected final List<AffineTransformation> affineTransformations;
    protected final List<Transformation> transformations;
    /**
     * Конструктор создаёт экземпляр генератора фракталов.
     *
     * @param config Конфигурация генерации фракталов.
     * @param transformations Список трансформаций для применения в процессе генерации.
     * @param renderer Рендерер для отображения результата.
     */
    public AbstractFlameGenerator(InputConfig config, List<Transformation> transformations, Renderer renderer) {
        this.config = config;
        this.renderer = renderer;
        this.transformations = transformations;

        this.affineTransformations = IntStream.range(0, config.affineTransformations())
            .mapToObj(x -> new AffineTransformation())
            .collect(Collectors.toList());
    }

    /**
     * Генерирует образец фрактала.
     * Вызывает метод генерации внутри вложенного объекта генератора.
     *
     * @param iterations количество итераций.
     */
    protected void generateFlameSample(int iterations) {
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
    private PixelColor getPointColor(Point point) {
        double r2 = point.x() * point.x() + point.y() * point.y();
        int r = (int) (255 * Math.abs(Math.sin(r2)));
        int g = (int) (255 * Math.abs(Math.sin(point.x() * Math.PI)));
        int b = (int) (255 * Math.abs(Math.sin(point.y() * Math.PI)));

        return new PixelColor(r, g, b);
    }

    /**
     * Абстрактный метод для генерации фрактала.
     * Должен быть реализован в подклассах.
     *
     * @throws InterruptedException в случае ошибок в многопоточной обработке.
     */
    public abstract void generate() throws InterruptedException;
}
