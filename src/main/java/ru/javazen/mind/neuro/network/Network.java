package ru.javazen.mind.neuro.network;


import ru.javazen.mind.neuro.neuron.Neuron;

import java.util.List;

public interface Network<T extends Neuron> {

    /**
     * Performs work neural network
     * @param inputValues input values for neural network
     * @return result values from output layer
     */
    double[] process(double[] inputValues);
}