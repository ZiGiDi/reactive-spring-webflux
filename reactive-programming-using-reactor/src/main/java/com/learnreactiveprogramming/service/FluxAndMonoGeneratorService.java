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
                .defaultIfEmpty("default")
                .log();
    }

    public Flux<String> namesFluxTransformSwitchIfEmpty(int stringLength) {
        Function<Flux<String>, Flux<String>> filterMap = name -> name.filter(s -> s.length() > stringLength)
                .flatMap(this::splitString);
        Flux<String> defaultFlux = Flux.just("default")
                .transform(filterMap);
        return Flux.fromIterable(List.of("bob", "don", "john", "chloe"))
                .transform(filterMap)
                .switchIfEmpty(defaultFlux)
                .log();
    }

    public Flux<String> exploreConcat() {
        Flux<String> abcFlux = Flux.just("A", "B", "C");
        Flux<String> defFlux = Flux.just("D", "E", "F");
        return Flux.concat(abcFlux, defFlux)
                .log();
    }

    public Flux<String> exploreConcatWith() {
        Mono<String> aMono = Mono.just("A");
        Mono<String> bMono = Mono.just("B");
        Mono<String> cMono = Mono.just("C");
        Flux<String> defFlux = Flux.just("D", "E", "F");
        return aMono.concatWith(bMono)
                .concatWith(cMono)
                .concatWith(defFlux)
                .log();
    }

    public Flux<String> exploreMerge() {
        Flux<String> abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100));
        Flux<String> defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(125));
        return Flux.merge(abcFlux, defFlux)
                .log();
    }

    public Flux<String> exploreMergeWith() {
        Mono<String> aMono = Mono.just("A");
        Mono<String> bMono = Mono.just("B");
        Mono<String> cMono = Mono.just("C");
        Flux<String> defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(125));
        return aMono.mergeWith(bMono)
                .mergeWith(cMono)
                .mergeWith(defFlux)
                .log();
    }

    public Flux<String> exploreMergeSequential() {
        Flux<String> abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100));
        Flux<String> defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(125));
        return Flux.mergeSequential(abcFlux, defFlux)
                .log();
    }

    public Flux<String> exploreZip() {
        Flux<String> abcFlux = Flux.just("A", "B", "C");
        Flux<String> defFlux = Flux.just("D", "E", "F", "G");
        return Flux.zip(abcFlux, defFlux, (first, second) -> first + second)
                .log();
    }

    public Flux<String> exploreZipWithTuple() {
        Flux<String> abcFlux = Flux.just("A", "B", "C");
        Flux<String> defFlux = Flux.just("D", "E", "F", "G");
        Flux<String> _123Flux = Flux.just("1", "2", "3");
        Flux<String> _456Flux = Flux.just("4", "5", "6", "7", "8");
        return Flux.zip(abcFlux, defFlux, _123Flux, _456Flux)
                .map(t4 -> t4.getT1() + t4.getT2() + t4.getT3() + t4.getT4())
                .log();
    }

    public Flux<String> exploreZipWith() {
        Flux<String> abcFlux = Flux.just("A", "B", "C");
        Flux<String> defFlux = Flux.just("D", "E", "F", "G");
        return abcFlux.zipWith(defFlux, (first, second) -> first + second)
                .log();
    }

    public Mono<String> exploreZipMono() {
        Mono<String> aMono = Mono.just("A");
        Mono<String> bMono = Mono.just("B");
        return aMono.zipWith(bMono)
                .map(t2 -> t2.getT1() + t2.getT2())
                .log();
    }
}
