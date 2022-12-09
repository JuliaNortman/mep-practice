package ua.knu.ynortman.key;

import lombok.extern.slf4j.Slf4j;
import ua.knu.ynortman.key.utils.FileUtils;

import java.sql.SQLException;
import java.util.Arrays;

@Slf4j
public class KeyMain {
    public static void main(String[] args) throws SQLException {
        /*FileUtils.txtToCsv("primes/primes1.txt", "primes.csv");
        FileUtils.txtToCsv("primes/primes2.txt", "primes.csv");
        FileUtils.txtToCsv("primes/primes3.txt", "primes.csv");*/
        //FileUtils.testCsvContent("primes.csv");
        //FileUtils.getConnection();
        //FileUtils.presetDB();

        DBKeyManagement management = new DBKeyManagement();
        log.debug("Keys: {}", Arrays.toString(management.getKeysForSender(5, "testStr")));
        log.debug("Keys: {}", Arrays.toString(management.getKeysForReceiver(5, "testStr")));
    }
}
