package backend.academy.config;

import java.util.List;

/**
 * Конфигурация для генерации фрактальных изображений.
 */
public class InputConfig {
    private final ImageSettings imageSettings;
    private final GenerationSettings generationSettings;
    private final List<String> transformations;

    public InputConfig(ImageSettings imageSettings, GenerationSettings generationSettings,
                       List<String> transformations) {
        this.imageSettings = imageSettings;
        this.generationSettings = generationSettings;
        this.transformations = transformations;
    }

    // Геттеры для параметров изображения
    public int width() {
        return imageSettings.width();
    }

    public int height() {
        return imageSettings.height();
    }

    public int axesCount() {
        return imageSettings.axesCount();
    }

    // Геттеры для параметров генерации
    public int samples() {
        return generationSettings.samples();
    }

    public int iterations() {
        return generationSettings.iterations();
    }

    public int affineTransformations() {
        return generationSettings.affineTransformations();
    }

    public boolean multithreaded() {
        return generationSettings.multithreaded();
    }

    public int threads() {
        return generationSettings.threads();
    }

    public List<String> transformations() {
        return transformations;
    }

    /**
     * Класс для параметров изображения.
     */public record ImageSettings(int width, int height, int axesCount) {
     }

    /**
     * Класс для параметров генерации.
     */
    public record GenerationSettings(int samples, int iterations, int affineTransformations, boolean multithreaded,
                                     int threads) {
    }
}
