package ua.knu.ynortman;

import ua.knu.ynortman.protocol.sender.Sender;
import ua.knu.ynortman.protocol.receiver.Receiver;
import ua.knu.ynortman.utils.PropertyUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) throws IOException, TimeoutException {
        String mode = args[0];//PropertyUtils.getProperty("mode");

        //BigInteger p = new BigInteger("70054097");
        BigInteger[] c = new BigInteger[]{
                BigInteger.valueOf(7),
                BigInteger.valueOf(3),
                BigInteger.valueOf(5),
                BigInteger.valueOf(3),
                BigInteger.valueOf(5),
                BigInteger.valueOf(7)
        };

        String p = args[1];
        int m = args.length > 2 ? Integer.parseInt(args[2]) : 2;
        int n = args.length > 3 ? Integer.parseInt(args[3]) : 10;

        if ("receiver".equals(mode)) {
            Receiver receiver = new Receiver("id", m, n, new BigInteger(p));
            receiver.init();
            //System.out.println(receiver.getMessage());
        } else if ("sender".equals(mode)) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter a string: ");
            String msg = sc.nextLine();
            Sender sender = new Sender("id", msg, new BigInteger(p));
            sender.init();
        }
    }

}
