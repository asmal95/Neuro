package ru.javazen.mind.neuro.network;

import ru.javazen.mind.neuro.distance.DistanceFunction;
import ru.javazen.mind.neuro.activation.ActivationFunction;
import ru.javazen.mind.neuro.neighborhood.NeighborhoodFunction;
import ru.javazen.mind.neuro.neuron.Neuron;
import ru.javazen.mind.neuro.neuron.link.NeuralLink;

import java.util.Arrays;
import java.util.List;

public class KohonenNetwork extends MultiLayerNetwork {

    private NeighborhoodFunction neighborhoodFunction;

    public KohonenNetwork(ActivationFunction activationFunction, NeighborhoodFunction neighborhoodFunction, int inputCount, int outputCount) {
        super(activationFunction, inputCount, outputCount);
        this.neighborhoodFunction = neighborhoodFunction;
    }

    //@Override
    public double[] process(double[] inputValues, boolean normalize) {


        double[] result = super.process(inputValues);
        if (normalize) {
            double max = Arrays.stream(result).max().getAsDouble();
            for (int i = 0; i < result.length; i++) {
                if (result[i] != max) {
                    result[i] = 0;
                } else {
                    result[i] = 1;
                }
            }
        }
        return result;
    }

    public void training(double[] inputValues, DistanceFunction distance, double era){
        if (inputValues.length != inputLayer.size()) {
            throw new IllegalArgumentException();
        }

        //gets a single layer of neurons
        List<Neuron> neurons = this.getLayers().get(0);
        int outputLength = neurons.size();

        for (int i=0; i<inputValues.length; i++) {
            inputLayer.get(i).setInputValue(inputValues[i]);
        }

        //calculate the distance between the inputs vector and weights of each neuron from output layer
        double[] distances = processDistance(inputValues, neurons, distance);

        //search index of neuron which is closest to the input vector
        int indexMin = getIndexOfMinimumElement(distances);

        double[] distBetweenWinAndOther;

        List<NeuralLink> winNeuralLinks = neurons.get(indexMin).getInputLinks();
        double[] winWeights = new double[winNeuralLinks.size()];
        for (int i=0; i<winNeuralLinks.size(); i++) {
            winWeights[i] = winNeuralLinks.get(i).getWeight();
        }

        //calculate the distance between the winner neuron and each neuron from output layer
        distBetweenWinAndOther = processDistance(winWeights, neurons, distance);

        for (int i=0; i<outputLength; i++) {
            Neuron neuron = neurons.get(i);
            List<NeuralLink> neuralLinks = neuron.getInputLinks();
            for (NeuralLink link : neuralLinks) {
                link.setWeight(link.getWeight() + neighborhoodFunction.process(distBetweenWinAndOther[i], era) * a(era) *  (link.getInputNeuron().getOutputValue()-link.getWeight()));
            }
        }


            /*Neuron neuron = neurons.get(indexMin);
            List<NeuralLink> neuralLinks = neuron.getInputLinks();
            for (NeuralLink link : neuralLinks) {
                link.setWeight(link.getWeight() + era *  (link.getInputNeuron().getOutputValue()-link.getWeight()));
            }*/
        //}
    }

    private double a(double k) {
        return (double)2/(k*3);
    }

    private int getIndexOfMinimumElement(double[] elements) {
        int index = 0;
        for (int i=1; i<elements.length; i++) {
            if (elements[i] < elements[index]) {
                index = i;
            }
        }
        return index;
    }

    private double[] processDistance(double[] targetCoordinates, List<Neuron> neurons, DistanceFunction distance) {
        double[] result = new double[neurons.size()];
        for (int i=0; i < neurons.size(); i++) {
            List<NeuralLink> neuralLinks = neurons.get(i).getInputLinks();
            double[] weights = new double[neuralLinks.size()];
            for (int j=0; j<neuralLinks.size(); j++) {
                weights[j] = neuralLinks.get(j).getWeight();
            }
            result[i] = distance.distance(targetCoordinates, weights);
        }

        return result;
    }
}