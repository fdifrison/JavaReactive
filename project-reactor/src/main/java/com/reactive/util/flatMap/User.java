package com.reactive.util.flatMap;

import com.reactive.util.Util;

public record User(int userId, String name) {
    public User(int userId) {
        this(userId, Util.faker().name().firstName());
    }
}
