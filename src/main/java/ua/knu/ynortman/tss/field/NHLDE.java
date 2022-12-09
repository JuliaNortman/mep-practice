package ua.knu.ynortman.tss.field;

import ua.knu.ynortman.utils.FieldUtils;

import java.math.BigInteger;

public class NHLDE {
    public static BigInteger[] solution(BigInteger[] nhlde, BigInteger b, BigInteger p) {

        BigInteger coef = null;
        int index = 0;
        for(int i = 0; i < nhlde.length; ++i) {
            BigInteger element = nhlde[i];
            if(!BigInteger.ZERO.equals(element)) {
                coef = element;
                index = i;
                break;
            }
        }
        BigInteger s = coef.modInverse(p);
        BigInteger[] solution = FieldUtils.zeroArray(nhlde.length);
        solution[index] = b.multiply(s).mod(p);
        return solution;
    }
}
