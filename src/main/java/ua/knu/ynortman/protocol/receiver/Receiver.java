package ua.knu.ynortman.protocol.receiver;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ua.knu.ynortman.enoder.Encoder;
import ua.knu.ynortman.key.DBKeyManagement;
import ua.knu.ynortman.utils.FieldUtils;
import ua.knu.ynortman.utils.RabbitUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Data
@Slf4j
public class Receiver {
    private final String id;
    private BigInteger[] c;
    private final BigInteger p;
    private final BigInteger[][] A;
    private BigInteger[][] D;
    private final List<FieldUtils.InvertibleTriangularMatrix> invMatrices;
    private final List<BigInteger[]> shiftVectors;
    private final int s = 5;
    private ReceivedMessage receivedMessage;

    static class ReceivedMessage {
        private final StringBuilder msg = new StringBuilder();
        public void appendMessageBlock(String str) {
            msg.append(str);
        }
        public String getMessage() {
            return msg.toString();
        }
    }

    public Receiver(String id, int m, int n, BigInteger p) {
        this.id = id;
        this.p = p;
        this.A = FieldUtils.nonsingularMatrix(3*m, n, p);
        log.debug(FieldUtils.printMatrix(A, "A:"));
        this.invMatrices = new ArrayList<>(s);
        this.shiftVectors = new ArrayList<>(s+1);
        for(int i = 0; i < s; ++i) {
            this.invMatrices.add(i, FieldUtils.triangularInvertibleMatrix(m, p));
            this.shiftVectors.add(i, FieldUtils.randomVector(3*m, p));
        }
        this.shiftVectors.add(s, FieldUtils.randomVector(3*m, p));
        this.receivedMessage = new ReceivedMessage();
        log.debug("b0: {}", Arrays.toString(shiftVectors.get(0)));
        log.debug("b: {}", Arrays.toString(shiftVectors.get(1)));
        log.debug(FieldUtils.printMatrix(invMatrices.get(0).matrix, "B:"));
    }

    public void init() {
        class dWrapper {
            BigInteger[] d;
        }

        try {
            prepareContext();
            RabbitUtils.send(new MatrixMessage(A, D), "StepOneQueue", true);
            dWrapper wrapper = new dWrapper();
            wrapper.d = new BigInteger[1];
            while (wrapper.d.length > 0) {
                log.debug("d length: {}", wrapper.d.length);
                RabbitUtils.receive("StepTwoQueue", o -> {
                    BigInteger[] d = (BigInteger[]) o;
                    if(d.length > 0) {
                        //log.debug("Received vector: {}", Arrays.toString(d));
                        String res = decode(d);
                        receivedMessage.appendMessageBlock(res);
                        System.out.println("RECEIVED MESSAGE IS: " + res);
                    }
                    wrapper.d = d;
                    log.debug("d warpper length: {}", wrapper.d.length);
                    log.debug("d length 1: {}", d.length);
                    synchronized (wrapper) {
                        wrapper.notify();
                    }
                });
                synchronized (wrapper) {
                    wrapper.wait();
                }
            }
        } catch (IOException | TimeoutException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void prepareContext() {
        D = new BigInteger[A.length][A[0].length+1]; //Ax + b0
        for (int i = 0; i < A.length; ++i) {
            System.arraycopy(A[i], 0, D[i], 0, A[i].length);
            D[i][D[i].length-1] = shiftVectors.get(0)[i];
        }
        log.debug(FieldUtils.printMatrix(D, "Ax+b0"));
        for(int i = 0; i < s; ++i) {
            D = FieldUtils.matrixAddVectorColumn(FieldUtils.operator(invMatrices.get(i).matrix, D, p),
                    shiftVectors.get(i+1), D[i].length-1, p);
        }
        this.c = DBKeyManagement.getKeysForReceiver(A.length, id);
        log.debug(FieldUtils.printMatrix(D, "D:"));
    }


    public String decode(BigInteger[] d) {
        d = FieldUtils.substractVectors(
                FieldUtils.substractVectors(d, c, p), shiftVectors.get(s), p);
        BigInteger[][] d1 = FieldUtils.rowToColumn(d);
        for(int i = s-1; i >= 0; --i) {
            d1 = FieldUtils.operator(invMatrices.get(i).inverted, d1, p);
            d = FieldUtils.columnToRow(d1);
            d = FieldUtils.substractVectors(d, shiftVectors.get(i), p);
            d1 = FieldUtils.rowToColumn(d);
        }
        d = FieldUtils.columnToRow(d1);
        return Encoder.getInstance(p).numbersToText(d);
    }

    public String getMessage() {
        return receivedMessage.getMessage();
    }
}
