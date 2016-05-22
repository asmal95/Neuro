package ru.javazen.mind.neuro.network;

import ru.javazen.mind.neuro.activation.ActivationFunction;
import ru.javazen.mind.neuro.neuron.ArtificialNeuron;
import ru.javazen.mind.neuro.neuron.Neuron;
import ru.javazen.mind.neuro.neuron.factory.ArtificialNeuronFactory;
import ru.javazen.mind.neuro.neuron.link.NeuralLink;

import java.util.ArrayList;
import java.util.List;


public class PerceptronNetwork extends MultiLayerNetwork<ArtificialNeuron> {

    public PerceptronNetwork(ActivationFunction function, int inputCount, int ... layersCount) {
        super(new ArtificialNeuronFactory(function), inputCount, layersCount);
    }

    public PerceptronNetwork(PerceptronNetwork network, int startLayer, int endLayer) {
        super(network.getNeuronFactory(), network.layers.get(startLayer).size());
        List<? extends Neuron> inputs = inputLayer;

        for (int i=0; i<endLayer-startLayer; i++) {
            List<ArtificialNeuron> layerCopyFrom = network.getLayers().get(startLayer+i);
            int count = layerCopyFrom.size();

            this.layers.add(new ArrayList<>(count));

            for (int j=0; j<count; j++) {
                ArtificialNeuron neuron = neuronFactory.createNeuron();

                for (int k=0; k<inputs.size(); k++) {
                    Neuron inputNeuron = inputs.get(k);
                    NeuralLink link = new NeuralLink();

                    link.setWeight(layerCopyFrom.get(j).getInputLinks().get(k).getWeight());

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
    protected double getInitWeightValue() {
        return RANDOM.nextDouble() - 0.5;
    }
}