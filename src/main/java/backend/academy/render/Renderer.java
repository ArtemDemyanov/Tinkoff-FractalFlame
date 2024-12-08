package backend.academy.render;

import backend.academy.domain.Color;
import backend.academy.domain.Pixel;
import backend.academy.domain.Point;
import backend.academy.models.FractalImage;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    private final int axesCount;

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
        this.axesCount = axesCount;
    }

    /**
     * Рендерит точку и её симметричные отображения.
     *
     * @param point Точка для рендеринга.
     * @param color Цвет точки.
     */
    public void renderPoint(Point point, Color color) {
        List<Point> points = getSymmetryPoints(point);
        points.add(point);
        points.forEach(p -> setPixel(p.x(), p.y(), color));
    }

    /**
     * Выводит окончательное изображение.
     */
    public void renderImage() {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Pixel pixel = fractalImage.getPixel(x, y);
                image.setRGB(x, y, pixel.getColor().getRGB());
            }
        }
    }

    /**
     * Применяет гамма-коррекцию к изображению.
     *
     * @param gamma Коэффициент гаммы.
     */
    public void applyGammaCorrection(double gamma) {
        double power = 1.0 / gamma;

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);

                int alpha = (pixel >> ALPHA_CHANNEL_SHIFT) & COLOR_MASK;
                int red = (pixel >> RED_CHANNEL_SHIFT) & COLOR_MASK;
                int green = (pixel >> GREEN_CHANNEL_SHIFT) & COLOR_MASK;
                int blue = pixel & COLOR_MASK;

                red = (int) (MAX_COLOR_VALUE * Math.pow(red / (double)MAX_COLOR_VALUE, power));
                green = (int) (MAX_COLOR_VALUE * Math.pow(green / (double)MAX_COLOR_VALUE, power));
                blue = (int) (MAX_COLOR_VALUE * Math.pow(blue / (double)MAX_COLOR_VALUE, power));

                int newPixel = (alpha << ALPHA_CHANNEL_SHIFT) | (red << RED_CHANNEL_SHIFT) | (green << GREEN_CHANNEL_SHIFT) | blue;
                image.setRGB(x, y, newPixel);
            }
        }
    }

    /**
     * Устанавливает цвет пикселя.
     *
     * @param x Горизонтальная координата пикселя.
     * @param y Вертикальная координата пикселя.
     * @param color Цвет пикселя.
     */
    private void setPixel(double x, double y, Color color) {
        int xInt = (int) ((x + 1) * image.getWidth() / 2);
        int yInt = (int) ((y + 1) * image.getHeight() / 2);

        if (xInt >= 0 && xInt < image.getWidth() && yInt >= 0 && yInt < image.getHeight()) {
            Pixel pixel = fractalImage.getPixel(xInt, yInt);
            synchronized (pixel) {
                pixel.addPoint(color);
            }
        }
    }

    /**
     * Генерирует симметричные точки для заданной точки.
     *
     * @param point Исходная точка.
     * @return Список симметричных точек.
     */
    private List<Point> getSymmetryPoints(Point point) {
        List<Point> points = new ArrayList<>();
        double angleStep = FULL_CIRCLE_DEGREE / axesCount;

        for (int i = 0; i < axesCount; i++) {
            double angle = Math.toRadians(i * angleStep);
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);
            double newX = point.x() * cos + point.y() * sin;
            double newY = -point.x() * sin + point.y() * cos;
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
