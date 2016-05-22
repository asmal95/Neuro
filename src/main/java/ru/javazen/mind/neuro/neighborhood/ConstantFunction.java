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
    public double process(double distance, double time) {
        return distance == 0 ? constValue*a(time) : 0;
    }
    private double a(double k) {
        return 2d/(3+k);
    }
}
