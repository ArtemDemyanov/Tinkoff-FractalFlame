package backend.academy.models;

import java.util.Random;

public record AffineCoefficient(double a, double b, double d, double e, double c, double f) {

    public static AffineCoefficient generate(Random random) {
        double a;
        double b;
        double c;
        double d;
        double e;
        double f;
        double a2;
        double b2;
        double c2;
        double d2;
        double determinant;
        double determinantSquare;

        do {
            a = random.nextDouble(-1, 1);
            b = random.nextDouble(-1, 1);
            c = random.nextDouble(-1, 1);
            d = random.nextDouble(-1, 1);
            e = random.nextDouble(-1, 1);
            f = random.nextDouble(-1, 1);

            a2 = Math.pow(a, 2);
            b2 = Math.pow(b, 2);
            c2 = Math.pow(c, 2);
            d2 = Math.pow(d, 2);
            determinant = a * d - b * c;
            determinantSquare = Math.pow(determinant, 2);
        } while (!checkCoefficientsValidity(a2, b2, c2, d2, determinantSquare));

        return new AffineCoefficient(a, b, d, e, c, f);
    }

    private static boolean checkCoefficientsValidity(double a2, double b2, double c2, double d2,
        double determinantSquare) {
        return (a2 + c2 < 1) && (b2 + d2 < 1) && (a2 + b2 + c2 + d2 < 1 + determinantSquare);
    }
}
