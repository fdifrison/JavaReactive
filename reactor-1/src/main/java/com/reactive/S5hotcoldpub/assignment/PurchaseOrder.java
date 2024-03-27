package com.reactive.S5hotcoldpub.assignment;

import com.reactive.util.Util;

public record PurchaseOrder(String item, Double price, String category, int quantity) {

    public PurchaseOrder() {
        this(
                Util.faker().commerce().productName(),
                Double.parseDouble(Util.faker().commerce().price().replace(",", ".")),
                Util.faker().commerce().department(),
                Util.faker().random().nextInt(1, 10)
        );
//        System.out.println("Created:"+ this);

    }
}
