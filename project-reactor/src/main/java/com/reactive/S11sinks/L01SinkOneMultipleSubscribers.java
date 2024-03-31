package com.reactive.S11sinks;

import com.reactive.util.Util;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class L01SinkOneMultipleSubscribers {

    public static void main(String[] args) {


        Sinks.One<Object> sink = Sinks.one(); 

        Mono<Object> mono = sink.asMono();

        // we can have multiple subscribers to the same mono that receives data from the same sink
        mono.subscribe(Util.subscriber("Frodo"));
        mono.subscribe(Util.subscriber("Sam"));
        mono.subscribe(Util.subscriber("Pippin"));

        sink.tryEmitValue("hi");
        
        
        

    }

}
