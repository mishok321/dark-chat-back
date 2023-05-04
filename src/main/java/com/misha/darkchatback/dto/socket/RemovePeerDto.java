package com.misha.darkchatback.dto.socket;

public class RemovePeerDto {
    private String peerID;

    public RemovePeerDto() {
    }

    public RemovePeerDto(String peerID) {
        this.peerID = peerID;
    }

    public String getPeerID() {
        return peerID;
    }

    public void setPeerID(String peerID) {
        this.peerID = peerID;
    }
}
