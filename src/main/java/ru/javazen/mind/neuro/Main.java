package ru.javazen.mind.neuro;

import ru.javazen.mind.neuro.education.PerceptronTeacher;
import ru.javazen.mind.neuro.education.Teacher;
import ru.javazen.mind.neuro.function.SigmoidFunction;
import ru.javazen.mind.neuro.network.PerceptronNetwork;

public class Main {

    public static void main(String[] args) {
        PerceptronNetwork network = new PerceptronNetwork(2, 2, 1, SigmoidFunction.getInstance());

        double[][] inputs = new double[][]          {{1, 1}, {0, 1}, {1, 0}, {0, 0}};
        double[][] expectedOutputs = new double[][] {{0},    {1},    {1},    {0}}; //XOR

        Teacher teacher = new PerceptronTeacher();

        int count = 0;

        while (true) {
            for (int i = 0; i< inputs.length; i++) {
                teacher.training(network, inputs[i], expectedOutputs[i]);
            }

            if (count++ > 10000) {
                break;
            }
        }

        double[] res = network.process(new double[]{1, 0});
        System.out.println("1, 0");
        for(double out : res) {
            System.out.println(out);
        }
        System.out.println();

        res = network.process(new double[]{0, 1});
        System.out.println("0, 1");
        for(double out : res) {
            System.out.println(out);
        }
        System.out.println();

        res = network.process(new double[]{1, 1});
        System.out.println("1, 1");
        for(double out : res) {
            System.out.println(out);
        }
        System.out.println();

        res = network.process(new double[]{0, 0});
        System.out.println("0, 0");
        for(double out : res) {
            System.out.println(out);
        }
    }
}
