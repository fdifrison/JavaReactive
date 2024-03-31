package com.reactive.S12context.assignment;

import com.reactive.util.Util;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class BookService {

    // we are creating a rate limiter for consuming our api given the user type is requesting it
    private static final Map<String, Integer> map = new HashMap<>(); // userType, rate limit

    static {
        map.put("std", 2);
        map.put("premium", 3);
    }

    public static Mono<String> getBook() {
        return Mono.deferContextual(ctx -> {
                    if (ctx.get("allow")) {
                        return Mono.just(Util.faker().book().title());
                    } else {
                        return Mono.error(new RuntimeException(
                                "too many requests for user:" + ctx.get("user") + ":" + ctx.get("type")
                        ));
                    }
                })
                .contextWrite(rateLimiterContext());
    }

    private static Function<Context, Context> rateLimiterContext() {
        return ctx -> {
            // we are looking for the header that is set in the userService depending on the username
            if (ctx.hasKey("type")) {
                String userType = ctx.get("type").toString();
                Integer attempt = map.get(userType);
                if (attempt > 0) { // if the user has still attempts, proceed
                    map.put(userType, attempt - 1); // decrease the number of attempts
                    return ctx.put("allow", true); // set to the request context a new header
                    // that will allow him to access the api
                }
            }
            return ctx.put("allow", false);


        };
    }

}
