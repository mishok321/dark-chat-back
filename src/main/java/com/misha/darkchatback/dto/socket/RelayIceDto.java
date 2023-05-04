package com.misha.darkchatback.dto.socket;

public class RelayIceDto {
    private String peerID;
    private Object iceCandidate;

    public RelayIceDto() {
    }

    public RelayIceDto(String peerID, Object iceCandidate) {
        this.peerID = peerID;
        this.iceCandidate = iceCandidate;
    }

    public String getPeerID() {
        return peerID;
    }

    public void setPeerID(String peerID) {
        this.peerID = peerID;
    }

    public Object getIceCandidate() {
        return iceCandidate;
    }

    public void setIceCandidate(Object iceCandidate) {
        this.iceCandidate = iceCandidate;
    }
}
