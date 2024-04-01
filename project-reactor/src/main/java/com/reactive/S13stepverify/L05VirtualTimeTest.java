package com.reactive.S13stepverify;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class L05VirtualTimeTest {

    @Test
    public void test1() {

        // take too much time this way
        // StepVerifier.create(timeConsumingFlux())
        //    .expectNext("1a","2a","3a","4a")
        //    .verifyComplete();

        // We are simulating that the specified time is past!
        StepVerifier.withVirtualTime(this::timeConsumingFlux)
                .thenAwait(Duration.ofSeconds(30))
                .expectNext("1a", "2a", "3a", "4a")
                .verifyComplete();

    }

    // our service will make the test duration too long! we need to use virtualTime
    private Flux<String> timeConsumingFlux() {
        return Flux.range(1, 4)
                .delayElements(Duration.ofSeconds(5))
                .map(i -> i + "a");
    }

    @Test
    public void test2() {

        // We are simulating that the specified time is past!
        StepVerifier.withVirtualTime(this::timeConsumingFlux)
                .expectSubscription()
                // for some requirement we need to pause the test for some time,
                // we are saying: for the first 4 seconds, nothing should happen except the subscription
                .expectNoEvent(Duration.ofSeconds(4))
                .thenAwait(Duration.ofSeconds(30))
                .expectNext("1a", "2a", "3a", "4a")
                .verifyComplete();

    }



}
