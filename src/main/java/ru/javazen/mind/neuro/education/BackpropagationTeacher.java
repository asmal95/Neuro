package ru.javazen.mind.neuro.education;

import ru.javazen.mind.neuro.network.PerceptronNetwork;
import ru.javazen.mind.neuro.neuron.ArtificialNeuron;
import ru.javazen.mind.neuro.neuron.Neuron;
import ru.javazen.mind.neuro.neuron.link.NeuralLink;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackpropagationTeacher implements Teacher<PerceptronNetwork> {

    //use for save errors of neurons
    private Map<Neuron, Double> neuronErrors = new HashMap<>();

    @Override
    public void training(PerceptronNetwork network, double[] inputValues, double[] expectedOutputValues) {
        training(network, inputValues, expectedOutputValues, 1);
    }

    @Override
    public void training(PerceptronNetwork network, double[] inputValues, double[] expectedOutputValues, double learningRate) {
        double[] outputValues = network.process(inputValues);
        double[] errors = new double[expectedOutputValues.length];

        List<ArtificialNeuron> outputLayer = network.getLayers().get(network.getLayers().size() - 1);

        for (int i = 0; i < outputLayer.size(); i++) {
            errors[i] = (expectedOutputValues[i] - outputValues[i]) * outputLayer.get(i).getDerivativeValue();

            neuronErrors.put(outputLayer.get(i), errors[i]);

            for(int j = 0; j < outputLayer.get(i).getInputLinks().size(); j++) {
                double weightDelta = learningRate * errors[i] * outputLayer.get(i).getInputLinks().get(j).getInputNeuron().getOutputValue();

                outputLayer.get(i).getInputLinks().get(j).setWeight(outputLayer.get(i).getInputLinks().get(j).getWeight() + weightDelta);
            }
        }



        int countOfHiddenLayers = network.getLayers().size() - 1; //layers without output layer

        for (int i = countOfHiddenLayers - 1; i >= 0; i--) {
            List<ArtificialNeuron> middleLayer = network.getLayers().get(i);
            for (int j = 0; j < middleLayer.size(); j++) {

                double errorSum = 0;

                for (NeuralLink link : middleLayer.get(j).getOutputLinks()) {
                    errorSum += link.getWeight() * neuronErrors.get(link.getOutputNeuron());
                }

                double error = errorSum * middleLayer.get(j).getDerivativeValue();
                neuronErrors.put(middleLayer.get(j), error);
                for (int k = 0; k < middleLayer.get(j).getInputLinks().size(); k++) {
                    double weightDelta = learningRate * error * middleLayer.get(j).getInputLinks().get(k).getInputNeuron().getOutputValue();

                    middleLayer.get(j).getInputLinks().get(k).setWeight(middleLayer.get(j).getInputLinks().get(k).getWeight() + weightDelta);
                }
            }
        }
    }
}
//w' = w + step * error_of_neuron * derivative_value_of_neuron * value_of_input_neuron