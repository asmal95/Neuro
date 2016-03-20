package ru.javazen.mind.neuro.neuron.link;

import ru.javazen.mind.neuro.neuron.Neuron;

public class NeuralLink {
    private Neuron inputNeuron;
    private Neuron outputNeuron;
    private double weight;

    public Neuron getInputNeuron() {
        return inputNeuron;
    }

    public void setInputNeuron(Neuron inputNeuron) {
        this.inputNeuron = inputNeuron;
    }

    public Neuron getOutputNeuron() {
        return outputNeuron;
    }

    public void setOutputNeuron(Neuron outputNeuron) {
        this.outputNeuron = outputNeuron;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
