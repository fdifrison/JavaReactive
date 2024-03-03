package com.reactive.S2flux;

import com.reactive.util.NameGenerator;
import com.reactive.util.Util;

import java.util.List;

public class L07FluxVsList {

    public static void main(String[] args) {

        List<String> names = NameGenerator.getNamesFromList(5);
        System.out.println(names);
        // Names will be printed all at once, everything is blocked due to the computing time of names' generation

        NameGenerator.getNamesFromFlux(5)
                .subscribe(Util.onNext());
        // the total time remains the same, but I'm getting values as soon as they are ready

    }

}
