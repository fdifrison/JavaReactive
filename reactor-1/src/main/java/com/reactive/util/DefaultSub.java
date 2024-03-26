package com.reactive.util;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class DefaultSub implements Subscriber<Object> {

    private String name = "Subscriber";

    public DefaultSub(String name) {
        this.name = name;
    }

    public DefaultSub() {
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(Object o) {
        System.out.printf("%s:onNext:%s%n", name, o.toString());
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.printf("%s:onError:%s%n", name, throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.printf("%s:onComplete%n", name);
    }
}
