package ru.javazen.mind.neuro;

import ru.javazen.mind.neuro.distance.EuclideanDistance;
import ru.javazen.mind.neuro.education.BackpropagationTeacher;
import ru.javazen.mind.neuro.education.Teacher;
import ru.javazen.mind.neuro.function.SigmoidFunction;
import ru.javazen.mind.neuro.neighborhood.ConstantFunction;
import ru.javazen.mind.neuro.neighborhood.GaussianFunction;
import ru.javazen.mind.neuro.network.KohonenNetwork;
import ru.javazen.mind.neuro.network.MultiLayerNetwork;

public class Main {

    public static void main(String[] args) {
        KohonenNetwork network = new KohonenNetwork(SigmoidFunction.getInstance(), new ConstantFunction(), 2, 2);

        double[][] inputs = new double[][]{
                {0.11, 0.12},
                {0.15, 0.12},
                {0.09, 0.07},
                {0.21, 0.31},
                {0.31, 0.21},
                {0.4, 0.3},
                {0.6, 0.65},
                {0.89, 0.69},
                {0.94, 0.83},
                {0.71, 0.61},
                {0.95, 0.81},
                {0.59, 0.62},
                {0.75, 0.86},
        };

        int count = 1;

        while (true) {
            for (int i = 0; i< inputs.length; i++) {
                network.training(inputs[i], new EuclideanDistance(), count);
            }

            if (count++ > 10) {
                break;
            }
        }

        double[] res = network.process(new double[]
                {0.13, 0.23});

        for(double out : res) {
            System.out.println(/*Math.round*/(out));
        }
        System.out.println();


        res = network.process(new double[]
                {0.4, 0.19});

        for(double out : res) {
            System.out.println(/*Math.round*/(out));
        }
        System.out.println();



        res = network.process(new double[]
                {0.78, 0.89});

        for(double out : res) {
            System.out.println(/*Math.round*/(out));
        }
        System.out.println();


        res = network.process(new double[]
                {0.89, 0.95});

        for(double out : res) {
            System.out.println(/*Math.round*/(out));
        }
        System.out.println();
    }
}
