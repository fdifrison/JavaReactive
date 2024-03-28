package com.reactive.S05hotcoldpub;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class L02HotShare {

    public static void main(String[] args) {


        Flux<String> movieStream = Flux.fromStream(Util::getMovie)
                .delayElements(Duration.ofSeconds(2))
                .share(); // with this it became a Hot Publisher since the publisher emits only one request

        // the first user start to watch the movie
        movieStream
                .subscribe(Util.subscriber("Frodo"));

        Util.sleepSeconds(3);

        // after a while, another user starts the same movie
        movieStream
                .subscribe(Util.subscriber("Sam"));

        Util.sleepSeconds(20);

    }


}
