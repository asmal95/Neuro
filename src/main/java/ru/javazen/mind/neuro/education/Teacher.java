package ru.javazen.mind.neuro.education;


import ru.javazen.mind.neuro.network.Network;

public interface Teacher<T extends Network> {

    void training(T network, double[] inputValues, double[] expectedOutputValues);

    void training(T network, double[] inputValues, double[] expectedOutputValues, double learningRate);
}
