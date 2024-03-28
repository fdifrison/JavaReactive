package com.reactive.S08combinepublishers;

import com.reactive.util.NameGeneratorCash;
import com.reactive.util.Util;

public class L01StartWith {

    // LAZY strategy

    // Start with will allow the publisher to supply first data from another publisher
    // (in this example, something that simulates a cache).
    // For example, the main emission block is time-consuming,
    // and also because there might be several subscribers subscribing at different times or with different requirements

    public static void main(String[] args) {

        NameGeneratorCash generator = new NameGeneratorCash();

        generator.generateNames()
                .take(2)
                .subscribe(Util.subscriber("Frodo"));
        generator.generateNames()
                .take(4)
                .subscribe(Util.subscriber("Gandalf"));
        generator.generateNames()
                .filter(n -> n.startsWith("A"))
                .take(4)
                .subscribe(Util.subscriber("Aragon"));


    }

}
