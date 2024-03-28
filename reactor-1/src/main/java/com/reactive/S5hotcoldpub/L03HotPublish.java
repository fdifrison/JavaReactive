package com.reactive.S5hotcoldpub;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class L03HotPublish {

    public static void main(String[] args) {

        // Hot Publisher
        // using share() == publish().refCount(1)
        // N.B. refCount(1)
        // means that as soon as there is a subscriber I will start emitting;
        // this also means that if the first subscriber finished the streaming and later a new subscriber joins,
        // the second one will get all the item from the beginning.
        // Only if a second subscriber joins while the publisher is emitting, then he will lose what has been emitted
        // before he actually joined

        Flux<String> movieStream = Flux.fromStream(Util::getMovie)
                .delayElements(Duration.ofMillis(300))
                .publish()
                .refCount(1); // min number of subscribers to start emitting


        // the first user start to watch the movie
        movieStream
                .subscribe(Util.subscriber("Frodo"));

        Util.sleepSeconds(6);

        // after a while, another user starts the same movie,
        // but the first already finished, hence the streaming will start from the beginning
        movieStream
                .subscribe(Util.subscriber("Sam"));

        Util.sleepSeconds(20);

    }


}
