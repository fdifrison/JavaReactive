package com.reactive.S09batching.assignment;

import com.reactive.util.Util;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class PurchaseOrder {
    private String item;
    private Double price;
    private String category;

    public PurchaseOrder() {
        this(
                Util.faker().commerce().productName(),
                Double.parseDouble(Util.faker().commerce().price().replace(",", ".")),
                Util.faker().commerce().department()
        );

    }

}
