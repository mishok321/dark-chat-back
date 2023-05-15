package com.misha.darkchatback.socket.socket;

public class SendMessageDto {
    private String message;
    private String author;
    private String color;

    public SendMessageDto() {
    }

    public SendMessageDto(String message, String author, String color) {
        this.message = message;
        this.author = author;
        this.color = color;
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
}
