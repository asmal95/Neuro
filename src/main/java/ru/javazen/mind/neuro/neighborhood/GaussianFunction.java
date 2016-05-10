package ru.javazen.mind.neuro.neighborhood;

public class GaussianFunction implements NeighborhoodFunction {

    @Override
    public double process(double distance, int time) {
        return Math.exp(-(distance/(/*2 * */sigma(time))));
    }

    private double sigma(int t) {
        return 1/Math.exp(Math.pow(t, -2));
    }

    private double a(int k) {
        return (double)1/(k*2);
    }
}
