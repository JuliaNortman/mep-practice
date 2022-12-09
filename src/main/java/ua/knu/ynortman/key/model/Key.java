package ua.knu.ynortman.key.model;

import lombok.Data;

import java.math.BigInteger;
import java.util.UUID;

@Data
public class Key {
    private BigInteger[] keys;
    private long createdTime;
    private long firstAccessTime;
    private long secondAccessTime;
    private UUID firstAccessPersonId;
    private UUID secondAccessPersonId;
}
