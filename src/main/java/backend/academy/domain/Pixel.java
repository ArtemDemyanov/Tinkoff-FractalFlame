package backend.academy.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Класс Pixel используется для представления пикселя на изображении.
 * Хранит информацию о цвете пикселя и количестве "попаданий", то есть сколько раз этот пиксель был обновлён.
 */
@Getter public class Pixel {
    private int red;
    private int green;
    private int blue;
    private int pointsCount;
    @Setter
    private double normal;

    /**
     * Создает пиксель с заданными значениями цвета.
     * @param red   Красный компонент цвета.
     * @param green Зелёный компонент цвета.
     * @param blue  Синий компонент цвета.
     */
    public Pixel(int red, int green, int blue)
    {
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
     * @param color Цвет, который будет добавлен к пикселю.
     */
    public void addPoint(Color color) {
        if (pointsCount == 0) {
            setRGB(color.r(), color.g(), color.b());
        } else {
            red = (red + color.r()) / 2;
            green = (green + color.g()) / 2;
            blue = (blue + color.b()) / 2;
        }
        this.pointsCount++;
    }

    /**
     * Возвращает цвет пикселя.
     * @return объект Color, представляющий цвет пикселя.
     */
    public Color getColor() {
        return new Color(red, green, blue);
    }
}
