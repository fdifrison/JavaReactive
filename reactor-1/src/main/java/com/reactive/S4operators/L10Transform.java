package com.reactive.S4operators;

import com.reactive.util.Person;
import com.reactive.util.Util;
import reactor.core.publisher.Flux;

import java.util.function.Function;

public class L10Transform {

    public static void main(String[] args) {

        getPerson()
                .transform(applyFilterMap())
                .subscribe(Util.subscriber());


    }

    public static Flux<Person> getPerson() {
        return Flux.range(1, 10)
                .map(i -> new Person());
    }

    // We are creating a custom operator that aggregates several steps;
    // This can be useful in case we have several pipelines that share similar or same operations.
    public static Function<Flux<Person>, Flux<Person>> applyFilterMap() {
        return flux -> flux
                .filter(p -> p.age() > 18)
                .map(p -> new Person(p.name().toUpperCase(), p.age()))
                .doOnDiscard(Person.class, p -> System.out.println("Minorenne: " + p));
    }
}
