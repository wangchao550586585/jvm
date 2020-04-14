package I_RunOptimization;

/**
 * Createpackage I_RunOptimization;
 * <p>
 * d by Administrator on 2017/6/9.
 */
public class Demo {
    public static final int NUM = 15000;


    public static int doubleValue(int i) {
        return i * 2;
    }

    public static long calcSum() {
        long sum = 0;
        for (int i = 1; i <= 100; i++) {
            sum += doubleValue(i);
        }
        return sum;
    }

    public static void main(String[] args) {
        for (int i = 0; i < NUM; i++) {
            long l = calcSum();
        }
    }

}
