package seerBit.backend.assessment.model;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class DataModel {
    private ConcurrentHashMap<Date, Transaction> map;
    public DataModel() {
        map = new ConcurrentHashMap<>();
    }
    public ConcurrentHashMap<Date, Transaction> getModel(){
        return this.map;
    }

}
