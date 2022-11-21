package com.alihmzyv.notebookrestapi.exception;

import java.time.Instant;
import java.util.List;

public class CustomErrorResponse {
    private int statusCode;
    private List<String> messages;
    private long timeStamp = Instant.now().toEpochMilli();

    public CustomErrorResponse(int statusCode, List<String> messages) {
        this.statusCode = statusCode;
        this.messages = messages;
    }

    public CustomErrorResponse() {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
