package com.reactive.S02flux;

import com.reactive.util.Util;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicReference;

public class L06FluxSubscription {

    public static void main(String[] args) {

        AtomicReference<Subscription> atomicReference = new AtomicReference<>();

        Flux.range(1, 20)
                .log()
                .subscribeWith(new Subscriber<Integer>() { // we apply our implementation of a subscriber not a lambda function
                                   @Override
                                   public void onSubscribe(Subscription subscription) {
                                       System.out.println("Received Sub: " + subscription);
                                       atomicReference.set(subscription);
                                   }

                                   @Override
                                   public void onNext(Integer integer) {
                                       System.out.println("onNext: " + integer);
                                   }

                                   @Override
                                   public void onError(Throwable throwable) {
                                       System.out.println("onError: " + throwable.getMessage());
                                   }

                                   @Override
                                   public void onComplete() {
                                       System.out.println("onComplete invoked");
                                   }
                               }
                );

        Util.sleepSeconds(3);
        atomicReference.get().request(3);
        Util.sleepSeconds(5);
        atomicReference.get().request(3);
        Util.sleepSeconds(5);
        System.out.println("Cancelling subscription...");
        atomicReference.get().cancel();
        Util.sleepSeconds(5);
        atomicReference.get().request(3); // nothing will happen since we canceled the subscription before
        // one we cancel the subscription we can't invoke for more items


    }

}
