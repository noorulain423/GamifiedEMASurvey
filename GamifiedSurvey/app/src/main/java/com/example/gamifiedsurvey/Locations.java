package com.example.gamifiedsurvey;

public class Locations{

    private String address;
    private String steps;
    private String mobile_usage;
    Locations(){

    }

    Locations(String address,String steps,String mobile_usage)
    {
        this.address = address;
        this.steps = steps;
        this.mobile_usage = mobile_usage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getMobile_usage() {
        return mobile_usage;
    }

    public void setMobile_usage(String mobile_usage) {
        this.mobile_usage = mobile_usage;
    }
}
