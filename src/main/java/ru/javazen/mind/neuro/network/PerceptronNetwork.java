package ru.javazen.mind.neuro.network;

import ru.javazen.mind.neuro.activation.ActivationFunction;
import ru.javazen.mind.neuro.neuron.ArtificialNeuron;
import ru.javazen.mind.neuro.neuron.factory.ArtificialNeuronFactory;


public class PerceptronNetwork extends MultiLayerNetwork<ArtificialNeuron> {

    public PerceptronNetwork(ActivationFunction function, int inputCount, int ... layersCount) {
        super(new ArtificialNeuronFactory(function), inputCount, layersCount);
    }
}
