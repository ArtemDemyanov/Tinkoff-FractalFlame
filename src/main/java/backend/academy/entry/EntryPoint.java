package backend.academy.entry;

import backend.academy.config.InputConfig;
import backend.academy.config.InputHandler;
import backend.academy.generate.MultiThreadedGenerator;
import backend.academy.generate.SingleThreadedGenerator;
import backend.academy.render.Renderer;
import backend.academy.transformation.Transformation;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

/**
 * Класс EntryPoint служит точкой входа в приложение для генерации фракталов.
 * Обеспечивает интерфейс командной строки для настройки параметров генерации.
 */
public class EntryPoint {

    private static final int DEFAULT_WIDTH = 1920;
    private static final int DEFAULT_HEIGHT = 1080;
    private static final int DEFAULT_SAMPLES = 5;
    private static final int DEFAULT_ITERATIONS = 10000000;
    public static final int DEFAULT_AFFINE_COUNT = 5;
    private static final int DEFAULT_THREADS = 4;
    private static final int DEFAULT_AXES = 8;
    private static final double DEFAULT_GAMMA = 2.5;

    /**
     * Запускает процесс генерации фракталов, руководствуясь вводом пользователя.
     */
    public void start() {
        PrintStream out = System.out;
        try (Scanner reader = new Scanner(System.in)) {
            out.print("Введите ширину изображения: ");
            int width = InputHandler.getInt(reader, DEFAULT_WIDTH);

            out.print("Введите высоту изображения: ");
            int height = InputHandler.getInt(reader, DEFAULT_HEIGHT);

            out.print("Введите количество сэмплов: ");
            int samples = InputHandler.getInt(reader, DEFAULT_SAMPLES);

            out.print("Введите количество итераций: ");
            int iterations = InputHandler.getInt(reader, DEFAULT_ITERATIONS);

            out.print("Введите количество аффинных преобразований (по умолчанию 5): ");
            int affineTransformationsCount = InputHandler.getInt(reader, DEFAULT_AFFINE_COUNT);

            out.print("Включить многопоточность? (true/false): ");
            boolean multithreaded = InputHandler.getBoolean(reader, true);

            int threads = 1;
            if (multithreaded) {
                out.print("Введите количество потоков: ");
                threads = InputHandler.getInt(reader, DEFAULT_THREADS);
            }

            out.print("Выберите трансформации (Heart, Polar, Sinusoidal, Spherical, Swirl): ");
            List<String> transformationNames = InputHandler.getTransformations(reader);

            out.print("Введите количество осей симметрии: ");
            int axesCount = InputHandler.getInt(reader, DEFAULT_AXES);

            out.print("Введите значение гаммы: ");
            double gamma = InputHandler.getDouble(reader, DEFAULT_GAMMA);

            InputConfig.ImageSettings imageSettings = new InputConfig.ImageSettings(width, height, axesCount);
            InputConfig.GenerationSettings generationSettings = new InputConfig.GenerationSettings(samples,
                iterations, affineTransformationsCount, multithreaded, threads);
            InputConfig config = new InputConfig(imageSettings, generationSettings, transformationNames);

            Renderer renderer = new Renderer(config.width(), config.height(), config.axesCount());

            List<Transformation> transformations = InputHandler.createTransformations(transformationNames);

            if (config.multithreaded()) {
                MultiThreadedGenerator multiGenerator = new MultiThreadedGenerator(config, transformations, renderer);
                multiGenerator.generate();
            } else {
                SingleThreadedGenerator generator = new SingleThreadedGenerator(config, transformations, renderer);
                generator.generate();
            }
            renderer.applyGamma(gamma);
            renderer.render();
            renderer.saveImage();
            out.println("Изображение сохранено в src/main/resources/fractal.png");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
