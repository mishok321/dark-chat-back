package com.misha.darkchatback.dto.socket;

public class AddPeerDto {
    private String peerID;
    private Boolean createOffer;
    private String username;

    public AddPeerDto() {
    }

    public AddPeerDto(String peerID, Boolean createOffer, String username) {
        this.peerID = peerID;
        this.createOffer = createOffer;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPeerID() {
        return peerID;
    }

    public void setPeerID(String peerID) {
        this.peerID = peerID;
    }

    public Boolean getCreateOffer() {
        return createOffer;
    }

    public void setCreateOffer(Boolean createOffer) {
        this.createOffer = createOffer;
    }
}
