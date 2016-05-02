package ru.javazen.mind.neuro.distance;

public class ManhattanDistance implements DistanceFunction {

    @Override
    public double distance(double[] fCoords, double[] sCoords) {
        if (fCoords.length != sCoords.length) {
            throw new IllegalArgumentException();
        }

        double sum = 0;

        for (int i = 0; i < fCoords.length; i++) {
            sum += Math.abs(fCoords[i] - sCoords[i]);
        }

        return sum;
    }
}
