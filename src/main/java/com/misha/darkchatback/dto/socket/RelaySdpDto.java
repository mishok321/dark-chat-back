package com.misha.darkchatback.dto.socket;

public class RelaySdpDto {
    private String peerID;
    private Object sessionDescription;

    public RelaySdpDto() {
    }

    public RelaySdpDto(String peerID, Object sessionDescription) {
        this.peerID = peerID;
        this.sessionDescription = sessionDescription;
    }

    public String getPeerID() {
        return peerID;
    }

    public void setPeerID(String peerID) {
        this.peerID = peerID;
    }

    public Object getSessionDescription() {
        return sessionDescription;
    }

    public void setSessionDescription(Object sessionDescription) {
        this.sessionDescription = sessionDescription;
    }
}
