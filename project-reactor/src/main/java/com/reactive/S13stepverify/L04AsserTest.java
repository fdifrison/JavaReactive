package com.reactive.S13stepverify;

import com.reactive.S09batching.assignment.BookOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class L04AsserTest {


    @Test
    public void test1() {

        Mono<BookOrder> mono = Mono.fromSupplier(BookOrder::new);

        StepVerifier.create(mono)
                .assertNext(b -> Assertions.assertNotNull(b.title()))
                .verifyComplete();

    }

    @Test
    public void test2() {

        // imagine the service is very slow, then it is better to provide a timeout to the test
        Mono<BookOrder> mono = Mono.fromSupplier(BookOrder::new)
                .delayElement(Duration.ofSeconds(3));
        StepVerifier.create(mono)
                .assertNext(b -> Assertions.assertNotNull(b.title()))
                .expectComplete()
                .verify(Duration.ofSeconds(4)); // we expect to finish in a max time

    }


}
