package ru.javazen.mind.neuro.neuron;

import ru.javazen.mind.neuro.neuron.link.NeuralLink;

import java.util.List;

public interface Neuron {

    double getOutputValue();

    void addInputLink(NeuralLink neuralLink);
    void addOutputLink(NeuralLink neuralLink);

    List<NeuralLink> getInputLinks();
    List<NeuralLink> getOutputLinks();

    void reset();

}
