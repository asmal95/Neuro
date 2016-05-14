package ru.javazen.mind.neuro.neuron;

import ru.javazen.mind.neuro.activation.ActivationFunction;
import ru.javazen.mind.neuro.neuron.link.NeuralLink;

public class ArtificialNeuron extends AbstractNeuron {

    private ActivationFunction activationFunction;

    public ArtificialNeuron(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }

    @Override
    protected double process() {
        double sum = 0;
        for (NeuralLink link : inputNeuralLinks) {
            sum += link.getInputNeuron().getOutputValue() * link.getWeight();
        }
        return activationFunction.process(sum);
    }

    public double getDerivativeValue() {
        double sum = 0;

        for (NeuralLink link : inputNeuralLinks) {
            sum += link.getInputNeuron().getOutputValue() * link.getWeight();
        }

        return activationFunction.derivative(sum);
    }

    public void setActivationFunction(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
        reset();
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }
}
