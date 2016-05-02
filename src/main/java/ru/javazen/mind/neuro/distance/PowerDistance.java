package ru.javazen.mind.neuro.distance;

public class PowerDistance implements DistanceFunction {

    private double r, p;

    public PowerDistance() {
        this(2, 2);
    }

    public PowerDistance(double r, double p) {
        this.r = r;
        this.p = p;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    @Override
    public double distance(double[] fCoords, double[] sCoords) {
        if (fCoords.length != sCoords.length) {
            throw new IllegalArgumentException();
        }

        double sqrSum = 0;

        for (int i = 0; i < fCoords.length; i++) {
            sqrSum += Math.pow((fCoords[i] - sCoords[i]), p);
        }

        return Math.pow(sqrSum, 1/r); //may be use Math.exp(Math.log(sqrSum)/r) ? (It's faster)
    }
}
