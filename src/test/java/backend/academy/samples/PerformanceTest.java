package backend.academy.samples;

import backend.academy.config.InputConfig;
import backend.academy.generate.MultiThreadedGenerator;
import backend.academy.generate.SingleThreadedGenerator;
import backend.academy.render.Renderer;
import backend.academy.transformation.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

public class PerformanceTest {

    private InputConfig createConfig(int threads, boolean multithreaded) {
        return new InputConfig(
            1920, // width
            1080, // height
            100,  // samples
            10000, // iterations
            5,    // affine transformations count
            List.of("Spherical", "Swirl", "Sinusoidal"), // transformations
            multithreaded,
            threads,
            1     // axes count
        );
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

        SingleThreadedGenerator singleThreadedGenerator = new SingleThreadedGenerator(singleThreadConfig, createTransformations(), renderer);
        MultiThreadedGenerator multiThreadedGenerator = new MultiThreadedGenerator(multiThreadConfig, createTransformations(), renderer);

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
