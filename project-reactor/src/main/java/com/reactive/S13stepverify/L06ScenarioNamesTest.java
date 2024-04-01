package com.reactive.S13stepverify;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;

public class L06ScenarioNamesTest {

    @Test
    public void test1() {

        Flux<String> flux = Flux.just("a", "b", "c");

        // we can provide additional information to the step-verifier that we'll help us in debugging
        StepVerifierOptions options = StepVerifierOptions.create().scenarioName("alphabets-test");

        StepVerifier.create(flux, options)
                .expectNextCount(12)
                .verifyComplete();

    }


    @Test
    public void test2() {

        Flux<String> flux = Flux.just("a", "b", "c", "d");

        // another way to provide information inside the test

        StepVerifier.create(flux)
                .expectNext("a")
                .as("a-test")
                .expectNext("b")
                .as("b-test")
                .expectNext("c")
                .as("c-test")
                .verifyComplete();

    }

}
