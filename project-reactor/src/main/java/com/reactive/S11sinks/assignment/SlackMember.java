package com.reactive.S11sinks.assignment;

import lombok.Data;

import java.util.function.Consumer;

@Data
public class SlackMember {

    private String name;
    private String color;

    private Consumer<String> msgConsumer;

    public SlackMember(String name, String color) {
        this.name = name;
        this.color = color;

    }

    void receiveMsg(String msg) {
        System.out.println(color + msg + Colors.RESET);
    }

    public void sendMsg(String msg) {
        this.msgConsumer.accept(String.format(color + "-> %s: %s" + Colors.RESET, name, msg));
    }

}
