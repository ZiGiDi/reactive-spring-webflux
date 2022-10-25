package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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
}
