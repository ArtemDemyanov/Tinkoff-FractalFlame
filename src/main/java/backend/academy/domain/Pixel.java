package backend.academy.domain;

import lombok.Getter;

/**
 * Класс Pixel используется для представления пикселя на изображении.
 * Хранит информацию о цвете пикселя и количестве "попаданий", то есть сколько раз этот пиксель был обновлён.
 */
@Getter public class Pixel {
    private int red;
    private int green;
    private int blue;
    private int pointsCount;

    /**
     * Создает пиксель с заданными значениями цвета.
     * @param red   Красный компонент цвета.
     * @param green Зелёный компонент цвета.
     * @param blue  Синий компонент цвета.
     */
    public Pixel(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.pointsCount = 0;
    }

    /**
     * Устанавливает новые значения RGB для этого пикселя.
     * @param red   новое значение красного компонента.
     * @param green новое значение зелёного компонента.
     * @param blue  новое значение синего компонента.
     */
    public void setRGB(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Добавляет цвет к пикселю, усредняя его с существующим цветом на основе количества попаданий.
     * @param pixelColor Цвет, который будет добавлен к пикселю.
     */
    public void addPoint(PixelColor pixelColor) {
        if (pointsCount == 0) {
            setRGB(pixelColor.red(), pixelColor.green(), pixelColor.blue());
        } else {
            red = (red + pixelColor.red()) / 2;
            green = (green + pixelColor.green()) / 2;
            blue = (blue + pixelColor.blue()) / 2;
        }
        this.pointsCount++;
    }

    /**
     * Возвращает цвет пикселя.
     * @return объект Color, представляющий цвет пикселя.
     */
    public PixelColor getColor() {
        return new PixelColor(red, green, blue);
    }
}
