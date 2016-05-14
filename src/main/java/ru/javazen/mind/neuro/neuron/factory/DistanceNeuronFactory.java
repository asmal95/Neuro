package ru.javazen.mind.neuro.neuron.factory;

import ru.javazen.mind.neuro.distance.DistanceFunction;
import ru.javazen.mind.neuro.neuron.DistanceNeuron;

public class DistanceNeuronFactory implements NeuronFactory<DistanceNeuron> {

    private DistanceFunction distanceFunction;

    public DistanceNeuronFactory(DistanceFunction distanceFunction) {
        this.distanceFunction = distanceFunction;
    }

    @Override
    public DistanceNeuron createNeuron() {
        DistanceNeuron neuron = new DistanceNeuron(distanceFunction);
        return neuron;
    }
}
