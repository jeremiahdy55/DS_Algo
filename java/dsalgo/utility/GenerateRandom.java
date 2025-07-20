package dsalgo.utility;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class GenerateRandom {

    // Create an int[] of size {amoount} with random values from [{min}, {max}]
    public static Integer[] intArray(int min, int max, int amount) {
        return IntStream.generate(() -> (min + (int)(Math.random() * ((max - min) + 1)))).limit(amount).boxed().toArray(Integer[]::new);
    }

    // Create an double[] of size {amoount} with random values from [{min}, {max}]
    public static Double[] doubleArray(double min, double max, int amount) {
        return DoubleStream.generate(() -> (min + (Math.random() * (max - min)))).limit(amount).boxed().toArray(Double[]::new);
    }

}
