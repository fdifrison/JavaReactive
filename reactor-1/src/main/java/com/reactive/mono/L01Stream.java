package com.reactive.mono;

import java.util.stream.Stream;

public class L01Stream {

    public static void main(String[] args) {

        Stream<Integer> stream = Stream.of(1)
                .map(i -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return i * 2;
                });

        System.out.println(stream); // I won't see sleeping time her because streams are lazy evaluator

        stream.forEach(System.out::println);
    }

}
