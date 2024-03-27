package com.reactive.S5hotcoldpub;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class L01ColdPublisher {

    public static void main(String[] args) {

        // Cold Publisher --
        // Whenever a subscriber subscribes to the source (publisher), this starts emitting just for that subscriber

        Flux<String> movieStream = Flux.fromStream(Util::getMovie)
                .delayElements(Duration.ofSeconds(2));// each scene will take some time to load

        // the first user start to watch the movie
        movieStream
                .subscribe(Util.subscriber("Frodo"));

        Util.sleep(3);

        // after a while, another user starts the same movie
        movieStream
                .subscribe(Util.subscriber("Sam"));

        Util.sleep(20);

    }


}
