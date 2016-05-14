package ru.javazen.mind.neuro;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.javazen.mind.neuro.education.BackpropagationTeacher;
import ru.javazen.mind.neuro.activation.SigmoidFunction;
import ru.javazen.mind.neuro.network.MultiLayerNetwork;
import ru.javazen.mind.neuro.network.PerceptronNetwork;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class PerceptronXORTest {

    private static PerceptronNetwork perceptronNetwork;
    private static final double MAX_ERROR = 0.1;

    private double[] input;
    private double expectedOutput;

    @BeforeClass
    public static void setUp() throws Exception {
        perceptronNetwork = new PerceptronNetwork(SigmoidFunction.getInstance(), 2, 3, 1);
        BackpropagationTeacher teacher = new BackpropagationTeacher();
        for (int i = 0; i < 2000; i++) {
            for (Object[] params : data()) {
                double[] input = (double[]) params[0];
                double expectedOutput = (double) params[1];
                teacher.training(perceptronNetwork, input, new double[]{expectedOutput});
            }
        }
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[]{new double[]{1,1}, 0d},
                new Object[]{new double[]{0,1}, 1d},
                new Object[]{new double[]{1,0}, 1d},
                new Object[]{new double[]{0,0}, 0d}
        );
    }

    public PerceptronXORTest(double[] input, double expectedOutput) {
        this.input = input;
        this.expectedOutput = expectedOutput;
    }

    @Test
    public void process() throws Exception {
        System.out.println("Input: " + Arrays.toString(input));
        double actualOutput = perceptronNetwork.process(input)[0];
        double error = Math.abs(actualOutput - expectedOutput);

        System.out.println("Expected: " + expectedOutput);
        System.out.println("Actual: " + actualOutput);
        System.out.println("Error: " + error);
        Assert.assertTrue(error < MAX_ERROR);
    }
}
