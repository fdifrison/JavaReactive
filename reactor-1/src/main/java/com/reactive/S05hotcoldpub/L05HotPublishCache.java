package com.reactive.S05hotcoldpub;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class L05HotPublishCache {

    public static void main(String[] args) {

        // Hot Publisher

        Flux<String> movieStream = Flux.fromStream(Util::getMovie)
                .delayElements(Duration.ofMillis(300))
                        .cache(5); // this is equal to publish().replay;
        // the second subscriber will get immediately all the item emitted since they are stored in memory
        // (up to int.max num. of elements)


        // the first user start to watch the movie
        movieStream
                .subscribe(Util.subscriber("Frodo"));

        Util.sleepSeconds(4);

        // after a while, another user starts the same movie,
        // and since the streaming is cached, Sam will immediately get all the data
        movieStream
                .subscribe(Util.subscriber("Sam"));

        Util.sleepSeconds(20);

    }

}
