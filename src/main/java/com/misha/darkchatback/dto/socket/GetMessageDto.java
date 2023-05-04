package com.misha.darkchatback.dto.socket;

public class GetMessageDto {
    private String message;
    private String author;
    private String color;
    private String roomID;

    public GetMessageDto() {
    }

    public GetMessageDto(String message, String author, String color, String roomID) {
        this.message = message;
        this.author = author;
        this.color = color;
        this.roomID = roomID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }
}
