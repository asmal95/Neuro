package ru.javazen.mind.neuro.neuron.factory;

import ru.javazen.mind.neuro.activation.ActivationFunction;
import ru.javazen.mind.neuro.neuron.ArtificialNeuron;

public class ArtificialNeuronFactory implements NeuronFactory<ArtificialNeuron> {

    private ActivationFunction activationFunction;

    public ArtificialNeuronFactory(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }

    @Override
    public ArtificialNeuron createNeuron() {
        ArtificialNeuron neuron = new ArtificialNeuron(activationFunction);
        return neuron;
    }
}
