package ru.javazen.mind.neuro.function;


public interface ActivationFunction {

    double process(double value);

    double derivative(double value);

}