package com.reactive.S13stepverify;

import com.reactive.util.Util;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class L03RangeTest {

    @Test
    public void test1() {
        Flux<Integer> range = Flux.range(1, 50);
        StepVerifier.create(range)
                // the test expects exactly 50 onNext signals
                .expectNextCount(50)
                .verifyComplete();
    }

    @Test
    public void test2() {
        // what id I don't know the exact number of items will be emitted?
        // then I can operate on filters to provide acceptance conditions
        Flux<Integer> range = Flux.range(1, Util.faker().random().nextInt(5, 99));
        StepVerifier.create(range)
                // we don't know how many items will be emitted, but we know these wil be less than 100
                .thenConsumeWhile( i -> i < 100)
                .verifyComplete();
    }

}
