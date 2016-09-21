package com.example.poiuyt.easysms.model;

public class MessageModel {

    public String message;
    public String recipient;
    public String sender;
    public int mRecipientOrSenderStatus;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRecipientOrSenderStatus(int recipientOrSenderStatus) {
        this.mRecipientOrSenderStatus = recipientOrSenderStatus;
    }

    public void setRecipient(String givenRecipient) {
        recipient = givenRecipient;
    }

    public void setSender(String givenSender) {
        sender = givenSender;
    }

    public String getMessage() {
        return message;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    public int getRecipientOrSenderStatus() {
        return mRecipientOrSenderStatus;
    }
}
