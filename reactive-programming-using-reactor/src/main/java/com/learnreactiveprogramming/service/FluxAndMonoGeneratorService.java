package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoGeneratorService {

    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
        fluxAndMonoGeneratorService.namesFlux()
                .subscribe(name -> System.out.println("Name is: " + name));
        fluxAndMonoGeneratorService.namesMono()
                .subscribe(name -> System.out.println("Mono name is: " + name));
    }

    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("bob", "don", "john"))
                .log();
    }

    public Mono<String> namesMono() {
        return Mono.just("single Person")
                .log();
    }

    public Flux<String> namesFluxMap() {
        return Flux.fromIterable(List.of("bob", "don", "john"))
                .map(String::toUpperCase)
                .log();
    }

    public Flux<String> namesFluxImmutability() {
        Flux<String> namesFlux = Flux.fromIterable(List.of("bob", "don", "john"));
        namesFlux.map(String::toUpperCase);
        return namesFlux;
    }

    public Flux<String> namesFluxFilter(int stringLength) {
        return Flux.fromIterable(List.of("bob", "don", "john", "chloe"))
                .filter(name -> name.length() > stringLength)
                .map(name -> name.length() + "-" + name)
                .log();
    }

    public Flux<String> namesFluxFlatMap(int stringLength) {
        return Flux.fromIterable(List.of("bob", "don", "john", "chloe"))
                .filter(name -> name.length() > stringLength)
                .flatMap(this::splitString)
                .log();
    }

    private Flux<String> splitString(String name) {
        String[] splitArray = name.split("");
        return Flux.fromArray(splitArray);
    }

    public Flux<String> namesFluxFlatMapAsync(int stringLength) {
        return Flux.fromIterable(List.of("bob", "don", "john", "chloe"))
                .filter(name -> name.length() > stringLength)
                .flatMap(this::splitStringWithDelay)
                .log();
    }

    public Flux<String> namesFluxFlatConcatMapAsync(int stringLength) {
        return Flux.fromIterable(List.of("bob", "don", "john", "chloe"))
                .filter(name -> name.length() > stringLength)
                .concatMap(this::splitStringWithDelay) //use when ordering matters
                .log();
    }

    private Flux<String> splitStringWithDelay(String name) {
        String[] splitArray = name.split("");
        int delay = new Random().nextInt(1000);
        return Flux.fromArray(splitArray)
                .delayElements(Duration.ofMillis(delay));
    }

    public Mono<List<String>> namesMonoFlatMap() {
        return Mono.just("Person")
                .map(String::toUpperCase)
                .flatMap(this::splitStringMono)
                .log();
    }

    private Mono<List<String>> splitStringMono(String name) {
        String[] charArray = name.split("");
        return Mono.just(List.of(charArray));

    }

    public Flux<String> namesMonoFlatMapMany() {
        return Mono.just("Person")
                .map(String::toUpperCase)
                .flatMapMany(this::splitString)
                .log();
    }

    public Flux<String> namesFluxTransform(int stringLength) {
        Function<Flux<String>, Flux<String>> filterMap = name -> name.filter(s -> s.length() > stringLength)
                .flatMap(this::splitString);
        return Flux.fromIterable(List.of("bob", "don", "john", "chloe"))
                .transform(filterMap)
                .log();
    }
}
