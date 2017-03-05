package com.riprap.emrox.trippin.model;

/**
 * Created by scott on 2/16/2017.
 */

public class Trip {

    private String name;
    private String beginAddress;
    private String destinationAddress;

    public Trip(String name, String beginAddress, String destinationAddress) {
        this.name = name;
        this.beginAddress = beginAddress;
        this.destinationAddress = destinationAddress;
    }

    public Trip() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeginAddress() {
        return beginAddress;
    }

    public void setBeginAddress(String beginAddress) {
        this.beginAddress = beginAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }
}
