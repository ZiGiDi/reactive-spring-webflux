package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

class FluxAndMonoGeneratorServiceTest {
    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    @Test
    void namesFlux() {
        var namesFlux = fluxAndMonoGeneratorService.namesFlux();

        StepVerifier.create(namesFlux)
//                .expectNext("bob", "don","john")\
//                .expectNextCount(3)
                .expectNext("bob")
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void namesFluxMap() {
        Flux<String> stringFlux = fluxAndMonoGeneratorService.namesFluxMap();
        StepVerifier.create(stringFlux)
                .expectNext("BOB", "DON", "JOHN")
                .verifyComplete();
    }

    @Test
    void namesFluxImmutability() {
        Flux<String> namesFlux = fluxAndMonoGeneratorService.namesFluxImmutability();
        StepVerifier.create(namesFlux)
                .expectNext("bob", "don", "john")
                .verifyComplete();
    }

    @Test
    void namesFluxFilter() {
        var namesFlux = fluxAndMonoGeneratorService.namesFluxFilter(3);

        StepVerifier.create(namesFlux)
                .expectNext("4-john")
                .expectNextCount(1)
                .verifyComplete();
    }


    @Test
    void namesFluxFlatMap() {
        var namesFlux = fluxAndMonoGeneratorService.namesFluxFlatMap(3);

        StepVerifier.create(namesFlux)
                .expectNext("j", "o", "h", "n")
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    void namesFluxFlatMapAsync() {
        var namesFlux = fluxAndMonoGeneratorService.namesFluxFlatMapAsync(3);

        StepVerifier.create(namesFlux)
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void namesFluxFlatConcatMapAsync() {
        var namesFlux = fluxAndMonoGeneratorService.namesFluxFlatConcatMapAsync(3);

        StepVerifier.create(namesFlux)
                .expectNext("j", "o", "h", "n")
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    void namesMonoFlatMap() {
        Mono<List<String>> namesMono = fluxAndMonoGeneratorService.namesMonoFlatMap();

        StepVerifier.create(namesMono)
                .expectNext(List.of("P", "E", "R", "S", "O", "N"))
                .verifyComplete();
    }

    @Test
    void namesMonoFlatMapMany() {
        Flux<String> namesMono = fluxAndMonoGeneratorService.namesMonoFlatMapMany();

        StepVerifier.create(namesMono)
                .expectNext("P", "E", "R", "S", "O", "N")
                .verifyComplete();
    }

    @Test
    void namesFluxTransform() {
        var namesFlux = fluxAndMonoGeneratorService.namesFluxTransform(3);

        StepVerifier.create(namesFlux)
                .expectNext("j", "o", "h", "n")
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    void namesFluxTransformDefault() {
        var namesFlux = fluxAndMonoGeneratorService.namesFluxTransform(6);

        StepVerifier.create(namesFlux)
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void namesFluxTransformSwitchIfEmpty() {
        var namesFlux = fluxAndMonoGeneratorService.namesFluxTransformSwitchIfEmpty(6);

        StepVerifier.create(namesFlux)
                .expectNext("d", "e", "f", "a", "u", "l", "t")
                .verifyComplete();
    }

    @Test
    void exploreConcat() {
        Flux<String> concat = fluxAndMonoGeneratorService.exploreConcat();

        StepVerifier.create(concat)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void exploreConcatWith() {
        Flux<String> concat = fluxAndMonoGeneratorService.exploreConcatWith();

        StepVerifier.create(concat)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void exploreMerge() {
        Flux<String> concat = fluxAndMonoGeneratorService.exploreMerge();

        StepVerifier.create(concat)
                .expectNext("A", "D", "B", "E", "C", "F")
                .verifyComplete();
    }

    @Test
    void exploreMergeWith() {
        Flux<String> concat = fluxAndMonoGeneratorService.exploreMergeWith();

        StepVerifier.create(concat)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void exploreMergeSequential() {
        Flux<String> concat = fluxAndMonoGeneratorService.exploreMergeSequential();

        StepVerifier.create(concat)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void exploreMergeZip() {
        Flux<String> concat = fluxAndMonoGeneratorService.exploreZip();

        StepVerifier.create(concat)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    void exploreMergeZipWithTuple() {
        Flux<String> concat = fluxAndMonoGeneratorService.exploreZipWithTuple();

        StepVerifier.create(concat)
                .expectNext("AD14", "BE25", "CF36")
                .verifyComplete();
    }

    @Test
    void exploreZipWith() {
        Flux<String> concat = fluxAndMonoGeneratorService.exploreZipWith();

        StepVerifier.create(concat)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    void exploreZipMono() {
        Mono<String> zip = fluxAndMonoGeneratorService.exploreZipMono();

        StepVerifier.create(zip)
                .expectNext("AB")
                .verifyComplete();
    }
}