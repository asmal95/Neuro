package ru.javazen.mind.neuro.network;

import ru.javazen.mind.neuro.distance.Distance;
import ru.javazen.mind.neuro.function.ActivationFunction;
import ru.javazen.mind.neuro.neuron.Neuron;

import java.util.List;

public class KohonenNetwork extends MultiLayerNetwork {

    public KohonenNetwork(ActivationFunction function, int inputCount, int outputCount) {
        super(function, inputCount, outputCount);
    }

    public double[] training(double[] inputValues, Distance distance){
        if (inputValues.length != inputLayer.size()) {
            throw new IllegalArgumentException();
        }

        //gets a single layer of neurons
        List<Neuron> neurons = this.getLayers().get(0);
        int outputLength = neurons.size();

        for (int i=0; i<inputValues.length; i++) {
            inputLayer.get(i).setInputValue(inputValues[i]);
        }

        double[] result = new double[outputLength];
        for (int i=0; i<outputLength; i++) {
            result[i] = neurons.get(i).getOutputValue();
        }

        double[] distances = new double[outputLength];
        for (int i=0; i < outputLength; i++) {
            distances[i] = distance.distance(inputValues, result);
        }

        int indexMin = getIndexOfMinimumElement(distances);

        //todo corrected weight

        return null;
    }

    private int getIndexOfMinimumElement(double[] elements) {
        int index = 0;
        for (int i=0; i<elements.length; i++) {
            if (elements[i] < elements[index]) {
                index = i;
            }
        }
        return index;
    }
}