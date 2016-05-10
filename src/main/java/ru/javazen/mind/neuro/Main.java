package ru.javazen.mind.neuro;

import ru.javazen.mind.neuro.activation.BipolarSigmoidFunction;
import ru.javazen.mind.neuro.distance.*;
import ru.javazen.mind.neuro.activation.SigmoidFunction;
import ru.javazen.mind.neuro.neighborhood.ConstantFunction;
import ru.javazen.mind.neuro.neighborhood.GaussianFunction;
import ru.javazen.mind.neuro.network.KohonenNetwork;

public class Main {

    public static void main(String[] args) {
        KohonenNetwork network = new KohonenNetwork(BipolarSigmoidFunction.getInstance(), new GaussianFunction(), 36, 3);

        double[][] inputs = new double[][]{
                {
                        1, 1, 0, 0, 0, 0,
                        1, 1, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                },
                {
                        1, 1, 0, 0, 0, 0,
                        0, 1, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                },
                {
                        1, 1, 1, 0, 0, 0,
                        1, 1, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                },
                {
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 1,
                        0, 0, 0, 0, 1, 1,
                },
                {
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 1, 1,
                        0, 0, 0, 0, 1, 1,
                },
                {
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 1, 1,
                        0, 0, 0, 1, 0, 1,
                },
                {
                        0, 0, 0, 0, 1, 1,
                        0, 0, 0, 0, 1, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                },
                {
                        0, 0, 0, 0, 1, 1,
                        0, 0, 0, 0, 1, 1,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                },
                {
                        0, 0, 0, 0, 1, 0,
                        0, 0, 0, 0, 1, 1,
                        0, 0, 0, 0, 1, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                },
                /*{
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                },*/
        };

        int count = 1;

        while (true) {
            for (int i = 0; i< inputs.length; i++) {
                network.training(inputs[i], new EuclideanDistance(), count);
            }

            if (count++ > 200) {
                break;
            }
        }

        double[] res = network.process(new double[]
                {
                        0, 0, 0, 0, 1, 1,
                        0, 0, 0, 0, 1, 1,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                });

        for(double out : res) {
            System.out.print(Math.round(out) + " ");
        }
        System.out.println();

        res = network.process(new double[]
                {
                        1, 1, 0, 0, 0, 0,
                        1, 1, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                });

        for(double out : res) {
            System.out.print(Math.round(out) + " ");
        }
        System.out.println();


        res = network.process(new double[]
                {
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 1, 1,
                        0, 0, 0, 0, 1, 1,
                });

        for(double out : res) {
            System.out.print(Math.round(out) + " ");
        }
        System.out.println();


    }
}
