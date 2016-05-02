package ru.javazen.mind.neuro.network;

import ru.javazen.mind.neuro.distance.DistanceFunction;
import ru.javazen.mind.neuro.function.ActivationFunction;
import ru.javazen.mind.neuro.neighborhood.NeighborhoodFunction;
import ru.javazen.mind.neuro.neuron.Neuron;
import ru.javazen.mind.neuro.neuron.link.NeuralLink;

import java.util.List;

public class KohonenNetwork extends MultiLayerNetwork {

    private NeighborhoodFunction neighborhoodFunction;

    public KohonenNetwork(ActivationFunction activationFunction, NeighborhoodFunction neighborhoodFunction, int inputCount, int outputCount) {
        super(activationFunction, inputCount, outputCount);
        this.neighborhoodFunction = neighborhoodFunction;
    }

    public void training(double[] inputValues, DistanceFunction distance, int era){
        if (inputValues.length != inputLayer.size()) {
            throw new IllegalArgumentException();
        }

        //gets a single layer of neurons
        List<Neuron> neurons = this.getLayers().get(0);
        int outputLength = neurons.size();

        for (int i=0; i<inputValues.length; i++) {
            inputLayer.get(i).setInputValue(inputValues[i]);
        }

        double[] distances = processDistance(inputValues, neurons, distance);

        int indexMin = getIndexOfMinimumElement(distances);

        double[] distBetweenWinAndOther;

        List<NeuralLink> winNeuralLinks = neurons.get(indexMin).getInputLinks();
        double[] winWeights = new double[winNeuralLinks.size()];
        for (int i=0; i<winNeuralLinks.size(); i++) {
            winWeights[i] = winNeuralLinks.get(i).getWeight();
        }

        distBetweenWinAndOther = processDistance(winWeights, neurons, distance);

        for (int i=0; i<outputLength; i++) {
            if (i!=indexMin) continue;
            Neuron neuron = neurons.get(i);
            List<NeuralLink> neuralLinks = neuron.getInputLinks();
            for (NeuralLink link : neuralLinks) {
                link.setWeight(link.getWeight() + neighborhoodFunction.process(distBetweenWinAndOther[i], era) * a(era) *  (link.getInputNeuron().getOutputValue()-link.getWeight())/*distances[indexMin]*/);
            }
        }
    }
    private double sigma(int t) {
        return 1/Math.exp(Math.pow(t, -2));
    }
    private double a(int k) {
        return (double)1/(k*2);
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