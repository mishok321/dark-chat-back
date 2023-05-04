package com.misha.darkchatback.adapter;

import java.util.UUID;

public class Peer {
    private UUID uuid;
    private String username;

    public Peer(UUID uuid, String username) {
        this.uuid = uuid;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
