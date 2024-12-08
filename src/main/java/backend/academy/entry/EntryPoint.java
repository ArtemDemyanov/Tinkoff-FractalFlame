package backend.academy.entry;

import backend.academy.config.Config;
import backend.academy.config.InputHandler;
import backend.academy.generate.MultiThreadedGenerator;
import backend.academy.generate.SingleThreadedGenerator;
import backend.academy.render.Renderer;
import backend.academy.transformation.Transformation;
import java.util.List;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Класс EntryPoint служит точкой входа в приложение для генерации фракталов.
 * Обеспечивает интерфейс командной строки для настройки параметров генерации.
 */
public class EntryPoint {

    /**
     * Запускает процесс генерации фракталов, руководствуясь вводом пользователя.
     */
    public void start() {
        PrintStream out = System.out;
        try (Scanner reader = new Scanner(System.in)) {
            out.print("Введите ширину изображения: ");
            int width = InputHandler.getInt(reader, 1920);

            out.print("Введите высоту изображения: ");
            int height = InputHandler.getInt(reader, 1080);

            out.print("Введите количество сэмплов: ");
            int samplesCount = InputHandler.getInt(reader, 5);

            out.print("Введите количество итераций: ");
            int iterations = InputHandler.getInt(reader, 100000000);

            out.print("Введите количество аффинных преобразований: ");
            int affineTransformationsCount = InputHandler.getInt(reader, 5);

            out.print("Включить многопоточность? (true/false): ");
            boolean multithreaded = InputHandler.getBoolean(reader, true);

            int threads = 1;
            if (multithreaded) {
                out.print("Введите количество потоков: ");
                threads = InputHandler.getInt(reader, 4);
            }

            out.print("Выберите трансформации (Heart, Polar, Sinusoidal, Spherical, Swirl): ");
            List<String> transformationNames = InputHandler.getTransformations(reader);

            out.print("Введите количество осей симметрии: ");
            int axesCount = InputHandler.getInt(reader, 1);

            out.print("Введите значение гаммы: ");
            double gamma = InputHandler.getDouble(reader, 2.2);

            Config config = new Config(width, height, samplesCount, iterations, affineTransformationsCount,
                transformationNames, multithreaded, threads, axesCount);

            Renderer renderer = new Renderer(config.width(), config.height(), config.axesCount());

            List<Transformation> transformations = InputHandler.createTransformations(transformationNames);

            if (config.multithreaded()) {
                MultiThreadedGenerator
                    multiGenerator = new MultiThreadedGenerator(config, transformations, renderer);
                multiGenerator.generate();
            } else {
                SingleThreadedGenerator
                    generator = new SingleThreadedGenerator(config, transformations, renderer);
                generator.generate();
            }
            renderer.applyGammaCorrection(gamma);
            renderer.renderImage();
            renderer.saveImage();
            out.println("Фрактал успешно сгенерирован и сохранён в src/main/resources/fractal.png");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
