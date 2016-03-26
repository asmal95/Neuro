package ru.javazen.mind.neuro.education;


import ru.javazen.mind.neuro.network.Network;

public interface Teacher {

    void training(Network network, double[] inputValues, double[] expectedOutputValues);

    void training(Network network, double[] inputValues, double[] expectedOutputValues, double learningRate);
}
