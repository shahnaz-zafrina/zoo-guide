package com.example.zooui;

public class FromQR {
    String qrId;
    String qrDestination;

    public FromQR(){}

    public FromQR(String qrId, String qrDestination) {
        this.qrId = qrId;
        this.qrDestination = qrDestination;
    }

    public String getQrId() {
        return qrId;
    }

    public String getQrDestination() {
        return qrDestination;
    }

    public void setQrId(String qrId) {
        this.qrId = qrId;
    }
}
