package com.example.punyaaachman.albus.POJO;

/**
 * Created by samarthgupta on 13/04/17.
 */

public class Trips {

    String start,end,tripId;
    Double pay;

    public Trips(){
        start=null;
        end=null;
        tripId=null;
        pay=0.0;
    }

    public Trips(String start, String end, Double pay) {
        this.start = start;
        this.end = end;
        this.pay = pay;
        this.tripId= Double.toString(System.currentTimeMillis());
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public Double getPay() {
        return pay;
    }
}
