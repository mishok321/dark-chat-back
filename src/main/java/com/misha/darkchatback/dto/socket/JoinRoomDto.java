package com.misha.darkchatback.dto.socket;

public class JoinRoomDto {
    private String room;
    private String username;

    public JoinRoomDto() {
    }

    public JoinRoomDto(String room, String username) {
        this.room = room;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "RoomIdDto{room='%s', username='%s'}".formatted(room, username);
    }
}
