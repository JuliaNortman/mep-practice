package ua.knu.ynortman.utils;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

@Slf4j
public class FieldUtils {
    public static BigInteger gcd(BigInteger[] arr) {
        BigInteger result = BigInteger.ZERO;
        for(BigInteger element : arr) {
            result = result.gcd(element);
            if(BigInteger.ONE.equals(result)) {
                return BigInteger.ONE;
            }
        }
        return result;
    }

    public static BigInteger[] zeroArray(int n) {
        BigInteger[] arr = new BigInteger[n];
        for(int i = 0; i < n; ++i) {
            arr[i] = BigInteger.ZERO;
        }
        return arr;
    }

    public static boolean isZeroVector(BigInteger[] vector) {
        for(BigInteger el : vector) {
            if(!BigInteger.ZERO.equals(el)) {
                return false;
            }
        }
        return true;
    }

    public static BigInteger[] addVectors(BigInteger[] first, BigInteger[] second, BigInteger p) {
        BigInteger[] result = new BigInteger[first.length];
        for(int i = 0; i < first.length; ++i) {
            result[i] = first[i].add(second[i]).mod(p);
        }
        return result;
    }

    public static BigInteger[] multiplyVectorByConst(BigInteger[] vector, BigInteger cnst, BigInteger p) {
        if(BigInteger.ZERO.equals(cnst)) {
            return zeroArray(vector.length);
        }
        if(BigInteger.ONE.equals(cnst)) {
            return vector;
        }
        BigInteger[] result = new BigInteger[vector.length];
        for(int i = 0; i < vector.length; ++i) {
            result[i] = vector[i].multiply(cnst).mod(p);
        }
        return result;
    }

    public static BigInteger[][] matrixAddVectorColumn(BigInteger[][] matrix, BigInteger[] vector,
                                                       int colIndex, BigInteger p) {
        for(int i = 0; i < matrix.length; ++i) {
            matrix[i][colIndex] = matrix[i][colIndex].add(vector[i]).mod(p);
        }
        return matrix;
    }

    public static BigInteger[] substractVectors(BigInteger[] a, BigInteger[] b, BigInteger p) {
        BigInteger[] result = new BigInteger[a.length];
        for(int i = 0; i < a.length; ++i) {
            BigInteger s = a[i].subtract(b[i]);
            if(s.compareTo(BigInteger.ZERO) < 0) {
                s = p.add(s);
            }
            result[i] = s;
        }
        return result;
    }

    public static BigInteger[][] matrixAddVector(BigInteger[][] matrix, BigInteger[] vector, BigInteger p) {
        BigInteger[][] result = new BigInteger[matrix.length][matrix[0].length+1];
        for (int i = 0; i < matrix.length; ++i) {
            System.arraycopy(matrix[i], 0, result[i], 0, matrix[i].length);
            result[i][result.length-1] = vector[i];
        }
        return result;
    }

    public static BigInteger[][] operator(BigInteger[][] operator, BigInteger[][] matrix, BigInteger p) {
        return matrixMultiply(operator, matrix, p);
    }

    public static BigInteger[][] matrixMultiply(BigInteger[][] A, BigInteger[][] B, BigInteger p) {
        BigInteger[][] result = new BigInteger[B.length][B[0].length];
        for(int i = 0; i < B.length; ++i) {
            result[i] = FieldUtils.zeroArray(B[0].length);
        }
        for (int i = 0; i < A.length; ++i) {
             for (int j = 0; j < B[0].length; ++j) {
                 for(int k = 0; k < A[i].length; ++k) {
                     result[i][j] = result[i][j].add(A[i][k].multiply(B[k][j])).mod(p);
                 }
             }
         }
        return result;
    }

    public static BigInteger[][] rowToColumn(BigInteger[] row) {
        BigInteger[][] column = new BigInteger[row.length][1];
        for (int i = 0; i < row.length; ++i) {
            column[i][0] = row[i];
        }
        return column;
    }

    public static BigInteger[] columnToRow(BigInteger[][] column) {
        BigInteger[] row = new BigInteger[column.length];
        for(int i = 0; i < column.length; ++i) {
            row[i] = column[i][0];
        }
        return row;
    }

    public static BigInteger getRandomBigInteger(BigInteger p) {
        Random rand = new Random();
        return new BigInteger(p.bitLength(), rand).mod(p);
    }

    public static BigInteger complement(BigInteger n, BigInteger p) {
        return p.subtract(n);
    }

    public static String printMatrix(BigInteger[][] matrix, String title) {
        StringBuilder str = new StringBuilder(title + "\n");
        for(BigInteger[] row : matrix) {
            str.append(Arrays.toString(row));
            str.append("\n");
        }
        return str.toString();
    }

    public static String printVector(BigInteger[] vector, String title) {
        return title + "\n" + Arrays.toString(vector) +
                "\n";
    }

    public static BigInteger[][] nonsingularSquareMatrix(int n, BigInteger p) {
        BigInteger[][] matrix = upperTriangularMatrix(n, p);
        Random random = new Random();
        int iterations = random.nextInt(200 - 20) + 20;
        for(int i = 0; i < iterations; ++i) {
            int operation = random.nextInt(4);
            int j = random.nextInt(n);
            int k = random.nextInt(n);
            switch (operation) {
                case 0: {
                    //Interchange two rows
                    BigInteger[] tmp = matrix[j];
                    matrix[j] = matrix[k];
                    matrix[k] = tmp;
                    break;
                }
                case 1: {
                    //Multiply each element in a row by a non-zero number
                    BigInteger cnst = getRandomBigInteger(p);
                    if(BigInteger.ZERO.equals(cnst)) {
                        cnst = BigInteger.ONE;
                    }
                    BigInteger[] tmp = multiplyVectorByConst(matrix[j], cnst, p);
                    if(!isZeroVector(tmp)) {
                        matrix[j] = tmp;
                        break;
                    }
                }
                case 2: {
                    //Multiply a row by a non-zero number and add the result to another row
                    BigInteger cnst = getRandomBigInteger(p);
                    if(BigInteger.ZERO.equals(cnst)) {
                        cnst = BigInteger.ONE;
                    }
                    BigInteger[] tmp = addVectors(matrix[j],
                            multiplyVectorByConst(matrix[k], cnst, p), p);
                    if(!isZeroVector(tmp)) {
                        matrix[j] = tmp;
                        break;
                    }
                }
                case 3: {
                    //Multiply a row by a non-zero number and substract the result from another row
                    BigInteger cnst = getRandomBigInteger(p);
                    if(BigInteger.ZERO.equals(cnst)) {
                        cnst = BigInteger.ONE;
                    }
                    BigInteger[] tmp = substractVectors(matrix[j],
                            multiplyVectorByConst(matrix[k], cnst, p), p);
                    if(!isZeroVector(tmp)) {
                        matrix[j] = tmp;
                        break;
                    }
                }
            }
        }
        return matrix;
    }

    public static BigInteger[][] nonsingularMatrix(int m, int n, BigInteger p) {
        BigInteger[][] matrix = new BigInteger[m][n];
        BigInteger[][] squareMatrix = nonsingularSquareMatrix(m, p);
        for(int i = 0; i < m; ++i) {
            for(int j = 0; j < n; ++j) {
                if(j < n-m) {
                    matrix[i][j] = BigInteger.ONE;
                } else {
                    matrix[i][j] = squareMatrix[i][j - (n-m)];
                }
            }
        }
        return matrix;
    }

    public static BigInteger[] randomVector(int n, BigInteger p) {
        BigInteger[] vector = new BigInteger[n];
        for(int i = 0; i < n; ++i) {
            vector[i] = getRandomBigInteger(p);
        }
        BigInteger gcd = gcd(vector);
        if(!BigInteger.ONE.equals(gcd)) {
            for(int i = 0; i < n; ++i) {
                vector[i] = vector[i].divide(gcd);
            }
        }
        return vector;
    }

    @ToString
    @AllArgsConstructor
    public static class InvertibleTriangularMatrix{
        public final BigInteger[][] matrix;
        public final BigInteger[][] inverted;
    }

    public static InvertibleTriangularMatrix triangularInvertibleMatrix(int n, BigInteger p) {
        BigInteger[][] A = nonsingularSquareMatrix(n, p);
        //log.debug(Arrays.deepToString(A));
        BigInteger[][] B = nonsingularSquareMatrix(n, p);
        //log.debug(Arrays.deepToString(B));
        BigInteger[][] AB = matrixMultiply(A, B, p);
        BigInteger[][] matrix = new BigInteger[3*n][3*n];
        BigInteger[][] inverted = new BigInteger[3*n][3*n];
        for(int i = 0; i < matrix.length; ++i) {
            for(int j = 0; j < matrix[i].length; ++j) {
                if(i == j) {
                    matrix[i][j] = BigInteger.ONE;
                    inverted[i][j] = BigInteger.ONE;
                } else if(j >= n && j < 2 * n && i < n) {
                    matrix[i][j] = A[i][j-n];
                    inverted[i][j] = complement(A[i][j-n], p);
                } else if(j >= 2*n && j < 3 * n && i < 2*n && i >= n) {
                    matrix[i][j] = B[i-n][j-2*n];
                    inverted[i][j] = complement(B[i-n][j-2*n], p);
                } else if(j >= 2*n && j < 3*n && i < 2*n) {
                    matrix[i][j] = BigInteger.ZERO;
                    inverted[i][j] = AB[i][j-2*n];
                } else {
                    matrix[i][j] = BigInteger.ZERO;
                    inverted[i][j] = BigInteger.ZERO;
                }
            }
        }
        return new InvertibleTriangularMatrix(matrix, inverted);
    }

    private static BigInteger[][] upperTriangularMatrix(int n, BigInteger p) {
        BigInteger[][] matrix = new BigInteger[n][n];
        for(int i = 0; i < n; ++i) {
            for(int j = 0; j < n; ++j) {
                if(j < i) {
                    matrix[i][j] = BigInteger.ZERO;
                } else if(j ==i) {
                    BigInteger el = BigInteger.ZERO;
                    while (BigInteger.ZERO.equals(el)) {
                        el = getRandomBigInteger(p);
                    }
                    matrix[i][j] = el;
                } else {
                    matrix[i][j] = getRandomBigInteger(p);
                }
            }
        }
        return matrix;
    }


    public static BigInteger[][] copyMatrix(BigInteger[][] oldMatrix) {
        BigInteger[][] newMatrix = new BigInteger[oldMatrix.length][oldMatrix[0].length];
        for(int i=0; i<oldMatrix.length; i++) {
            System.arraycopy(oldMatrix[i], 0, newMatrix[i], 0, oldMatrix[i].length);
        }
        return newMatrix;
    }

}
