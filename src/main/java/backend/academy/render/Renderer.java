package backend.academy.render;

import backend.academy.domain.PixelColor;
import backend.academy.domain.Pixel;
import backend.academy.domain.Point;
import backend.academy.models.FractalImage;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;

/**
 * Класс Renderer используется для рендеринга фрактальных изображений.
 * Он поддерживает рендеринг с учётом симметрии и применяет гамма-коррекцию.
 */
public class Renderer {

    private static final int MAX_COLOR_VALUE = 255;
    private static final double FULL_CIRCLE_DEGREE = 360.0;
    private static final int COLOR_MASK = 0xFF;
    private static final int ALPHA_CHANNEL_SHIFT = 24;
    private static final int RED_CHANNEL_SHIFT = 16;
    private static final int GREEN_CHANNEL_SHIFT = 8;

    private final BufferedImage image;
    private final FractalImage fractalImage;
    private final List<double[]> precomputedAngles;

    /**
     * Создаёт экземпляр Renderer с заданными параметрами.
     *
     * @param width Ширина изображения.
     * @param height Высота изображения.
     * @param axesCount Количество осей симметрии.
     */
    public Renderer(int width, int height, int axesCount) {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.fractalImage = new FractalImage(width, height);
        this.precomputedAngles = new ArrayList<>();
        double angleStep = FULL_CIRCLE_DEGREE / axesCount;
        for (int i = 0; i < axesCount; i++) {
            double angle = Math.toRadians(i * angleStep);
            precomputedAngles.add(new double[]{Math.cos(angle), Math.sin(angle)});
        }
    }

    /**
     * Рендерит точку и её симметричные отображения.
     *
     * @param point Точка для рендеринга.
     * @param pixelColor Цвет точки.
     */
    public void renderPoint(Point point, PixelColor pixelColor) {
        List<Point> points = applySymmetry(point);
        points.add(point);
        points.forEach(p -> setPixel(p.x(), p.y(), pixelColor));
    }

    /**
     * Выводит окончательное изображение.
     */
    public void render() {
        IntStream.range(0, image.getHeight()).parallel().forEach(y -> {
            for (int x = 0; x < image.getWidth(); x++) {
                Pixel pixel = fractalImage.getPixel(x, y);
                image.setRGB(x, y, pixel.getColor().getRGB());
            }
        });
    }

    /**
     * Применяет гамма-коррекцию к изображению.
     *
     * @param gammaCoefficient Коэффициент гаммы.
     */
    public void applyGamma(double gammaCoefficient) {
        double power = 1.0 / gammaCoefficient;

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);

                int alpha = (pixel >> ALPHA_CHANNEL_SHIFT) & COLOR_MASK;
                int red = (pixel >> RED_CHANNEL_SHIFT) & COLOR_MASK;
                int green = (pixel >> GREEN_CHANNEL_SHIFT) & COLOR_MASK;
                int blue = pixel & COLOR_MASK;

                red = (int) (MAX_COLOR_VALUE * Math.pow(red / (double) MAX_COLOR_VALUE, power));
                green = (int) (MAX_COLOR_VALUE * Math.pow(green / (double) MAX_COLOR_VALUE, power));
                blue = (int) (MAX_COLOR_VALUE * Math.pow(blue / (double) MAX_COLOR_VALUE, power));

                int newPixel = (alpha << ALPHA_CHANNEL_SHIFT) | (red << RED_CHANNEL_SHIFT)
                    | (green << GREEN_CHANNEL_SHIFT) | blue;
                image.setRGB(x, y, newPixel);
            }
        }
    }

    /**
     * Устанавливает цвет пикселя.
     *
     * @param x Горизонтальная координата пикселя.
     * @param y Вертикальная координата пикселя.
     * @param pixelColor Цвет пикселя.
     */
    private void setPixel(double x, double y, PixelColor pixelColor) {
        int xInt = (int) ((x + 1) * image.getWidth() / 2);
        int yInt = (int) ((y + 1) * image.getHeight() / 2);

        if (xInt < 0 || xInt >= image.getWidth() || yInt < 0 || yInt >= image.getHeight()) {
            return;
        }

        Pixel pixel = fractalImage.getPixel(xInt, yInt);
        synchronized (pixel) {
            pixel.addPoint(pixelColor);
        }
    }

    /**
     * Генерирует симметричные точки для заданной точки.
     *
     * @param point Исходная точка.
     * @return Список симметричных точек.
     */
    private List<Point> applySymmetry(Point point) {
        List<Point> points = new ArrayList<>();
        for (double[] angle : precomputedAngles) {
            double newX = point.x() * angle[0] + point.y() * angle[1];
            double newY = -point.x() * angle[1] + point.y() * angle[0];
            points.add(new Point(newX, newY));
        }
        return points;
    }

    /**
     * Сохраняет изображение в файл.
     */
    public void saveImage() {
        try {
            ImageIO.write(image, "png", new File("src/main/resources/fractal.png"));
        } catch (Exception e) {
            System.err.println("Ошибка сохранения изображения: " + e.getMessage());
        }
    }
}
