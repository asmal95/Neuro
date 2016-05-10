package ru.javazen.mind.neuro.activation;

public class BipolarSigmoidFunction implements ActivationFunction {

    private static BipolarSigmoidFunction instance = new BipolarSigmoidFunction();

    private BipolarSigmoidFunction() { }

    public static ActivationFunction getInstance() {
        return instance;
    }

    @Override
    public double process(double value) {
        return (2/(1+Math.exp(-value)))-1;
    }

    @Override
    public double derivative(double value) {
        double func = process(value);

        return 0.5 * (1+func)*(1-func);
    }
}
