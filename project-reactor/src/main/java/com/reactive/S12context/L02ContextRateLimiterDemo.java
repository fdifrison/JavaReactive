package com.reactive.S12context;

import com.reactive.S12context.assignment.BookService;
import com.reactive.S12context.assignment.UserService;
import com.reactive.util.Util;
import reactor.util.context.Context;

public class L02ContextRateLimiterDemo {

    public static void main(String[] args) {

        BookService.getBook()
                .repeat(5) // as soon
                // as we hit the max number of request
                // given by the rate limiter, we will get the onError msg back and the subscription will be canceled
                .contextWrite(UserService.userCategoryContext())
                .contextWrite(Context.of("user", "Frodo"))
                .subscribe(Util.subscriber("Frodo"));

    }

}
