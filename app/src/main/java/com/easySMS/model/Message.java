package com.easySMS.model;

public class Message {

    public String message;
    public String recipient;
    public String sender;


    public Message(String sender, String message, String recipient) {
        this.message = message;
        this.recipient = recipient;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

}
