package ru.javazen.mind.neuro.neuron;

import ru.javazen.mind.neuro.activation.ActivationFunction;
import ru.javazen.mind.neuro.neuron.link.NeuralLink;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InputNeuron implements Neuron {

    private double inputValue;

    private List<NeuralLink> outputLinks = new ArrayList<>();
    private ActivationFunction activationFunction;

    private boolean isCalculateValue;
    private double calculateValue;

    public void setInputValue(double value) {
        inputValue = value;
        reset();
    }

    private double process() {
        return /*activationFunction.process*/(inputValue);
    }

    @Override
    public double getOutputValue() {
        if (!isCalculateValue) {
            calculateValue = process();
            isCalculateValue = true;
        }

        return calculateValue;
    }

    @Override
    public double getDerivativeValue() {
        return activationFunction.derivative(inputValue);
    }

    @Override
    public void setActivationFunction(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
        reset();
    }

    @Override
    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }

    @Override
    public List<NeuralLink> getInputLinks() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<NeuralLink> getOutputLinks() {
        return outputLinks;
    }

    @Override
    public void addOutputLink(NeuralLink neuralLink) {
        outputLinks.add(neuralLink);
    }

    @Override
    public void addInputLink(NeuralLink neuralLink) {
        throw new NotImplementedException();
    }

    @Override
    public void reset() {
        if (isCalculateValue) {
            outputLinks.forEach(l -> l.reset());
            isCalculateValue = false;
        }
    }
}