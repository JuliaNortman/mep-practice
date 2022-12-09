package ua.knu.ynortman.tss.field;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import ua.knu.ynortman.utils.FieldUtils;

import java.math.BigInteger;

@Slf4j
public class NHLDESystem {

    @ToString
    public static class NHLDESystemTransform {
        public final BigInteger[][] hldeSystem;
        public final BigInteger[] nhlde;
        public final BigInteger b;

        public NHLDESystemTransform(BigInteger[][] hldeSystem, BigInteger[] nhlde, BigInteger b) {
            this.hldeSystem = hldeSystem;
            this.nhlde = nhlde;
            this.b = b;
        }
    }

    public static NHLDESystemTransform transform(BigInteger[][] a, BigInteger[] b, BigInteger p) {
        BigInteger[][] aCopy = FieldUtils.copyMatrix(a);
        int pivot = 0;
        int nonZeroRows = 0;
        while (nonZeroRows != 1) {
            nonZeroRows = 0;
            for (int i = 0; i < b.length; ++i) {
                if (!BigInteger.ZERO.equals(b[i])) {
                    nonZeroRows++;
                    if (b[i].compareTo(b[pivot]) < 0 || BigInteger.ZERO.equals(b[pivot])) {
                        pivot = i;
                    }
                }
            }
            for (int i = 0; i < b.length; ++i) {
                if (i != pivot && !BigInteger.ZERO.equals(b[i])) {
                    BigInteger k = b[i].divide(b[pivot]);
                    b[i] = b[i].subtract(b[pivot].multiply(k));
                    aCopy[i] = FieldUtils.substractVectors(aCopy[i], FieldUtils.multiplyVectorByConst(aCopy[pivot], k, p), p);
                }
            }
        }
        BigInteger[][] hldeSystem = new BigInteger[aCopy.length-1][aCopy[0].length];
        for (int i = 0; i < aCopy.length; ++i) {
            if(i < pivot) {
                hldeSystem[i] = aCopy[i];
            }
            else if(i > pivot) {
                hldeSystem[i-1] = aCopy[i];
            }
        }
        return new NHLDESystemTransform(hldeSystem, aCopy[pivot], b[pivot]);
    }

    public static BigInteger[][] generalSolution(BigInteger[][] nhlde, BigInteger[] b, BigInteger p) {
        NHLDESystemTransform transformed = transform(nhlde, b, p);
        BigInteger[][] hldeSystemBasis = HLDESystem.systemBasis(transformed.hldeSystem, p);
        BigInteger[] hlde = HLDESystem.constructHlde(transformed.nhlde, hldeSystemBasis, p);
        BigInteger[] nhldeSolution = NHLDE.solution(hlde, transformed.b, p);
        BigInteger[] particularSolution = FieldUtils.zeroArray(hldeSystemBasis[0].length);
        for(int i = 0; i < nhldeSolution.length; ++i) {
            particularSolution = FieldUtils.addVectors(particularSolution,
                    FieldUtils.multiplyVectorByConst(hldeSystemBasis[i], nhldeSolution[i], p), p);
        }
        BigInteger[][] correspondingHldeBasis = HLDESystem.systemBasis(nhlde, p);
        BigInteger[][] generalSolution = new BigInteger[correspondingHldeBasis.length+1][particularSolution.length];
        generalSolution[0] = particularSolution;
        System.arraycopy(correspondingHldeBasis, 0, generalSolution, 1, correspondingHldeBasis.length);
        //log.debug("General solution: {}", FieldUtils.printMatrix(generalSolution, ""));
        return generalSolution;
    }

    public static boolean verifySolution(BigInteger[][] nhlde, BigInteger[] b,
                                         BigInteger p, BigInteger[][] generalSolution, BigInteger[] solution) {

        for(int i = 0; i < nhlde.length; ++i) {
            BigInteger res = BigInteger.ZERO;
            for(int j = 0; j < nhlde[i].length; ++j) {
                res = res.add(nhlde[i][j].multiply(solution[j]).mod(p)).mod(p);
            }
            if(!res.equals(b[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean verifySolution(BigInteger[][] nhlde, BigInteger[] b,
                                         BigInteger p, BigInteger[][] generalSolution) {
        BigInteger[] vector = FieldUtils.randomVector(generalSolution.length-1, p);
        //log.debug("Random vector is: " + Arrays.toString(vector));
        BigInteger[] solution = generalSolution[0];
        for(int i = 1; i < generalSolution.length; ++i) {
            solution = FieldUtils.addVectors(solution,
                    FieldUtils.multiplyVectorByConst(generalSolution[i], vector[i-1], p), p);
        }
        return verifySolution(nhlde, b, p, generalSolution, solution);
    }

    public static BigInteger[] solution(BigInteger[][] generalSolution, BigInteger p) {
        BigInteger[] vector = FieldUtils.randomVector(generalSolution.length-1, p);
        BigInteger[] solution = generalSolution[0];
        for(int i = 1; i < generalSolution.length; ++i) {
            solution = FieldUtils.addVectors(solution,
                    FieldUtils.multiplyVectorByConst(generalSolution[i], vector[i-1], p), p);
        }
        return solution;
    }
}
