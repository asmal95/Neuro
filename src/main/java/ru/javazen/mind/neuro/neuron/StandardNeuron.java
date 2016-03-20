package ru.javazen.mind.neuro.neuron;

import ru.javazen.mind.neuro.function.ActivationFunction;
import ru.javazen.mind.neuro.neuron.link.NeuralLink;

import java.util.ArrayList;
import java.util.List;

public class StandardNeuron implements Neuron {
    private List<NeuralLink> inputNeuralLinks = new ArrayList<>();
    private List<NeuralLink> outputNeuralLinks = new ArrayList<>();
    private ActivationFunction activationFunction;

    private boolean isCalculateValue;
    private double calculateValue;


    private double process() {
        double sum = 0;

        for (NeuralLink link : inputNeuralLinks) {
            sum += link.getInputNeuron().getOutputValue() * link.getWeight();
        }

        return activationFunction.process(sum);
    }

    @Override
    public double getOutputValue() {
        //if (!isCalculateValue) { //todo
            calculateValue = process();
            isCalculateValue = true;
        //}

        return calculateValue;
    }

    @Override
    public double getDerivativeValue() {
        double sum = 0;

        for (NeuralLink link : inputNeuralLinks) {
            sum += link.getInputNeuron().getOutputValue() * link.getWeight();
        }

        return activationFunction.derivative(sum);
    }

    @Override
    public void setActivationFunction(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }

    @Override
    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }

    @Override
    public List<NeuralLink> getInputLinks() {
        return inputNeuralLinks;
    }

    @Override
    public List<NeuralLink> getOutputLinks() {
        return outputNeuralLinks;
    }

    @Override
    public void addInputLink(NeuralLink neuralLink) {
        inputNeuralLinks.add(neuralLink);
    }

    @Override
    public void addOutputLink(NeuralLink neuralLink) {
        outputNeuralLinks.add(neuralLink);
    }

    /*@Override
    public void reset() {
        isCalculateValue = false;
    }*/
}