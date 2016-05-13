package ru.javazen.mind.neuro.neighborhood;

public class GaussianFunction implements NeighborhoodFunction {

    private static final double M = 0;
    private static final double SQRT_OF_2PI = Math.sqrt(2*Math.PI);

    @Override
    public double process(double distance, double time) {
        double sigma = sigma(time);
        return (1/(sigma * SQRT_OF_2PI)) * Math.exp(-(Math.pow(distance-M, 2)/(2 * Math.pow(sigma, 2))));
    }

    private double sigma(double t) {
        return 1/Math.exp(Math.pow(t, -2));
    }
}
