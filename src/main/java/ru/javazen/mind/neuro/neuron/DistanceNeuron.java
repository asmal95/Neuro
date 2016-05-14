package ru.javazen.mind.neuro.neuron;

import ru.javazen.mind.neuro.distance.DistanceFunction;
import ru.javazen.mind.neuro.distance.EuclideanDistance;
import ru.javazen.mind.neuro.neuron.link.NeuralLink;

public class DistanceNeuron extends AbstractNeuron {

    private DistanceFunction distanceFunction;

    public DistanceNeuron(DistanceFunction distanceFunction) {
        this.distanceFunction = distanceFunction;
    }

    public DistanceFunction getDistanceFunction() {
        return distanceFunction;
    }

    public void setDistanceFunction(DistanceFunction distanceFunction) {
        this.distanceFunction = distanceFunction;
    }

    @Override
    protected double process() {

        double[] f = new double[inputNeuralLinks.size()];
        double[] s = new double[inputNeuralLinks.size()];

        for (int i = 0; i < inputNeuralLinks.size(); i++) {
            NeuralLink link = inputNeuralLinks.get(i);
            f[i] = link.getInputNeuron().getOutputValue();
            s[i] = link.getWeight();
        }

        return distanceFunction.distance(f, s);
    }
}
