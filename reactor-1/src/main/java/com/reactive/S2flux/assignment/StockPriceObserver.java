package com.reactive.S2flux.assignment;

import com.reactive.util.Util;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;

public class StockPriceObserver {

    public static int INTIAL_STOCK_PRICE = 100;

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);

        getStockPrice().subscribeWith(new Subscriber<Integer>() {

            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                subscription.request(Long.MAX_VALUE); // unbounded request, give me what you have!
            }

            @Override
            public void onNext(Integer integer) {
                if (integer > 110 || integer < 90) {
                    this.subscription.cancel();
                    latch.countDown();
                    System.out.println("Reached target price");
                } else {
                    System.out.printf("%s : %d Price%n", LocalDateTime.now(), integer);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                latch.countDown();
            }

            @Override
            public void onComplete() {
                latch.countDown();
            }
        });

        latch.await();

    }

    private static Flux<Integer> getStockPrice() {

        return Flux.interval(Duration.ofMillis(500L))
                .map(i -> INTIAL_STOCK_PRICE += Util.faker().random().nextInt(-8, 8));


    }
}
