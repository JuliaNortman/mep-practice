package ua.knu.ynortman.tss;

import lombok.extern.slf4j.Slf4j;
import ua.knu.ynortman.tss.field.HLDE;
import ua.knu.ynortman.tss.field.NHLDESystem;
import ua.knu.ynortman.utils.FieldUtils;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Arrays;

@Slf4j
public class Main {
    public static void main(String[] args) {
        /*HLDE tss = new HLDE();
        HLDESystem system = new HLDESystem();
        BigInteger[][] basis = HLDE.basis(
                new BigInteger[]{
                        BigInteger.TWO,
                        BigInteger.ONE,
                        BigInteger.ZERO,
                        BigInteger.ONE,
                        BigInteger.TWO
                }, BigInteger.valueOf(3)
        );
        BigInteger[] hlde2 = system.constructHlde(new BigInteger[] {
                BigInteger.ONE,
                BigInteger.TWO,
                BigInteger.ONE,
                BigInteger.ZERO,
                BigInteger.ONE
        }, basis, BigInteger.valueOf(3));

        BigInteger[][] basis2 = tss.basis(hlde2, BigInteger.valueOf(3));
        BigInteger[][] systemBasis = system.calculateBasis(basis, basis2, BigInteger.valueOf(3));
        System.out.println(Arrays.deepToString(basis));
        //System.out.println(Arrays.deepToString(hlde2));
        System.out.println(Arrays.deepToString(basis2));
        System.out.println(Arrays.deepToString(systemBasis));*/

        /*System.out.println(Arrays.deepToString(tss.HLDEBasis(
                new BigInteger[]{
                        BigInteger.ZERO,
                        BigInteger.ONE,
                        BigInteger.ZERO,
                        BigInteger.ONE
                }, BigInteger.valueOf(3)
        )));*/
        /*BigInteger[] partHLDESolution = NHLDE.solution(
                new BigInteger[] {
                        BigInteger.valueOf(2),
                        BigInteger.valueOf(3),
                        BigInteger.valueOf(5),
                        BigInteger.valueOf(6),
                        BigInteger.valueOf(4),
                },
                BigInteger.valueOf(7), BigInteger.valueOf(13)
        );
        System.out.println(Arrays.toString(partHLDESolution));
        NHLDESystem.NHLDESystemTransform hldeTransformed = NHLDESystem.transform(
                new BigInteger[][] {
                        {BigInteger.TWO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.TWO},
                        {BigInteger.ONE, BigInteger.TWO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE},
                        {BigInteger.ONE, BigInteger.ONE, BigInteger.TWO, BigInteger.TWO, BigInteger.ZERO},
                },
                new BigInteger[] {BigInteger.TWO, BigInteger.ONE, BigInteger.TWO},
                BigInteger.valueOf(3)
        );
        System.out.println();
        System.out.println(hldeTransformed);

        BigInteger[][] hldeBasis = HLDESystem.systemBasis(
                new BigInteger[][]{
                        {
                            BigInteger.TWO,
                            BigInteger.ONE,
                            BigInteger.ZERO,
                            BigInteger.ONE,
                            BigInteger.TWO
                        },
                        {
                                BigInteger.ONE,
                                BigInteger.TWO,
                                BigInteger.ONE,
                                BigInteger.ZERO,
                                BigInteger.ONE
                        },
                        {
                                BigInteger.ONE,
                                BigInteger.ONE,
                                BigInteger.TWO,
                                BigInteger.TWO,
                                BigInteger.ZERO
                        }
                }, BigInteger.valueOf(3)
        );
        System.out.println(Arrays.deepToString(hldeBasis));
        System.out.println();*/

        /*BigInteger[][] nhldeSystem = new BigInteger[][]{
                {
                        BigInteger.TWO,
                        BigInteger.ONE,
                        BigInteger.ZERO,
                        BigInteger.ONE,
                        BigInteger.TWO
                },
                {
                        BigInteger.ONE,
                        BigInteger.TWO,
                        BigInteger.ONE,
                        BigInteger.ZERO,
                        BigInteger.ONE
                },
                {
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.TWO,
                        BigInteger.TWO,
                        BigInteger.ZERO
                }
        };
        BigInteger[] b = new BigInteger[] {BigInteger.TWO, BigInteger.ONE, BigInteger.TWO};*/


        /*BigInteger[][] nhldeSystem = new BigInteger[][]{
                {
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ZERO,
                        BigInteger.ZERO,
                        BigInteger.ZERO,
                        BigInteger.ZERO,
                        BigInteger.ZERO
                },
                {
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ZERO,
                        BigInteger.ONE,
                        BigInteger.ZERO,
                        BigInteger.ZERO,
                        BigInteger.ZERO,
                        BigInteger.ZERO
                },
                {
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ZERO,
                        BigInteger.ZERO,
                        BigInteger.ONE,
                        BigInteger.ZERO,
                        BigInteger.ZERO,
                        BigInteger.ZERO
                },
                {
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ZERO,
                        BigInteger.ZERO,
                        BigInteger.ZERO,
                        BigInteger.ONE,
                        BigInteger.ZERO,
                        BigInteger.ZERO
                },
                {
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ZERO,
                        BigInteger.ZERO,
                        BigInteger.ZERO,
                        BigInteger.ZERO,
                        BigInteger.ONE,
                        BigInteger.ZERO
                 },
                {
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ZERO,
                        BigInteger.ZERO,
                        BigInteger.ZERO,
                        BigInteger.ZERO,
                        BigInteger.ZERO,
                        BigInteger.ONE
                }
        };*/
        /*BigInteger[][] nhldeSystem = new BigInteger[][]{
                {
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ZERO,
                        BigInteger.ZERO
                },
                {
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ZERO,
                        BigInteger.ONE,
                        BigInteger.ZERO
                },
                {
                        BigInteger.ONE,
                        BigInteger.ONE,
                        BigInteger.ZERO,
                        BigInteger.ZERO,
                        BigInteger.ONE
                }
        };*/
        /*BigInteger[] b = new BigInteger[] {
                BigInteger.valueOf(17),
                BigInteger.valueOf(5),
                BigInteger.valueOf(1)
        };*/

        /*BigInteger[] b = new BigInteger[] {
                BigInteger.valueOf(18),
                BigInteger.valueOf(21),
                BigInteger.valueOf(17),
                BigInteger.valueOf(17),
                BigInteger.valueOf(0),
                BigInteger.valueOf(14),
        };

        BigInteger p = BigInteger.valueOf(23);

        BigInteger[][] generalSolution  = NHLDESystem.generalSolution(
                nhldeSystem, b, p
        );
        System.out.println(FieldUtils.printMatrix(generalSolution, "General solution: "));
        boolean res = true;
        for(int i = 0; i < 100; ++i) {
            res = res && NHLDESystem.verifySolution(
                    nhldeSystem, b, p, generalSolution
            );
            if(!res) {
                System.out.println(i + ": " + res + "\n\n");
            }
        }
        System.out.println(res);
        BigInteger[] solution = new BigInteger[] {
                BigInteger.ZERO,
                BigInteger.ZERO,
                BigInteger.ZERO,
                BigInteger.ZERO,
                BigInteger.ZERO,
                BigInteger.ZERO,
                BigInteger.valueOf(18),
                BigInteger.valueOf(21),
                BigInteger.valueOf(17),
                BigInteger.valueOf(17),
                BigInteger.valueOf(0),
                BigInteger.valueOf(14)
        };

        log.debug("Solution is: {}", Arrays.toString(solution));
        log.debug(String.valueOf(NHLDESystem.verifySolution(
                nhldeSystem, b, p, generalSolution, solution
        )));


        BigInteger[][] A = new BigInteger[][] {
                new BigInteger[] {
                        BigInteger.valueOf(-2),
                        BigInteger.valueOf(1),
                        BigInteger.valueOf(-1),
                        BigInteger.valueOf(3),
                        BigInteger.valueOf(1)
                },
                new BigInteger[] {
                        BigInteger.valueOf(-3),
                        BigInteger.valueOf(2),
                        BigInteger.valueOf(-1),
                        BigInteger.valueOf(1),
                        BigInteger.valueOf(1)
                }
        };
        BigInteger[][] B = new BigInteger[][] {
                new BigInteger[] {
                        BigInteger.valueOf(2),
                        BigInteger.valueOf(1)
                },
                new BigInteger[] {
                        BigInteger.valueOf(-1),
                        BigInteger.valueOf(-1)
                }
        };*/
        /*int m = 53;
        int n = 100;
        BigInteger p = BigInteger.valueOf(90004169);
        BigInteger[][] A = FieldUtils.nonsingularMatrix(m, n, BigInteger.valueOf(23));
        log.debug(FieldUtils.printMatrix(A, ""));
        BigInteger[] b = FieldUtils.randomVector(m, p);
        BigInteger[][] generalSolution = NHLDESystem.generalSolution(A, b, p);
        boolean verify = true;
        for(int i = 0; i < 10000; ++i) {
            verify = verify && NHLDESystem.verifySolution(A, b, p, generalSolution);
        }
        log.debug("Verified: {}", verify);*/

        /*BigInteger[][] A = new BigInteger[][] {
                {
                    BigInteger.valueOf(1),
                    BigInteger.valueOf(1),
                    BigInteger.valueOf(0),
                    BigInteger.valueOf(1),
                    BigInteger.valueOf(2),
                },
                {
                        BigInteger.valueOf(1),
                        BigInteger.valueOf(1),
                        BigInteger.valueOf(9),
                        BigInteger.valueOf(8),
                        BigInteger.valueOf(8),
                },
                {
                        BigInteger.valueOf(1),
                        BigInteger.valueOf(1),
                        BigInteger.valueOf(3),
                        BigInteger.valueOf(6),
                        BigInteger.valueOf(10),
                }
        };
        BigInteger[] b = new BigInteger[] {
                BigInteger.valueOf(1),
                BigInteger.valueOf(2),
                BigInteger.valueOf(3),
        };

        BigInteger p = BigInteger.valueOf(11);
        BigInteger[][] genSolution = NHLDESystem.generalSolution(A, b, p);
        log.debug(FieldUtils.printMatrix(genSolution, "General Solution"));
        BigInteger[] solution = new BigInteger[] {
                BigInteger.valueOf(0),
                BigInteger.valueOf(7),
                BigInteger.valueOf(10),
                BigInteger.valueOf(7),
                BigInteger.valueOf(10)
        };*/

//        int i = 2;
//        for(int j = 1; j <= 30; j++) {
//            BigInteger p = BigInteger.valueOf(70054097);
//            i = 20*j;
//            log.debug("Step: {}", i);
//            BigInteger[][] AMatrix = FieldUtils.nonsingularSquareMatrix(i, p);
//            BigInteger[] bVector = FieldUtils.randomVector(i, p);
//            long start = System.currentTimeMillis();
//            NHLDESystem.generalSolution(AMatrix, bVector, p);
//            long end = System.currentTimeMillis();
//            log.debug("Duration {}", end-start);
//        }
        //boolean verify = NHLDESystem.verifySolution(AMatrix, bVector, p, genSolution, solution);
        //log.debug("Verify: {}", verify);

        BigInteger[] hlde = new BigInteger[] {
                BigInteger.valueOf(2),
                BigInteger.valueOf(5),
                BigInteger.valueOf(7),
                BigInteger.valueOf(3),
                BigInteger.valueOf(6),
        };
       log.debug("Ring basis: {}", FieldUtils.printMatrix(HLDE.ringBasis(hlde, BigInteger.valueOf(12)), ""));


    }
}
