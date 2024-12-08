package backend.academy.models;

import backend.academy.domain.Pixel;
import lombok.Getter;
import java.util.stream.IntStream;

/**
 * Класс представляющий изображение фрактала.
 * Хранит массив пикселей и обеспечивает доступ к пикселям по координатам.
 */
public class FractalImage {

    private final Pixel[] pixels;
    private final int width;
    @Getter private final int height;

    /**
     * Конструктор класса FractalImage.
     * Инициализирует изображение заданного размера, заполняя его пикселями с начальным цветом (черный).
     *
     * @param width Ширина изображения в пикселях.
     * @param height Высота изображения в пикселях.
     */
    public FractalImage(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new Pixel[width * height];
        IntStream.range(0, width*height).forEach(i -> this.pixels[i] = new Pixel(0, 0,0));
    }

    /**
     * Возвращает пиксель изображения по заданным координатам.
     *
     * @param x Горизонтальная координата пикселя.
     * @param y Вертикальная координата пикселя.
     * @return Объект {@link Pixel}, соответствующий указанным координатам.
     */
    public Pixel getPixel(int x, int y) {
        return this.pixels[y * width + x];
    }

}
