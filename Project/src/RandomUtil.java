import java.util.Random;

public class RandomUtil {
    public static int seed = (int) (Math.random() * Integer.MAX_VALUE);;
    public static Random rand = new Random(seed);
}
