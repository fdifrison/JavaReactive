package com.reactive.util;

public record Person(String name, int age) {
    public Person() {
        this(Util.faker().name().firstName(), Util.faker().random().nextInt(1, 99));
    }
}
