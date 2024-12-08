
package backend.academy.generate;

import backend.academy.config.InputConfig;
import backend.academy.render.Renderer;
import backend.academy.transformation.AffineTransformation;
import backend.academy.transformation.Transformation;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Абстрактный класс для генераторов фрактального пламени.
 * Служит основой для создания конкретных реализаций генераторов.
 */
public abstract class AbstractFlameGenerator {

    protected final InputConfig config;
    protected final FlameGenerator flameGenerator;
    protected final Renderer renderer;

    /**
     * Конструктор создаёт экземпляр генератора фракталов.
     *
     * @param config Конфигурация генерации фракталов.
     * @param transformations Список трансформаций для применения в процессе генерации.
     * @param renderer Рендерер для отображения результата.
     */
    public AbstractFlameGenerator(InputConfig config, List<Transformation> transformations, Renderer renderer) {
        this.config = config;

        List<AffineTransformation> affineTransformations = IntStream.range(0, config.affineTransformationsCount())
            .mapToObj(x -> new AffineTransformation())
            .collect(Collectors.toList());

        this.flameGenerator = new FlameGenerator(affineTransformations, transformations);
        this.renderer = renderer;
    }

    /**
     * Генерирует образец фрактала.
     * Вызывает метод генерации внутри вложенного объекта генератора.
     */
    protected void generateFlameSample() {
        flameGenerator.generate(renderer, config.iterations());
    }

    /**
     * Абстрактный метод для генерации фрактала.
     * Должен быть реализован в подклассах.
     *
     * @throws InterruptedException в случае ошибок в многопоточной обработке.
     */
    protected abstract void generate() throws InterruptedException;
}
