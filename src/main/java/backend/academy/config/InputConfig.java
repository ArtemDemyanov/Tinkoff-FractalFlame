package backend.academy.config;

import java.util.List;

/**
 * Конфигурация для генерации фрактальных изображений.
 */
public class InputConfig {
    private final int width;
    private final int height;
    private final int samples;
    private final int iterations;
    private final int affineTransformationsCount;
    private final List<String> transformations;
    private final boolean multithreaded;
    private final int threads;
    private final int axesCount;

    public InputConfig(int width, int height, int samples, int iterations, int affineTransformationsCount,
        List<String> transformations, boolean multithreaded, int threads, int axesCount) {
        this.width = width;
        this.height = height;
        this.samples = samples;
        this.iterations = iterations;
        this.affineTransformationsCount = affineTransformationsCount;
        this.transformations = transformations;
        this.multithreaded = multithreaded;
        this.threads = threads;
        this.axesCount = axesCount;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
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

    public List<String> transformations() {
        return transformations;
    }

    public boolean multithreaded() {
        return multithreaded;
    }

    public int threads() {
        return threads;
    }

    public int axesCount() {
        return axesCount;
    }
}
