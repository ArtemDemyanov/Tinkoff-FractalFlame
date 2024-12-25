package backend.academy.generate;

import backend.academy.config.InputConfig;
import backend.academy.render.Renderer;
import backend.academy.transformation.Transformation;
import java.util.List;

/**
 * Класс для однопоточной генерации фрактальных изображений.
 * Этот класс расширяет абстрактный генератор и реализует однопоточное выполнение генерации.
 */
public class SingleThreadedGenerator extends AbstractFlameGenerator {

    /**
     * Конструктор, инициализирующий генератор с заданными параметрами конфигурации, списком трансформаций и рендерером.
     *
     * @param config конфигурация генератора, содержащая параметры изображения и выполнения.
     * @param transformations список трансформаций, применяемых при генерации.
     * @param renderer рендерер, используемый для отрисовки результатов генерации.
     */
    public SingleThreadedGenerator(InputConfig config, List<Transformation> transformations, Renderer renderer) {
        super(config, transformations, renderer);
    }

    /**
     * Выполняет однопоточную генерацию фракталов.
     * Метод последовательно генерирует каждый сэмпл изображения в соответствии с заданным количеством сэмплов.
     */
    @Override
    public void generate() {
        for (int i = 0; i < config.samples(); i++) {
            generateFlameSample(config.iterations());
        }
    }
}
