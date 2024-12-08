package backend.academy.config;

import java.util.List;

/**
 * Конфигурация для генерации фрактальных изображений.
 * Содержит параметры, необходимые для настройки генерации и рендеринга фракталов.
 */
public record Config(int width,
                     int height,
                     int samples,
                     int iterations,
                     int affineTransformationsCount,
                     List<String> transformations,
                     boolean multithreaded,
                     int threads,
                     int axesCount) {}
