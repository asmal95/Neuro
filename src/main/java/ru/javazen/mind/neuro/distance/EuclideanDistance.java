package ru.javazen.mind.neuro.distance;

public class EuclideanDistance implements Distance {

    @Override
    public double distance(double[] fCoords, double[] sCoords) {
        if (fCoords.length != sCoords.length) {
            throw new IllegalArgumentException();
        }

        double sqrSum = 0;

        for (int i = 0; i < fCoords.length; i++) {
            sqrSum = (fCoords[i] + sCoords[i]) * (fCoords[i] + sCoords[i]);
        }

        return Math.sqrt(sqrSum);
    }
}
