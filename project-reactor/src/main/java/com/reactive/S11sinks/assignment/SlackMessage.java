package com.reactive.S11sinks.assignment;

import lombok.Data;

@Data
public class SlackMessage {

    private static final String FORMAT = Colors.RED + "[%s -> %s] : %s" + Colors.RESET;

    private String sender;
    private String receiver;
    private String msg;

    @Override
    public String toString() {
        return String.format(FORMAT, this.sender, this.receiver, this.msg);
    }
}
