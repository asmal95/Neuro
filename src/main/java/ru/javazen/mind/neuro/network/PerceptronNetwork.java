package ru.javazen.mind.neuro.network;

import ru.javazen.mind.neuro.function.ActivationFunction;
import ru.javazen.mind.neuro.neuron.InputNeuron;
import ru.javazen.mind.neuro.neuron.link.NeuralLink;
import ru.javazen.mind.neuro.neuron.Neuron;
import ru.javazen.mind.neuro.neuron.StandardNeuron;

import java.util.*;

public class PerceptronNetwork implements Network {

    private List<InputNeuron> inputLayer;
    private List<Neuron> middleLayer;
    private List<Neuron> outputLayer;

    /**
     * @param inputCount count neurons of input layer
     * @param middleCount count neurons of middle layer
     * @param outputCount count neurons of output layer
     * @param function activation function
     */
    public PerceptronNetwork(int inputCount, int middleCount, int outputCount, ActivationFunction function) {

        inputLayer = new ArrayList<>(inputCount);
        middleLayer = new ArrayList<>(middleCount);
        outputLayer = new ArrayList<>(outputCount);

        Random random = new Random();

        //input links
        for (int i = 0; i < inputCount; i++) {
            InputNeuron neuron = new InputNeuron();
            neuron.setActivationFunction(function);
            inputLayer.add(neuron);
        }

        //middle layer
        for (int i = 0; i < middleCount; i++) {
            StandardNeuron middleNeuron = new StandardNeuron();
            middleNeuron.setActivationFunction(function);

            for (Neuron inputNeuron : inputLayer) {
                NeuralLink link = new NeuralLink();
                link.setWeight(random.nextDouble());

                inputNeuron.addOutputLink(link);
                link.setInputNeuron(inputNeuron);

                middleNeuron.addInputLink(link);
                link.setOutputNeuron(middleNeuron);
            }
            middleLayer.add(middleNeuron);
        }

        //output layer
        for (int i = 0; i < outputCount; i++) {
            StandardNeuron outputNeuron = new StandardNeuron();
            outputNeuron.setActivationFunction(function);

            for (Neuron middleNeuron : middleLayer) {
                NeuralLink link = new NeuralLink();
                link.setWeight(random.nextDouble());

                middleNeuron.addOutputLink(link);
                link.setInputNeuron(middleNeuron);

                outputNeuron.addInputLink(link);
                link.setOutputNeuron(outputNeuron);
            }
            outputLayer.add(outputNeuron);
        }
    }

    @Override
    public double[] process(double[] inputValues) {
        if (inputValues.length != inputLayer.size()) {
            throw new IllegalArgumentException("Count of input values must be equals " + inputLayer.size());
        }
        double[] result = new double[outputLayer.size()];

        for (int i=0; i<inputLayer.size(); i++) {
            inputLayer.get(i).setInputValue(inputValues[i]);
        }
        for (int i = 0; i < outputLayer.size(); i++) {
            Neuron neuron = outputLayer.get(i);
            result[i] = neuron.getOutputValue();
        }

        //todo
        /*for (Neuron neuron : inputLayer) {
            neuron.reset();
        }
        for (Neuron neuron : middleLayer) {
            neuron.reset();
        }
        for (Neuron neuron : outputLayer) {
            neuron.reset();
        }*/

        return result;
    }

    @Override
    public List<List<Neuron>> getLayers() {
        return Arrays.asList(middleLayer, outputLayer);
    }
}
