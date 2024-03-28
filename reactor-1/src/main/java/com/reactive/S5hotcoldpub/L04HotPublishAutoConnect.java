package com.reactive.S5hotcoldpub;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class L04HotPublishAutoConnect {

    public static void main(String[] args) {

        // Hot Publisher


        Flux<String> movieStream = Flux.fromStream(Util::getMovie)
                .delayElements(Duration.ofMillis(300))
                .publish()
                .autoConnect(1);
        // Instead of refCount,
        // with autoConnect we have a truly Hot Publisher.
        // Therefore, if the second subscriber joins when the streaming is finished,
        // He won't see any data, the emission won't restart
        // If we set the autoConnect to 0, the streaming will start even if there is no subscriber.


        // the first user start to watch the movie
        movieStream
                .subscribe(Util.subscriber("Frodo"));

        Util.sleepSeconds(4);

        System.out.println("Sam is about to join");

        // after a while, another user starts the same movie,
        // but the first already finished, so Sam won't see anything
        movieStream
                .subscribe(Util.subscriber("Sam"));

        Util.sleepSeconds(20);

    }

}
