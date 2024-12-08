package backend.academy.render;

import backend.academy.domain.Color;
import backend.academy.domain.Pixel;
import backend.academy.models.FractalImage;
import backend.academy.domain.Point;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для рендеринга фрактальных изображений с учетом симметрии и гамма-коррекции.
 */
public class Renderer {

    private final BufferedImage image;
    private final FractalImage fractalImage;
    private final int axesCount;

    /**
     * Конструктор класса Renderer.
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
     * Рендерит точку с учетом симметрии.
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
     * Рендерит полное изображение.
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
     * Применяет гамма-коррекцию ко всем пикселям изображения.
     *
     * @param gamma Значение гаммы.
     */
    public void applyGammaCorrection(double gamma) {
        int newPixel;
        double power = 1.0 / gamma;

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);

                int alpha = (pixel >> 24) & 0xff;
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;

                red = (int) (255 * Math.pow(red / 255.0, power));
                green = (int) (255 * Math.pow(green / 255.0, power));
                blue = (int) (255 * Math.pow(blue / 255.0, power));

                newPixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
                image.setRGB(x, y, newPixel);
            }
        }
    }

    /**
     * Рассчитывает координаты пикселя на изображении и устанавливает его цвет.
     *
     * @param x Горизонтальная координата точки.
     * @param y Вертикальная координата точки.
     * @param color Цвет для установки.
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
     * Генерирует список точек, учитывая оси симметрии для данной точки.
     *
     * @param point Основная точка для генерации симметричных точек.
     * @return Список симметричных точек.
     */
    private List<Point> getSymmetryPoints(Point point) {
        List<Point> points = new ArrayList<>();
        double angleStep = 360.0 / axesCount;

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
