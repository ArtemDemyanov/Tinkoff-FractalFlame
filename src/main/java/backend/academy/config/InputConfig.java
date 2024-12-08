package backend.academy.config;

import java.util.List;

/**
 * Конфигурация для генерации фрактальных изображений.
 */
public class InputConfig {
    private final ImageSettings imageSettings;
    private final GenerationSettings generationSettings;
    private final List<String> transformations;

    public InputConfig(int width, int height, int samples, int iterations, int affineTransformationsCount,
                       List<String> transformations, boolean multithreaded, int threads, int axesCount) {
        this.imageSettings = new ImageSettings(width, height, axesCount);
        this.generationSettings = new GenerationSettings(samples, iterations, affineTransformationsCount, multithreaded, threads);
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

    public int affineTransformationsCount() {
        return generationSettings.affineTransformationsCount();
    }

    public boolean multithreaded() {
        return generationSettings.multithreaded();
    }

    public int threads() {
        return generationSettings.threads();
    }

    // Геттер для трансформаций
    public List<String> transformations() {
        return transformations;
    }

    // Вложенный класс для параметров изображения
    private static class ImageSettings {
        private final int width;
        private final int height;
        private final int axesCount;

        public ImageSettings(int width, int height, int axesCount) {
            this.width = width;
            this.height = height;
            this.axesCount = axesCount;
        }

        public int width() {
            return width;
        }

        public int height() {
            return height;
        }

        public int axesCount() {
            return axesCount;
        }
    }

    // Вложенный класс для параметров генерации
    private static class GenerationSettings {
        private final int samples;
        private final int iterations;
        private final int affineTransformationsCount;
        private final boolean multithreaded;
        private final int threads;

        public GenerationSettings(int samples, int iterations, int affineTransformationsCount, boolean multithreaded, int threads) {
            this.samples = samples;
            this.iterations = iterations;
            this.affineTransformationsCount = affineTransformationsCount;
            this.multithreaded = multithreaded;
            this.threads = threads;
        }

        public int samples() {
            return samples;
        }

        public int iterations() {
            return iterations;
        }

        public int affineTransformationsCount() {
            return affineTransformationsCount;
        }

        public boolean multithreaded() {
            return multithreaded;
        }

        public int threads() {
            return threads;
        }
    }
}
