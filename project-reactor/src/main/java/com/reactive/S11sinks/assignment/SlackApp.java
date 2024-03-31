package com.reactive.S11sinks.assignment;

import com.reactive.util.Util;

public class SlackApp {

    private static final Colors colors = new Colors();

    public static void main(String[] args) {

        SlackRoom council = new SlackRoom("Consiglio di Elrond");

        SlackMember frodo = new SlackMember("Frodo", colors.getColor());
        SlackMember elrond = new SlackMember("Elrond", colors.getColor());
        SlackMember aragon = new SlackMember("Aragon", colors.getColor());
        SlackMember gandalf = new SlackMember("Gandalf", colors.getColor());

        council.joinRoom(frodo);
        council.joinRoom(elrond);
        council.joinRoom(aragon);
        council.joinRoom(gandalf);

        elrond.sendMsg("Benvenuti miei cari!");
        Util.sleepSeconds(2);

        gandalf.sendMsg("Tu non puoi passare!");
        Util.sleepSeconds(2);





        Util.sleepSeconds(60);

    }


}
