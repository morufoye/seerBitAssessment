package seerBit.backend.assessment.serviceUnitTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import seerBit.backend.assessment.model.Statistic;
import seerBit.backend.assessment.model.Transaction;
import seerBit.backend.assessment.service.TransactionService;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceUnitTest {
    @Autowired
    private TransactionService transactionService;

    @Test
    public void postSuccessfulTransactionTest(){
        Transaction transaction = new Transaction(new BigDecimal(12.3343), Timestamp.valueOf("2022-03-20 09:36:00.000"));
        String result = transactionService.postTransaction(transaction);
        assertThat(result).isEqualTo("201");
    }

    @Test
    public void postLateTransactionTest(){
        Transaction transaction = new Transaction(new BigDecimal(12.3343), Timestamp.valueOf("2018-07-17 09:59:51.312"));
        String result = transactionService.postTransaction(transaction);
        assertThat(result).isEqualTo("204");
    }

    @Test
    public void postFutureTransactionTest(){
        Transaction transaction = new Transaction(new BigDecimal(12.3343), Timestamp.valueOf("2018-07-17 09:59:51.312"));
        String result = transactionService.postTransaction(transaction);
        assertThat(result).isEqualTo("422");
    }

    @Test
    public void postJsonParseErrorTransactionTest(){
        Transaction transaction = new Transaction(new BigDecimal(12.3343), Timestamp.valueOf("2018-07-17 09:59:51.312"));
        String result = transactionService.postTransaction(transaction);
        assertThat(result).isEqualTo("422");
    }



    @Test
    public void statisticTest(){
        Transaction transaction1 = new Transaction(new BigDecimal(10.00), Timestamp.valueOf("2022-03-20 09:40:00.000"));
        Transaction transaction2 = new Transaction(new BigDecimal(20.00), Timestamp.valueOf("2022-03-20 09:40:00.000"));
 transactionService.postTransaction(transaction1);
        transactionService.postTransaction(transaction2);
        // avg = 15, sum = 30, max = 20, min = 10, count = 2
        Statistic statistic = transactionService.getStatistic();
        assertThat(statistic.getCount()).isEqualTo("2");
        assertThat(statistic.getAvg()).isEqualTo("15.00");
        assertThat(statistic.getMax()).isEqualTo("20.00");
        assertThat(statistic.getMin()).isEqualTo("10.00");
        assertThat(statistic.getSum()).isEqualTo("30.00");
    }

    @Test
    public void deleteTest(){
        Transaction transaction1 = new Transaction(new BigDecimal(10.00), Timestamp.valueOf("2022-03-20 09:22:00.312"));
        Transaction transaction2 = new Transaction(new BigDecimal(15.00), Timestamp.valueOf("2022-03-20 09:22:00.312"));
        Transaction transaction3 = new Transaction(new BigDecimal(20.00), Timestamp.valueOf("2022-03-20 09:22:00.312"));
        Transaction transaction4 = new Transaction(new BigDecimal(25.00), Timestamp.valueOf("2022-03-20 09:22:00.312"));
        Transaction transaction5 = new Transaction(new BigDecimal(30.00), Timestamp.valueOf("2022-03-20 09:22:00.312"));
        transactionService.postTransaction(transaction1);
        transactionService.postTransaction(transaction2);
        transactionService.postTransaction(transaction3);
        transactionService.postTransaction(transaction4);
        transactionService.postTransaction(transaction5);
        transactionService.deleteTransaction();
        Statistic statistic = transactionService.getStatistic();
        assertThat(statistic.getAvg()).isEqualTo("0.00");
        assertThat(statistic.getCount()).isEqualTo("0");
        assertThat(statistic.getMax()).isEqualTo("0.00");
        assertThat(statistic.getMin()).isEqualTo("0.00");
        assertThat(statistic.getSum()).isEqualTo("0.00");
    }


}
