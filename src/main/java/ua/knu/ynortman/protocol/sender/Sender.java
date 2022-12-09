package ua.knu.ynortman.protocol.sender;

import lombok.extern.slf4j.Slf4j;
import ua.knu.ynortman.enoder.Encoder;
import ua.knu.ynortman.key.DBKeyManagement;
import ua.knu.ynortman.protocol.receiver.MatrixMessage;
import ua.knu.ynortman.tss.field.NHLDESystem;
import ua.knu.ynortman.utils.FieldUtils;
import ua.knu.ynortman.utils.RabbitUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

@Slf4j
public class Sender {
    private final String id;
    private BigInteger[] msg;
    private BigInteger[] c;
    private BigInteger p;

    public Sender(String id, String text, BigInteger p) {
        this.msg = Encoder.getInstance(p).textToNumbers(text);
        log.debug("Msg: {}", Arrays.toString(msg));
        this.id = id;
        this.p = p;
    }

    public void init() {
        try {
            //RabbitUtils.send(true, "InitQueue");
            RabbitUtils.receive("StepOneQueue", o -> {
                MatrixMessage message = (MatrixMessage) o;
                this.c = DBKeyManagement.getKeysForSender(message.getA().length, id);
                //log.debug("Received matrix A: {}", Arrays.deepToString(message.getA()));
                //log.debug("Received matrix D: {}", Arrays.deepToString(message.getD()));
                int messageBlocksNum = (msg.length + message.getA().length - 1) / message.getA().length - 1;
                for(int i = 0; i < msg.length; i = i + message.getA().length) {
                    BigInteger[] messageBlock = Arrays.copyOfRange(msg, i, i + message.getA().length);


                    if(msg.length < i + message.getA().length) {
                        //log.debug("Fill with empty chars");
                        for (int j = (msg.length - (msg.length/message.getA().length)*message.getA().length);
                        j < message.getA().length; ++j) {
                            //log.debug("j={}, symbol = {}", j, Encoder.getInstance(p).getCharNumber(""));
                            messageBlock[j] = Encoder.getInstance(p).getCharNumber("");
                        }
                    }
                    //log.debug("MESSAGE BLOCK IS: {}", Arrays.toString(messageBlock) );
                    //log.debug("MESSAGE IS: {}", Arrays.toString(msg) );
                    //log.debug("Start index: {}, end index: {}, message blocks num: {}",
                    //        i,  i + message.getA().length, messageBlocksNum);
                    BigInteger[] d = encode(message.getA(), message.getD(), messageBlock);
                    try {
                        RabbitUtils.send(d, "StepTwoQueue", false);
                    } catch (IOException | TimeoutException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    RabbitUtils.send(new BigInteger[0], "StepTwoQueue", false);
                } catch (IOException | TimeoutException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public BigInteger[] encode(BigInteger[][] A, BigInteger[][] D, BigInteger[] messageBlock) {
        log.debug(FieldUtils.printMatrix(A, "A:"));
        log.debug(FieldUtils.printMatrix(D, "D:"));
        BigInteger[][] genSolution = NHLDESystem.generalSolution(A, messageBlock, p);
        BigInteger[] solution = NHLDESystem.solution(genSolution, p);
        log.debug("Solution: {}", Arrays.toString(solution));
        BigInteger[] d = FieldUtils.zeroArray(A.length);
        for(int i = 0; i < D.length; ++i) {
            BigInteger el = BigInteger.ZERO;
            for(int j = 0; j < D[i].length-1; ++j) {
                el = el.add(D[i][j].multiply(solution[j]));
                //log.debug("i: {}, j: {}, el: {}", i, j, el);
            }
            d[i] = el.add(D[i][D[i].length-1]).mod(p);
        }
        log.debug("d: {}", Arrays.toString(d));
        d = FieldUtils.addVectors(d, c, p);
        log.debug("d': {}", Arrays.toString(d));
        return d;
    }
}
