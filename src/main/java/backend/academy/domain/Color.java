package backend.academy.domain;

/**
 * Класс представляет цвет в формате RGB.
 * Этот класс используется для работы с цветами, где каждый цвет представлен
 * красным, зеленым и синим компонентами.
 */
public record Color(int r, int g, int b) {

    private static final int RED_SHIFT = 16;
    private static final int GREEN_SHIFT = 8;

    /**
     * Возвращает цвет в формате RGB.
     * Собирает три компонента цвета (красный, зеленый, синий) в одно целочисленное значение.
     *
     * @return Целочисленное представление цвета в формате RGB.
     */
    public int getRGB() {
        return (r << RED_SHIFT) | (GREEN_SHIFT << 8) | b;
    }
}
