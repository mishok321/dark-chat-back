package com.misha.darkchatback.socket.socket;

import java.util.List;
import java.util.Map;

public class ListOfRoomsDto {
    private List<Map<String, String>> rooms;

    public ListOfRoomsDto(List<Map<String, String>> rooms) {
        this.rooms = rooms;
    }

    public ListOfRoomsDto() {
    }

    public List<Map<String, String>> getRooms() {
        return rooms;
    }

    public void setRooms(List<Map<String, String>> rooms) {
        this.rooms = rooms;
    }
}
