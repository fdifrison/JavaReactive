package com.reactive.S13stepverify;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;
import reactor.util.context.Context;

public class L07ContextTest {

    @Test
    public void test1() {
        StepVerifier.create(getWelcomeMsg())
                .verifyError(RuntimeException.class);
    }

    @Test
    public void test2() {

        // we need to add options to simulate the presene of the context key user
        StepVerifierOptions options = StepVerifierOptions.create().withInitialContext(Context.of("user", "Frodo"));

        StepVerifier.create(getWelcomeMsg(), options)
                .expectNext("Welcome Frodo")
                .verifyComplete();
    }



    private static Mono<String> getWelcomeMsg() {
        return Mono.deferContextual(contextView -> {
            if (contextView.hasKey("user")) {
                return Mono.just("Welcome " + contextView.get("user"));
            } else {
                return Mono.error(new RuntimeException("unauthenticated"));
            }
        });
    }

}
