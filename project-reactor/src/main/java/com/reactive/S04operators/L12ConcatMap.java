package com.reactive.S04operators;

import com.reactive.util.Util;
import com.reactive.util.flatMap.OrderService;
import com.reactive.util.flatMap.UserService;

public class L12ConcatMap {

    public static void main(String[] args) {

        UserService.getUsers()
                // with concatMap we assure that the order of execution is respected as per request;
                // i.e., the orders will be shown for the first user
                // and only when completed we will pass to the second user's orders and so on
                .concatMap(user -> OrderService.getOrders(user.userId()))
                .subscribe(Util.subscriber());


        Util.sleepSeconds(10); // to see what happens outside the main thread

    }
}
