package seerBit.backend.assessment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Transaction {
    private BigDecimal amount;
    private Timestamp transactionTime;
}
