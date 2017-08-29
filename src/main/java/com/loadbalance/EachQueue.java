package com.loadbalance;

import java.util.ArrayList;

/**
 * Created by KshitijBahul on 29-08-2017.
 */
public class EachQueue {

    private Integer loadFactor;

    private ArrayList<Boolean> calls;


    public Integer getLoadFactor() {
        return loadFactor;
    }

    public void setLoadFactor(Integer loadFactor) {
        this.loadFactor = loadFactor;
    }

    public ArrayList<Boolean> getCalls() {
        return calls;
    }

    public void setCalls(ArrayList<Boolean> calls) {
        this.calls = calls;
    }

    public EachQueue(Integer loadFactor) {
        this.loadFactor = loadFactor;
        this.calls = new ArrayList<>();
    }
    public void clearCalls(){
        this.calls.clear();
    }

    @Override
    public String toString() {
        return "EachQueue{" +
                "loadFactor=" + loadFactor +
                ", calls=" + calls +
                '}';
    }
}
