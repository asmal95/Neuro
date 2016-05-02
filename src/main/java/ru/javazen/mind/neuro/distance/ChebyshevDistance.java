package ru.javazen.mind.neuro.distance;

public class ChebyshevDistance implements DistanceFunction {

    @Override
    public double distance(double[] fCoords, double[] sCoords) {
        if (fCoords.length != sCoords.length) {
            throw new IllegalArgumentException();
        }

        double max = 0;

        for (int i = 0; i < fCoords.length; i++) {
            double abs = Math.abs(fCoords[i] - sCoords[i]);
            if (max < abs) {
                max = abs;
            }
        }

        return max;
    }
}
