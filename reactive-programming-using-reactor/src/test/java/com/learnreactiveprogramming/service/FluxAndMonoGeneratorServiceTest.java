package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

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
        var namesFlux = fluxAndMonoGeneratorService.namesFluxFlatMapAsync(3);

        StepVerifier.create(namesFlux)
                .expectNext("j", "o", "h", "n")
                .expectNextCount(5)
                .verifyComplete();
    }
}