package com.example.poiuyt.easysms.data;

/**
 * Created by poiuyt on 9/14/16.
 */

public class ChatMessage {

    String sender, recipient, message;

    public ChatMessage(String sender, String recipient, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
    }
}
