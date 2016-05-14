package ru.javazen.mind.neuro.neuron;

import ru.javazen.mind.neuro.neuron.link.NeuralLink;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNeuron implements Neuron {
    protected List<NeuralLink> inputNeuralLinks = new ArrayList<>();
    protected List<NeuralLink> outputNeuralLinks = new ArrayList<>();

    private boolean isCalculateValue;
    private double calculateValue;


    abstract protected double process();

    @Override
    public double getOutputValue() {
        if (!isCalculateValue) {
            calculateValue = process();
            isCalculateValue = true;
        }

        return calculateValue;
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
        reset();
    }

    @Override
    public void addOutputLink(NeuralLink neuralLink) {
        outputNeuralLinks.add(neuralLink);
    }

    @Override
    public void reset() {
        if (isCalculateValue) {
            outputNeuralLinks.forEach(l -> l.reset());
            isCalculateValue = false;
        }
    }
}