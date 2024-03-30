package com.reactive.S04operators;

import com.reactive.util.Util;
import com.reactive.util.flatMap.OrderService;
import com.reactive.util.flatMap.UserService;

public class L12FlatMap {

    public static void main(String[] args) {

        UserService.getUsers()
                // here I only get a Flux as output
                // and without subscription is useless, we will only see FluxCreate for each user
//                .map(user -> OrderService.getOrders(user.userId()))

                // instead,
                // we need a flatMap that internally subscribes to all the publishers created
                // (the purchaseOrders) flatten the data and sent it to the main subscriber
                .flatMap(user -> OrderService.getOrders(user.userId()))
                .subscribe(Util.subscriber());


        Util.sleepSeconds(10); // to see what happens outside the main thread

    }
}
