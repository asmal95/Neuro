package ru.javazen.mind.neuro.network;


import ru.javazen.mind.neuro.neuron.Neuron;

import java.util.List;

public interface Network {

    /**
     * Performs work neural network
     * @param inputValues input values for neural network
     * @return result values from output layer
     */
    double[] process(double[] inputValues);

    /**
     * Returns all layers without input layer (because it layer not contains input relations with weights)
     * @return all layers
     */
    List<List<Neuron>> getLayers();
}
