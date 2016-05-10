package ru.javazen.mind.neuro.network;

import ru.javazen.mind.neuro.activation.ActivationFunction;
import ru.javazen.mind.neuro.neuron.InputNeuron;
import ru.javazen.mind.neuro.neuron.link.NeuralLink;
import ru.javazen.mind.neuro.neuron.Neuron;
import ru.javazen.mind.neuro.neuron.StandardNeuron;

import java.util.*;

public class MultiLayerNetwork implements Network {

    protected List<InputNeuron> inputLayer;

    protected List<List<Neuron>> layers;


    /**
     *
     * @param function activation function
     * @param inputCount count of inputs
     * @param layersCount count of layers
     */
    public MultiLayerNetwork(ActivationFunction function, int inputCount, int ... layersCount) {

        inputLayer = new ArrayList<>(inputCount);

        layers = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < inputCount; i++) {
            InputNeuron neuron = new InputNeuron();
            neuron.setActivationFunction(function);
            inputLayer.add(neuron);
        }

        List<? extends Neuron> inputs = inputLayer;
        for (int count : layersCount) {
            layers.add(new ArrayList<Neuron>(count));

            for (int i=0; i<count; i++) {
                StandardNeuron neuron = new StandardNeuron();
                neuron.setActivationFunction(function);

                for (Neuron inputNeuron : inputs) {
                    NeuralLink link = new NeuralLink();
                    link.setWeight(random.nextDouble() - 0.5);

                    inputNeuron.addOutputLink(link);
                    link.setInputNeuron(inputNeuron);

                    neuron.addInputLink(link);
                    link.setOutputNeuron(neuron);
                }
                layers.get(layers.size() - 1).add(neuron);
            }
            inputs = layers.get(layers.size() - 1);
        }
    }

    @Override
    public double[] process(double[] inputValues) {
        if (inputValues.length != inputLayer.size()) {
            throw new IllegalArgumentException("Count of input values must be equals " + inputLayer.size());
        }
        List<Neuron> outputLayer = layers.get(layers.size() - 1);

        double[] result = new double[outputLayer.size()];

        for (int i=0; i<inputLayer.size(); i++) {
            inputLayer.get(i).setInputValue(inputValues[i]);
        }
        for (int i = 0; i < outputLayer.size(); i++) {
            Neuron neuron = outputLayer.get(i);
            result[i] = neuron.getOutputValue();
        }

        return result;
    }

    @Override
    public List<List<Neuron>> getLayers() {
        return layers;
    }
}
