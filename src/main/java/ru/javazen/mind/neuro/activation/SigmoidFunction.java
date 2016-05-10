package ru.javazen.mind.neuro.activation;


public class SigmoidFunction implements ActivationFunction {

    private static SigmoidFunction instance = new SigmoidFunction();

    private SigmoidFunction() { }

    public static ActivationFunction getInstance() {
        return instance;
    }

    @Override
    public double process(double value) {
        return 1/(1+Math.exp(-value));
    }

    @Override
    public double derivative(double value) {
        double func = process(value);

        return func*(1-func);
    }

}
