package backend.academy.samples;

import backend.academy.domain.Point;
import backend.academy.transformation.HeartTransformation;
import backend.academy.transformation.PolarTransformation;
import backend.academy.transformation.SinusoidalTransformation;
import backend.academy.transformation.SphericalTransformation;
import backend.academy.transformation.SwirlTransformation;
import backend.academy.transformation.Transformation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransformationsTest {

    private static final double DELTA = 0.0001;

    @Test
    void testSphericalTransformation() {
        Transformation transformation = new SphericalTransformation();
        Point input = new Point(2, 0);
        Point expected = new Point(0.5, 0); // Correct expected result
        Point result = transformation.transform(input);

        assertEquals(expected.x(), result.x(), DELTA);
        assertEquals(expected.y(), result.y(), DELTA);
    }

    @Test
    void testSwirlTransformation() {
        Transformation transformation = new SwirlTransformation();
        Point input = new Point(1, 1);
        double r2 = 2; // x^2 + y^2 = 1^2 + 1^2
        Point expected = new Point(1 * Math.sin(r2) - 1 * Math.cos(r2),
            1 * Math.cos(r2) + 1 * Math.sin(r2));
        Point result = transformation.transform(input);

        assertEquals(expected.x(), result.x(), DELTA);
        assertEquals(expected.y(), result.y(), DELTA);
    }

    @Test
    void testSinusoidalTransformation() {
        Transformation transformation = new SinusoidalTransformation();
        Point input = new Point(Math.PI, 0);
        Point expected = new Point(0, 0);
        Point result = transformation.transform(input);

        assertEquals(expected.x(), result.x(), DELTA);
        assertEquals(expected.y(), result.y(), DELTA);
    }

    @Test
    void testPolarTransformation() {
        Transformation transformation = new PolarTransformation();
        Point input = new Point(1, 0);
        Point expected = new Point(0, 0); // theta / PI = 0, r - 1 = 0
        Point result = transformation.transform(input);

        assertEquals(expected.x(), result.x(), DELTA);
        assertEquals(expected.y(), result.y(), DELTA);
    }

    @Test
    void testHeartTransformation() {
        Transformation transformation = new HeartTransformation();
        Point input = new Point(0, 1);
        double r = Math.sqrt(0 * 0 + 1 * 1); // r = 1
        double theta = Math.atan2(1, 0); // theta = PI/2
        Point expected = new Point(r * Math.sin(theta * r), -r * Math.cos(theta * r));
        Point result = transformation.transform(input);

        assertEquals(expected.x(), result.x(), DELTA);
        assertEquals(expected.y(), result.y(), DELTA);
    }
}
