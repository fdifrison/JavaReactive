package com.reactive.S13stepverify;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class L02FluxErrorTest {

    @Test
    public void test1() {
        Flux<Integer> flux = Flux.just(1, 2, 3);
        Flux<Integer> error = Flux.error(new RuntimeException("oops"));
        Flux<Integer> concat = Flux.concat(flux, error);

        StepVerifier.create(concat)
                .expectNext(1,2,3)
                .verifyError();
    }

    @Test
    public void test2() {
        Flux<Integer> flux = Flux.just(1, 2, 3);
        Flux<Integer> error = Flux.error(new RuntimeException("oops"));
        Flux<Integer> concat = Flux.concat(flux, error);

        StepVerifier.create(concat)
                .expectNext(1,2,3)
                .verifyError(RuntimeException.class);

    }

    @Test
    public void test3() {
        Flux<Integer> flux = Flux.just(1, 2, 3);
        Flux<Integer> error = Flux.error(new RuntimeException("oops"));
        Flux<Integer> concat = Flux.concat(flux, error);

        StepVerifier.create(concat)
                .expectNext(1,2,3)
                .verifyErrorMessage("oops");

    }

}
