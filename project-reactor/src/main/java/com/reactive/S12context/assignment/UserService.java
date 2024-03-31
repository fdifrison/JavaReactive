package com.reactive.S12context.assignment;

import reactor.util.context.Context;

import java.util.Map;
import java.util.function.Function;

public class UserService {

    // a Map of users and their account type
    private static final Map<String, String> map = Map.of(
            "Sam", "std",
            "Frodo", "premium"
    );

    public static Function<Context, Context> userCategoryContext() {
        return ctx -> {
            String user = ctx.get("user").toString(); // we receive a context and want to check its account type
            String userType = map.get(user);
            return ctx.put("type", userType); // we set the type user we've found at db back to the context
            // and later use it as an authorization header
        };
    }

}
