package solar;

public class Scalar {
    final static double logBase = 1000;
    final static double power = 5;

    public static double scaleMeter(double value){
        double res = Math.log10(value) / Math.log10(logBase);

        return Math.pow(res, power);
    }
    public static double scaleSecond(double value){
        double res = Math.log10(value);

        return res;
    }
}
