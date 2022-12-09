package ua.knu.ynortman.tss.field;

import ua.knu.ynortman.utils.FieldUtils;

import java.math.BigInteger;

public class HLDESystem {
    public static BigInteger[] constructHlde(BigInteger[] equation, BigInteger[][] basis, BigInteger p) {
        BigInteger[] quotients = new BigInteger[basis.length];
        boolean inconsistent = true;
        for (int j = 0; j < basis.length; ++j) {
            BigInteger[] vector = basis[j];
            BigInteger q = BigInteger.ZERO;
            for(int i = 0; i < vector.length; ++i) {
                BigInteger element = vector[i];
                if(!element.equals(BigInteger.ZERO)) {
                    q = q.add(equation[i].multiply(element));
                }
            }
            quotients[j] = q.mod(p);
            if(!quotients[j].equals(BigInteger.ZERO)) {
                inconsistent = false;
            }
        }
        if (inconsistent) {
            throw new RuntimeException("The system is inconsistent!");
        }
        return quotients;
    }

    public static BigInteger[][] calculateBasis(BigInteger[][] firstBasis, BigInteger[][] secondBasis, BigInteger p) {
        BigInteger[][] basis = new BigInteger[secondBasis.length][firstBasis[0].length];
        for(int i = 0; i < secondBasis.length; ++i) {
            BigInteger[] vector = secondBasis[i];
            BigInteger[] resultVector = null;
            for(int j = 0; j < vector.length; ++j) {
                if(!BigInteger.ZERO.equals(vector[j])) {
                    if(resultVector == null) {
                        resultVector = FieldUtils.multiplyVectorByConst(
                                firstBasis[j], vector[j], p
                        );
                    } else {
                        resultVector = FieldUtils.addVectors(
                                resultVector,
                                FieldUtils.multiplyVectorByConst(firstBasis[j], vector[j], p),
                                p
                        );
                    }
                }
            }
            basis[i] = resultVector;
        }
        return basis;
    }

    public static BigInteger[][]  systemBasis(BigInteger[][] system, BigInteger p) {
        BigInteger[][] basis = HLDE.basis(system[0], p);
        for(int i = 1; i < system.length; ++i) {
            BigInteger[] hlde = constructHlde(system[i], basis, p);
            BigInteger[][] secondBasis = HLDE.basis(hlde, p);
            basis = calculateBasis(basis, secondBasis, p);
        }
        return basis;
    }
}
