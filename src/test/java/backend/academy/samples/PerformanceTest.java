package backend.academy.samples;

import backend.academy.config.InputConfig;
import backend.academy.config.InputConfig.GenerationSettings;
import backend.academy.config.InputConfig.ImageSettings;
import backend.academy.generate.MultiThreadedGenerator;
import backend.academy.generate.SingleThreadedGenerator;
import backend.academy.render.Renderer;
import backend.academy.transformation.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PerformanceTest {

    private InputConfig createConfig(int threads, boolean multithreaded) {
        // Создание настроек изображения
        ImageSettings imageSettings = new ImageSettings(1920, 1080, 1);

        // Создание настроек генерации
        GenerationSettings generationSettings = new GenerationSettings(100, 10000, 5, multithreaded, threads);

        // Трансформации
        List<String> transformations = List.of("Spherical", "Swirl", "Sinusoidal");

        return new InputConfig(imageSettings, generationSettings, transformations);
    }

    private List<Transformation> createTransformations() {
        return List.of(
            new SphericalTransformation(),
            new SwirlTransformation(),
            new SinusoidalTransformation()
        );
    }

    @Test
    void comparePerformance() {
        InputConfig singleThreadConfig = createConfig(1, false);
        InputConfig multiThreadConfig = createConfig(4, true);

        Renderer renderer = new Renderer(singleThreadConfig.width(), singleThreadConfig.height(), singleThreadConfig.axesCount());

        SingleThreadedGenerator
            singleThreadedGenerator = new SingleThreadedGenerator(singleThreadConfig, createTransformations(), renderer);
        MultiThreadedGenerator
            multiThreadedGenerator = new MultiThreadedGenerator(multiThreadConfig, createTransformations(), renderer);

        long singleThreadStartTime = System.nanoTime();
        singleThreadedGenerator.generate();
        long singleThreadEndTime = System.nanoTime();

        long multiThreadStartTime = System.nanoTime();
        try {
            multiThreadedGenerator.generate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long multiThreadEndTime = System.nanoTime();

        long singleThreadDuration = singleThreadEndTime - singleThreadStartTime;
        long multiThreadDuration = multiThreadEndTime - multiThreadStartTime;

        System.out.println("Single-threaded duration: " + singleThreadDuration / 1_000_000 + " ms");
        System.out.println("Multi-threaded duration: " + multiThreadDuration / 1_000_000 + " ms");

        assertTrue(singleThreadDuration > multiThreadDuration, "Multi-threaded version should be faster");
    }
}
