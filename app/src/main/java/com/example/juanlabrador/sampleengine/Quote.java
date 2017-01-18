package com.example.juanlabrador.sampleengine;

/**
 * Created by juanlabrador on 11/01/17.
 */

public class Quote {

    Long id;
    String who;
    String what;

    public Quote(Long id, String who, String what) {
        this.id = id;
        this.who = who;
        this.what = what;
    }

    public Long getId() {
        return id;
    }

    public String getWho() {
        return who;
    }

    public String getWhat() {
        return what;
    }
}
