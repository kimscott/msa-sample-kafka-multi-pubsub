package com.example.template;


public class CloudInstance {
    String accountId;
    String region;
    String status;
    String dummy;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDummy() {
        return dummy;
    }

    public void setDummy(String dummy) {
        this.dummy = dummy;
    }

    @Override
    public String toString(){
        return "CloudInstance(" + accountId + ", " + region + ", " + status + ", "+ dummy + ")" ;
//        return "CloudInstance(" + accountId + ", " + region + ", " + status + ")" ;
    }


}
