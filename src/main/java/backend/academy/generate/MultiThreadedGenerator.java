package backend.academy.generate;

import backend.academy.config.InputConfig;
import backend.academy.render.Renderer;
import backend.academy.transformation.Transformation;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Класс для многопоточной генерации фрактальных изображений.
 * Этот класс расширяет абстрактный генератор и реализует многопоточное выполнение генерации.
 */
public class MultiThreadedGenerator extends AbstractFlameGenerator {

    private final int threadsCount = config.threads();

    /**
     * Конструктор, который инициализирует генератор с заданными параметрами конфигурации и рендерером.
     *
     * @param config конфигурация генератора, содержащая параметры изображения и выполнения.
     * @param transformations список трансформаций, применяемых при генерации.
     * @param renderer рендерер, используемый для отрисовки результатов генерации.
     */
    public MultiThreadedGenerator(InputConfig config, List<Transformation> transformations, Renderer renderer) {
        super(config, transformations, renderer);
    }

    /**
     * Выполняет многопоточную генерацию фракталов.
     * Создаёт пул потоков и запускает задачи генерации, каждая из которых создаёт отдельный сэмпл изображения.
     *
     * @throws InterruptedException если выполнение потоков было прервано.
     */
    @Override
    public void generate() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);
        for (int i = 0; i < config.samples(); i++) {
            executorService.execute(() -> generateFlameSample(config.iterations()));
        }
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }
}
