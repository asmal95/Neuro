package ru.javazen.mind.neuro.neighborhood;

public interface NeighborhoodFunction {

    /**
     *
     * @param distance distance between the winner neuron and a neighboring neuron
     * @param time number of training phase
     * @return
     */
    double process(double distance, int time);
}
