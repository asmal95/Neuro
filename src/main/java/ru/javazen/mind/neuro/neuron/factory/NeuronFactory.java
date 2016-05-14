package ru.javazen.mind.neuro.neuron.factory;

import ru.javazen.mind.neuro.neuron.Neuron;

public interface NeuronFactory <T extends Neuron> {

    T createNeuron();
}
