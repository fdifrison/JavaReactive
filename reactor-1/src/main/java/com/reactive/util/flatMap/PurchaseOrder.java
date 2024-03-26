package com.reactive.util.flatMap;

import com.reactive.util.Util;

public record PurchaseOrder(String item, String price, int userId) {

    public PurchaseOrder(int userId) {
        this(Util.faker().commerce().productName(), Util.faker().commerce().price(), userId);
    }
}
