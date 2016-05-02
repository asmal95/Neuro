package ru.javazen.mind.neuro.neighborhood;

public class ConstantFunction implements NeighborhoodFunction {

    private double constValue;

    public ConstantFunction(double constValue) {
        this.constValue = constValue;
    }

    public ConstantFunction() {
        this(1);
    }

    @Override
    public double process(double distance, int time) {
        return distance <= sigma(time) ? constValue : 0;
    }

    private double sigma(int t) {
        return 1/Math.exp(Math.pow(t, -2));
    }

    private double a(int k) {
        return (double)1/(k);
    }
}
