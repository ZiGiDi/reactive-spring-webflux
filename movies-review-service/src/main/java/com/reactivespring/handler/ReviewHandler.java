package com.reactivespring.handler;

import com.reactivespring.domain.Review;
import com.reactivespring.repository.ReviewReactiveRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class ReviewHandler {

    private ReviewReactiveRepository reviewReactiveRepository;

    public Mono<ServerResponse> addReview(ServerRequest request) {

        return request.bodyToMono(Review.class)
                .flatMap(review -> reviewReactiveRepository.save(review))
                .flatMap(savedReview -> ServerResponse.status(HttpStatus.CREATED)
                        .bodyValue(savedReview))
                .log();
    }

    public Mono<ServerResponse> getReviews(ServerRequest serverRequest) {
        Flux<Review> reviews = reviewReactiveRepository.findAll();
        return ServerResponse.ok().body(reviews, Review.class);
    }
}
