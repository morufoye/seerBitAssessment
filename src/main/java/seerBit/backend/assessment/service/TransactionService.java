package seerBit.backend.assessment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seerBit.backend.assessment.model.DataModel;
import seerBit.backend.assessment.model.Statistic;
import seerBit.backend.assessment.model.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.*;

@Service
public class TransactionService {
    @Autowired
    private DataModel dataModel;

    public String postTransaction(Transaction transaction) {
        Date now = new Date(System.currentTimeMillis());
        Timestamp ts =new Timestamp(now.getTime());
        System.out.println(" this is the current time >> jjjjjjjjjjjjjjjjjj    " + ts);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(transaction.getTransactionTime().getTime()));
        calendar.add(Calendar.HOUR, 7);
        Timestamp trans_time = new Timestamp(calendar.getTimeInMillis());
        System.out.println(" this is the trans time >> adjusted    " + trans_time);
        Long timeDifference =  now.getTime()  - trans_time.getTime() ;
        if (timeDifference > 30000)  {
       // if (timeDifference > 2 * 60 * 1000)  {  // 2 minutes threshold for test
            return "204";
        }
        if (timeDifference < 0)  {
            return "422";
        }
        dataModel.getModel().put(now, transaction);
        return "201";
    }

    public Statistic getStatistic() {
        List<Transaction> trans = new ArrayList<>();
        Map<Date, Transaction> map = dataModel.getModel();
                Date now = new Date(System.currentTimeMillis());
                for (Date date : map.keySet())  {
                    long timeDiff = now.getTime() - date.getTime();
                   // if (timeDiff <= 2 * 60 * 1000) {
                    if (timeDiff <= 30000) {
                        trans.add(map.get(date));
            }
        }
        return processStat(trans);
    }

    Statistic processStat(List<Transaction> trans) {
        Statistic stat = new Statistic();
        if (trans.isEmpty()) {
            stat.setSum("0.00");
            stat.setAvg("0.00");
            stat.setMax("0.00");
            stat.setMin("0.00");
            stat.setCount("0");
            return stat;
        }
        BigDecimal max = new BigDecimal(0);
        BigDecimal min = trans.get(0).getAmount();
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal average = new BigDecimal(0);
        for (int i = 0; i < trans.size(); i++){
            if (trans.get(i).getAmount().compareTo(max) == 1){
                max = trans.get(i).getAmount();
            }
            if (trans.get(i).getAmount().compareTo(min) == -1){
                min = trans.get(i).getAmount();
            }
            sum = sum.add(trans.get(i).getAmount());
        }

        average = sum.divide(BigDecimal.valueOf(trans.size()), 2, RoundingMode.HALF_UP);
        stat.setSum(String.valueOf(sum.setScale(2, RoundingMode.HALF_UP)));
        stat.setAvg(String.valueOf(average));
        stat.setMax(String.valueOf(max.setScale(2, RoundingMode.HALF_UP)));
        stat.setMin(String.valueOf(min.setScale(2, RoundingMode.HALF_UP)));
        stat.setCount(String.valueOf(trans.size()));
        return stat;
    }

    public String deleteTransaction() {
        List<Transaction> trans = new ArrayList<>();
        Map<Date, Transaction> map = dataModel.getModel();
        Date now = new Date(System.currentTimeMillis());
        for (Date date : map.keySet())  {
                map.remove(date);
        }
        return "204";
    }

}
