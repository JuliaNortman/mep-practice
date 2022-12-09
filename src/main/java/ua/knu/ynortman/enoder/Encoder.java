package ua.knu.ynortman.enoder;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import lombok.extern.slf4j.Slf4j;
import ua.knu.ynortman.utils.FieldUtils;

import java.math.BigInteger;
import java.util.Arrays;

@Slf4j
public class Encoder {
    private final String alphabet = "abcdefghijklmnopqrstuvwxyz " +
            "0123456789" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            ".,?/!@#$;{}[]%^:&*()_-+=";
    private final BiMap<String, BigInteger> map;
    private static BigInteger p = BigInteger.ZERO;
    private static Encoder instance = null;
    private Encoder() {
        log.info("Encoder constructor");
        this.map = HashBiMap.create();
        String[] chars = alphabet.split("");
        for(int i = 0; i < chars.length; ++i) {
            try {
                map.put(chars[i], BigInteger.valueOf(i)/*FieldUtils.getRandomBigInteger(p)*/);
            } catch (IllegalArgumentException e) {
                log.error("Duplicate values");
                i--;
            }
        }
        map.put("", BigInteger.valueOf(chars.length));
    }

    public static Encoder getInstance(BigInteger p1) {
        if(instance == null || !p.equals(p1)) {
            p = p1;
            instance = new Encoder();

        }
        return instance;
    }

    public BigInteger[] textToNumbers(String text) {
        log.debug("Numbers to text");
        String[] chars = text.split("");
        BigInteger[] numbers = new BigInteger[text.length()];
        for(int i = 0; i < chars.length; ++i) {
            numbers[i] = map.get(chars[i]);
        }
        log.debug("Text: {}, numbers: {}", text, Arrays.toString(numbers));
        return numbers;
    }

    public String numbersToText(BigInteger[] numbers) {
        StringBuilder builder = new StringBuilder();
        for (BigInteger number : numbers) {
            builder.append(map.inverse().get(number));
        }
        log.debug("Numbers: {}, text: {}", Arrays.toString(numbers), builder.toString());
        return builder.toString();
    }

    public BigInteger getCharNumber(String ch) {
        return map.get(ch);
    }
}
