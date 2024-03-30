package com.reactive.S09batching.assignment;

import com.reactive.util.Util;

public record BookOrder(String title, String category, double price) {
    public BookOrder() {
        this(Util.faker().book().title(),
                Util.faker().book().genre(),
                Util.faker().random().nextInt(1, 100) * 1d
        );
    }
}
