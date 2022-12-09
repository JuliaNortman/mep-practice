package ua.knu.ynortman.protocol;

import lombok.extern.slf4j.Slf4j;
import ua.knu.ynortman.protocol.sender.Sender;
import ua.knu.ynortman.protocol.receiver.Receiver;

import java.math.BigInteger;

@Slf4j
public class MainProtocol {
    public static void main(String[] args) {
        BigInteger p = new BigInteger("70054097");
        BigInteger[] c = new BigInteger[] {
                BigInteger.valueOf(7),
                BigInteger.valueOf(3),
                BigInteger.valueOf(5),
                BigInteger.valueOf(3),
                BigInteger.valueOf(5),
                BigInteger.valueOf(7)
        };
        /*BigInteger[] msg = new BigInteger[] {
                BigInteger.valueOf(1),
                BigInteger.valueOf(2),
                BigInteger.valueOf(3),
                BigInteger.valueOf(4),
                BigInteger.valueOf(5),
                BigInteger.valueOf(6)
        };*/
        String msg = "Hello,";
        Receiver receiver = new Receiver("id", 2, 10, p);
        receiver.init();
        Sender sender = new Sender("id", msg, p);

        //BigInteger[] d = sender.encode(receiver.getA(), receiver.getD(), "");
        //String rcvdMsg = receiver.decode(d);
        //log.info("Received message is: {}", rcvdMsg);

    }
}
