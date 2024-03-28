package com.reactive.S04operators;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

public class L04LimitRate {

    public static void main(String[] args) {

        Flux.range(1, 1000)
                .log()
                .limitRate(100, 75) // im setting the request from unbounded to 100 items;
                // at 75% consumption of the request the subscriber we'll ask for more data!
                // the lowTide indicates
                // at what percentage of the request size the subscriber needs to request for more data;
                .subscribe(Util.subscriber());

    }

}
