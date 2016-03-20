package ru.javazen.mind.neuro.education;

import ru.javazen.mind.neuro.network.Network;
import ru.javazen.mind.neuro.neuron.Neuron;
import ru.javazen.mind.neuro.neuron.link.NeuralLink;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerceptronTeacher implements Teacher {

    //use for save errors of neurons
    private Map<Neuron, Double> neuronErrors = new HashMap<>();

    @Override
    public void training(Network network, double[] inputValues, double[] expectedOutputValues) {
        double[] outputValues = network.process(inputValues);
        double[] errors = new double[expectedOutputValues.length];

        //// TODO: 20.03.2016  Unify the process of learning for multiple layers

        List<Neuron> outputLayer = network.getLayers().get(1);

        for (int i = 0; i < outputLayer.size(); i++) {
            errors[i] = (expectedOutputValues[i] - outputValues[i]) * outputLayer.get(i).getDerivativeValue();

            neuronErrors.put(outputLayer.get(i), errors[i]);

            for(int j = 0; j < outputLayer.get(i).getInputLinks().size(); j++) {
                double weightDelta = errors[i] * outputLayer.get(i).getInputLinks().get(j).getInputNeuron().getOutputValue();

                outputLayer.get(i).getInputLinks().get(j).setWeight(outputLayer.get(i).getInputLinks().get(j).getWeight() + weightDelta);
            }
        }

        List<Neuron> middleLayer = network.getLayers().get(0);

        for (int i = 0; i < middleLayer.size(); i++) {

            double errorSum = 0;

            for (NeuralLink link : middleLayer.get(i).getOutputLinks()) {
                errorSum += link.getWeight() * neuronErrors.get(link.getOutputNeuron());
            }

            double error = errorSum * middleLayer.get(i).getDerivativeValue();
            for(int j = 0; j < middleLayer.get(i).getInputLinks().size(); j++) {
                double weightDelta = error * middleLayer.get(i).getInputLinks().get(j).getInputNeuron().getOutputValue();

                middleLayer.get(i).getInputLinks().get(j).setWeight(middleLayer.get(i).getInputLinks().get(j).getWeight() + weightDelta);
            }
        }
    }
}
//w' = w + step * error_of_neuron * derivative_value_of_neuron * value_of_input_neuron