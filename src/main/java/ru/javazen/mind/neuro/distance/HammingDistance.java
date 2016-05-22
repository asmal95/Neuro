package ru.javazen.mind.neuro.distance;

public class HammingDistance implements DistanceFunction {

    @Override
    public double distance(double[] fCoords, double[] sCoords) {
        if (fCoords.length != sCoords.length) {
            throw new IllegalArgumentException();
        }

        double sum = 0;

        for (int i = 0; i < fCoords.length; i++) {
            if (Math.abs(fCoords[i] - sCoords[i]) < 0.01) {
                sum++;
            }
        }

        return sum;
    }
}
