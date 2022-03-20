package seerBit.backend.assessment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import seerBit.backend.assessment.model.Statistic;
import seerBit.backend.assessment.model.Transaction;
import seerBit.backend.assessment.service.TransactionService;

import java.math.RoundingMode;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatController extends ResponseEntityExceptionHandler {

    private final TransactionService transactionService;

    @PostMapping("/transactions")
    public ResponseEntity<String> postTransaction(@RequestBody Transaction transaction) {
        try {

        } catch (org.springframework.http.converter.HttpMessageNotReadableException ex) {
            return ResponseEntity.ok().body("422");
        }
        String respose = null;
       respose =  transactionService.postTransaction(transaction);
        return ResponseEntity.ok().body(respose);
    }

    @GetMapping("/statistics")
    public ResponseEntity<Statistic> getStatistic() {
        Statistic stat  = transactionService.getStatistic();
        return ResponseEntity.ok().body(stat);
    }

    @DeleteMapping("/transactions")
    public ResponseEntity<String> deleteTransaction() {
        String response = transactionService.deleteTransaction();
        return ResponseEntity.ok().body(response);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.ok().body("400");
    }
}
