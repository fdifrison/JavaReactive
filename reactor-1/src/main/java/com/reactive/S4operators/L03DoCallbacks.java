package com.reactive.S4operators;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

public class L03DoCallbacks {

    // operators are like decorators placed around each other onto the subscriber;
    // this means that the order of application can sometimes be relevant!

    public static void main(String[] args) {

        Flux.create(fluxSink -> {
                    System.out.println("Inside create:");
                    for (int i = 0; i < 5; i++) {
                        fluxSink.next(i);
                    }
                    fluxSink.complete();
                    System.out.println("\nCompleted");
                })
                .doOnComplete(() -> System.out.println("doOnComplete"))
                .doFirst(() -> System.out.println("doFirst1"))
                .doFirst(() -> System.out.println("doFirst2"))
                .doFirst(() -> System.out.println("doFirst3")) // this is executed first
                // since the subscriber is calling the publisher,
                // hence the application of the operator is bottom up
                .doOnNext(o -> System.out.println("doOnNext:" + o))
                .doOnSubscribe(subscription -> System.out.println("doOnSubscribe1:" + subscription)) // this is executed first
                // since the subscription object comes from the publisher to the subscriber,
                // hence operators are applied from top to bottom
                .doOnSubscribe(subscription -> System.out.println("doOnSubscribe2:" + subscription))
                .doOnRequest(value -> System.out.println("doOnRequest:" + value))
                .doOnError(err -> System.out.println("doOnError:" + err))
                .doOnTerminate(() -> System.out.println("doOnTerminate"))
                .doOnCancel(() -> System.out.println("doOnCancel"))
                .doFinally(signalType -> System.out.println("doFinally1:" + signalType)) // always executed in the last step
                // even if an error is encountered, like the finally in a while loop
                .doOnDiscard(Object.class, o -> System.out.println("doOnDiscard:" + o))
                // if we restrict the output from an unbounded publisher,
                // some elements will be discarded and can be operated with the doOnDiscard
                .take(2)
                .doFinally(signalType -> System.out.println("doFinally2:" + signalType)) // if the doOnDiscard is called,
                // and it is placed closer to the subscriber,
                // than the doFinally won't be the last operation performed;
                // we can see that doFinally1 is executed before with the signal "cancel"
                // while doFinally2 is executed with the signal "onComplete"
                .subscribe(Util.subscriber());

    }

}
