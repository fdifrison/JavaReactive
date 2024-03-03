package com.reactive.S2flux;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

import java.util.List;

public class L03FluxFromArrayOrList {

    public static void main(String[] args) {

        List<Integer> integerList = List.of(1, 2, 3, 4, 5);

        Flux.fromIterable(integerList)
                .subscribe(Util.onNext());

        Integer[] arr = {6,7,8,9,10};

        Flux.fromArray(arr)
                .subscribe(Util.onNext());

    }

}
