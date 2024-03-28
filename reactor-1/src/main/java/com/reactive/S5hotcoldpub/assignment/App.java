package com.reactive.S5hotcoldpub.assignment;

import com.reactive.util.Util;

public class App {

    public static void main(String[] args) {

        OrderService orderService = new OrderService();
        InventoryService inventoryService = new InventoryService();
        RevenueService revenueService = new RevenueService();

        // revenue and inventory observe the order-stream
        orderService.orderStream().subscribe(revenueService.subscribeOrderStream());
        orderService.orderStream().subscribe(inventoryService.subscribeInventoryStream());

        inventoryService.inventoryStream()
                .subscribe(Util.subscriber("InventoryManager"));

        revenueService.revenueStream()
                .subscribe(Util.subscriber("RevenueManager"));

        Util.sleepSeconds(60);

    }

}
