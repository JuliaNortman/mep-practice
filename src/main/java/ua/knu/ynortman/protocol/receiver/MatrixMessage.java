package ua.knu.ynortman.protocol.receiver;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@AllArgsConstructor
public class MatrixMessage implements Serializable {
    private BigInteger[][] A;
    private BigInteger[][] D;
}
