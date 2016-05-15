package ru.javazen.mind.neuro.network;

import ru.javazen.mind.neuro.distance.DistanceFunction;
import ru.javazen.mind.neuro.neighborhood.NeighborhoodFunction;
import ru.javazen.mind.neuro.neuron.DistanceNeuron;
import ru.javazen.mind.neuro.neuron.Neuron;
import ru.javazen.mind.neuro.neuron.factory.DistanceNeuronFactory;
import ru.javazen.mind.neuro.neuron.link.NeuralLink;

import java.util.Arrays;
import java.util.List;

public class KohonenNetwork extends MultiLayerNetwork<DistanceNeuron> {

    private NeighborhoodFunction neighborhoodFunction;
    private DistanceFunction distanceFunction;
    private double potentialMinimum = 0.75;

    public KohonenNetwork(DistanceFunction distanceFunction, NeighborhoodFunction neighborhoodFunction, int inputCount, int outputCount) {
        super(new DistanceNeuronFactory(distanceFunction), inputCount, outputCount);
        this.neighborhoodFunction = neighborhoodFunction;
        this.distanceFunction = distanceFunction;

        //set potentials: p=1/n
        for (DistanceNeuron neuron : getLayers().get(0)) {
            neuron.setPotential(1d/outputCount);
        }
    }

    public double[] process(double[] inputValues, boolean normalize) {
        double[] result = super.process(inputValues);
        if (normalize) {
            double winnerValue = Arrays.stream(result).min().getAsDouble();
            for (int i = 0; i < result.length; i++) {
                result[i] = result[i] == winnerValue ? 1 : 0;
            }
        }
        return result;
    }

    public void training(double[][] inputValues) {
        double era = 0.8;
        while (era < 3) {
            era += 0.1;
            for (double[] inputVector : inputValues) {
                training(inputVector, era);
            }
        }
    }

    public void training(double[] inputValues, double era){
        if (inputValues.length != inputLayer.size()) {
            throw new IllegalArgumentException();
        }

        //gets a single layer of neurons
        List<DistanceNeuron> neurons = this.getLayers().get(0);
        int outputLength = neurons.size();

        for (int i=0; i<inputValues.length; i++) {
            inputLayer.get(i).setInputValue(inputValues[i]);
        }

        //calculate the distance between the inputs vector and weights of each neuron from output layer
        double[] distances = processDistance(inputValues, neurons, distanceFunction);

        //search index of neuron which is closest to the input vector
        int indexMin = getIndexOfMinimumElement(distances);

        double[] distBetweenWinAndOther;

        List<NeuralLink> winNeuralLinks = neurons.get(indexMin).getInputLinks();
        double[] winWeights = new double[winNeuralLinks.size()];
        for (int i=0; i<winNeuralLinks.size(); i++) {
            winWeights[i] = winNeuralLinks.get(i).getWeight();
        }

        //calculate the distance between the winner neuron and each neuron from output layer
        distBetweenWinAndOther = processDistance(winWeights, neurons, distanceFunction);

        for (int i=0; i<outputLength; i++) {
            DistanceNeuron neuron = neurons.get(i);
            List<NeuralLink> neuralLinks = neuron.getInputLinks();

            if (neuron.getPotential() > potentialMinimum) {
                for (NeuralLink link : neuralLinks) {
                    link.setWeight(link.getWeight() + neighborhoodFunction.process(distBetweenWinAndOther[i], era) * a(era) * (link.getInputNeuron().getOutputValue() - link.getWeight()));
                }
            }

            //corrected potential
            neuron.setPotential(neuron.getPotential() + 1d/outputLength);
            if (indexMin==i) {
                neuron.setPotential(neuron.getPotential() - potentialMinimum);
            }
        }
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

    private double[] processDistance(double[] targetCoordinates, List<DistanceNeuron> neurons, DistanceFunction distance) {
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

    public double getPotentialMinimum() {
        return potentialMinimum;
    }

    public void setPotentialMinimum(double potentialMinimum) {
        this.potentialMinimum = potentialMinimum;
    }
}