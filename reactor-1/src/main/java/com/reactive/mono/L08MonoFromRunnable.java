package com.reactive.mono;

import com.reactive.util.Util;
import reactor.core.publisher.Mono;

public class L08MonoFromRunnable {

    public static void main(String[] args) {

        Runnable runnable = () -> System.out.println("I'm a runnable, i dont accept nor return parameters!");

        // the only utility of a Mono.fromRunnable is to receive a notification

        Mono.fromRunnable(timeConsumingProcess())
                .subscribe(Util.onNext(),
                        Util.onError(),
                        Util.onComplete());

    }

    private static  Runnable timeConsumingProcess() {
        return () -> {
            Util.sleep(3);
            System.out.println("Finally I'm done!");
        };
    }

}
