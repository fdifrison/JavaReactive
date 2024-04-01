package com.reactive.S13stepverify;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class L01StepVerifyDemoTest {

    @Test
    public void test1() {
        Flux<Integer> flux = Flux.just(1, 2, 3);

        // the stepVerifier internally subscribes to the publisher
        // and has a series of methods that will help us test the emission
        StepVerifier.create(flux)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .verifyComplete();
    }

    @Test
    public void test2() {
        Flux<Integer> flux = Flux.just(1, 2, 3);

        // the stepVerifier internally subscribes to the publisher
        // and has a series of methods that will help us test the emission
        StepVerifier.create(flux)
                .expectNext(1,2,3)
                .verifyComplete();
    }


}
