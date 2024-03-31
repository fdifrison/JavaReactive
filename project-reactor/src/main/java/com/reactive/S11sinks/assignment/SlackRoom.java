package com.reactive.S11sinks.assignment;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class SlackRoom {

    private final String name;
    private final Sinks.Many<SlackMessage> sink;
    private final Flux<SlackMessage> flux;


    public SlackRoom(String name) {
        this.name = name;
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
        this.flux = this.sink.asFlux();
    }

    public void joinRoom(SlackMember slackMember) {
        System.out.println(Colors.RED + "-> " +slackMember.getName() + " JOINED " + this.name + "!" + Colors.RESET);
        this.subscribe(slackMember);
        slackMember.setMsgConsumer(
                msg -> this.postMsg(msg, slackMember)
        );
    }

    private void postMsg(String msg, SlackMember slackMember) {
        SlackMessage message = new SlackMessage();
        message.setSender(slackMember.getName());
        message.setMsg(msg);
        this.sink.tryEmitNext(message);
    }

    private void subscribe(SlackMember member){
        this.flux
                .filter(sm -> !sm.getSender().equals(member.getName())) // avoid the sender to receive its own message
                .doOnNext(slackMessage -> slackMessage.setReceiver(member.getName()))
                .map(SlackMessage::toString)
                .subscribe(member::receiveMsg);
    }
}
