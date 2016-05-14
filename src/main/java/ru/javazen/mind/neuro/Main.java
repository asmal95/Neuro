package ru.javazen.mind.neuro;

import ru.javazen.mind.neuro.activation.BipolarSigmoidFunction;
import ru.javazen.mind.neuro.distance.*;
import ru.javazen.mind.neuro.activation.SigmoidFunction;
import ru.javazen.mind.neuro.neighborhood.ConstantFunction;
import ru.javazen.mind.neuro.neighborhood.GaussianFunction;
import ru.javazen.mind.neuro.network.KohonenNetwork;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        KohonenNetwork network = new KohonenNetwork(new EuclideanDistance(), new GaussianFunction(), 20, 5);

        Map<String, List<Double>> eduMap = loadSet("animals.set", 20);

        /*double era = 0.8;
        while (era < 3) {
            era += 0.1;
            for (List<Double> v : eduMap.values()) {
                double[] doubles = new double[v.size()];
                for (int i=0; i<v.size(); i++) {
                    doubles[i] = v.get(i);
                }
                training(doubles, era);
            }
        }*/
        double[][] inputVector = new double[eduMap.size()][];
        int count = 0;
        for (List<Double> v : eduMap.values()) {
            inputVector[count] = new double[v.size()];
            for (int i=0; i<v.size(); i++) {
                inputVector[count][i] = v.get(i);
            }
            count++;
        }
        network.training(inputVector);

        double[] res;

        Map<String, List<Double>> testMap = loadSet("test_animals.set", 20);

        for (String key : testMap.keySet()) {
            List<Double> v = testMap.get(key);
            double[] doubles = new double[v.size()];
            for (int i=0; i<v.size(); i++) {
                doubles[i] = v.get(i);
            }
            res = network.process(doubles, true);

            System.out.printf("%15s ", key, " ");
            for(double out : res) {
                System.out.print(/*Math.round*/(out) + " \t");
            }
            System.out.println();
        }

    }

    private static Map<String, List<Double>> loadSet(String name, int propsCount) {
        Map<String, List<Double>> map = new HashMap<>(); //Name <list of values>
        ClassLoader classLoader = Main.class.getClassLoader();
        File eduSet = new File(classLoader.getResource(name).getFile());
        try(BufferedReader reader = new BufferedReader(new FileReader(eduSet))) {
            String str;
            while ((str = reader.readLine()) != null) {
                if (str.trim().isEmpty()) { continue; }
                StringTokenizer tokenizer = new StringTokenizer(str);
                String first = tokenizer.nextToken();
                if (first.startsWith("#")) { continue; }
                List<Double> values = new ArrayList<>(20);
                map.put(first, values);
                for (int i=0; i<propsCount; i++) {
                    values.add(Double.parseDouble(tokenizer.nextToken()));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return map;
    }
}
