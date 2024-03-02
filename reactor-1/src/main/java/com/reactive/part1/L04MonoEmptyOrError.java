package com.reactive.part1;

import com.reactive.util.Util;
import reactor.core.publisher.Mono;

public class L04MonoEmptyOrError {

    public static void main(String[] args) {
        // here is the SUBSCRIBER

        int userId = 3;
        userRepository(userId)
                .subscribe(
                        Util.onNext(),
                        Util.onError(),
                        Util.onComplete());

    }

    // PUBLISHER
    // simulating a repository that fetches data from db
    private static Mono<String> userRepository(int userId) {

        if (userId == 1) {
            return Mono.just(Util.faker().name().firstName());
        } else if (userId == 2) {
            return Mono.empty(); // NEVER RETURN NULL
        } else {
            return Mono.error(new RuntimeException("OutOfBound Id!"));
        }


    }

}
