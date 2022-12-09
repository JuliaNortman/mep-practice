package ua.knu.ynortman.tss.field;

import lombok.extern.slf4j.Slf4j;
import ua.knu.ynortman.utils.FieldUtils;

import java.math.BigInteger;

@Slf4j
public class HLDE {
    public static BigInteger[][] basis(BigInteger[] hlde, BigInteger p) {
        int n = hlde.length;
        BigInteger[][] basis = new BigInteger[n-1][n];
        BigInteger complement = BigInteger.ZERO;
        int j = 0; //complement position
        for(int i = 0; i < n; ++i) {
            BigInteger a = hlde[i];
            if(BigInteger.ZERO.equals(a)) {
                if(BigInteger.ZERO.equals(complement)) {
                    basis[i] = canonicalVector(i, n);
                } else {
                    basis[i-1] = canonicalVector(i, n);
                }
            } else {
                if(BigInteger.ZERO.equals(complement)) {
                    complement = FieldUtils.complement(a, p);
                    j = i;
                } else {
                    basis[i-1] = basisVector(a, i, complement, j, n);
                }
            }
        }
        return basis;
    }

    public static BigInteger[][] ringBasis(BigInteger[] hlde, BigInteger p) {
        int n = hlde.length;
        BigInteger[][] basis = new BigInteger[n-1][n];
        BigInteger complement = BigInteger.ZERO;
        int j = 0; //complement position
        for(int i = 0; i < n; ++i) {
            if(BigInteger.ONE.equals(FieldUtils.gcd(new BigInteger[]{hlde[i], p}))) {
                complement = FieldUtils.complement(hlde[i], p);
                j = i;
                break;
            }
        }

        for(int i = 0; i < n; ++i) {
            BigInteger a = hlde[i];
            if(BigInteger.ZERO.equals(a)) {
                if(i < j) {
                    basis[i] = canonicalVector(i, n);
                } else {
                    basis[i-1] = canonicalVector(i, n);
                }
            } else {
                if(i < j) {
                    basis[i] = basisVector(a, i, complement, j, n);
                } else if(i > j) {
                    basis[i-1] = basisVector(a, i, complement, j, n);
                }
            }
        }
        return basis;
    }

    protected static BigInteger[] canonicalVector(int j, int n) {
        BigInteger[] vector = FieldUtils.zeroArray(n);
        vector[j] = BigInteger.ONE;
        return vector;
    }

    protected static BigInteger[] basisVector(BigInteger a, int j, BigInteger complement, int k, int n) {
        BigInteger[] vector = FieldUtils.zeroArray(n);
        BigInteger gcd = FieldUtils.gcd(new BigInteger[] {a, complement});
        if(BigInteger.ONE.equals(gcd)) {
            vector[k] = a;
            vector[j] = complement;
        } else {
            vector[k] = a.divide(gcd);
            vector[j] = complement.divide(gcd);
        }
        return vector;
    }


}
