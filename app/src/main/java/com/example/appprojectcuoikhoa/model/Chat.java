package com.example.appprojectcuoikhoa.model;

public class Chat {
    private String message;
    private String receiver;
    private String sender;
    private boolean isSeen;

    public Chat (){

    }
    public Chat(String message, String receiver, String sender,boolean isSeen) {
        this.message = message;
        this.receiver = receiver;
        this.sender = sender;
        this.isSeen = isSeen;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }
}
