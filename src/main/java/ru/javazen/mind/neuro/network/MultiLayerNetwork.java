package ru.javazen.mind.neuro.network;

import ru.javazen.mind.neuro.neuron.InputNeuron;
import ru.javazen.mind.neuro.neuron.factory.NeuronFactory;
import ru.javazen.mind.neuro.neuron.link.NeuralLink;
import ru.javazen.mind.neuro.neuron.Neuron;

import java.util.*;

public abstract class MultiLayerNetwork<T extends Neuron> implements Network<T> {

    protected List<InputNeuron> inputLayer;

    protected List<List<T>> layers;

    protected NeuronFactory neuronFactory;

    /**
     *
     * @param inputCount count of inputs
     * @param layersCount count of layers
     */
    public MultiLayerNetwork(NeuronFactory<T> neuronFactory, int inputCount, int ... layersCount) {

        this.neuronFactory = neuronFactory;
        inputLayer = new ArrayList<>(inputCount);

        layers = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < inputCount; i++) {
            InputNeuron neuron = new InputNeuron();
            inputLayer.add(neuron);
        }

        List<? extends Neuron> inputs = inputLayer;
        for (int count : layersCount) {
            layers.add(new ArrayList<>(count));

            for (int i=0; i<count; i++) {
                T neuron = neuronFactory.createNeuron();

                for (Neuron inputNeuron : inputs) {
                    NeuralLink link = new NeuralLink();
                    link.setWeight(random.nextDouble()/2 - 0.25);

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
        List<T> outputLayer = layers.get(layers.size() - 1);

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

    /**
     * Returns all layers without input layer (because it layer not contains input relations with weights)
     * @return all layers
     */
    public List<List<T>> getLayers() {
        return layers;
    }

    /**
     * Returns the {@link NeuronFactory} for this network
     * @return {@link NeuronFactory} for this network
     */
    public NeuronFactory getNeuronFactory() {
        return neuronFactory;
    }
}
