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

import java.util.Optional;

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
        Optional<String> movieInfoId = serverRequest.queryParam("movieInfoId");

        if (movieInfoId.isPresent()) {
            Flux<Review> reviewsByMovieInfoId = reviewReactiveRepository
                    .findReviewsByMovieInfoId(Long.valueOf(movieInfoId.get()));
            return ServerResponse.ok().body(reviewsByMovieInfoId, Review.class);
        } else {
            Flux<Review> reviews = reviewReactiveRepository.findAll();
            return ServerResponse.ok().body(reviews, Review.class);
        }
    }

    public Mono<ServerResponse> updateReview(ServerRequest serverRequest) {
        String reviewId = serverRequest.pathVariable("id");
        Mono<Review> existingReview = reviewReactiveRepository.findById(reviewId);

        return existingReview.flatMap(review -> serverRequest.bodyToMono(Review.class)
                        .map(request -> {
                            review.setComment(request.getComment());
                            review.setRating(request.getRating());
                            return review;
                        }))
                .flatMap(reviewReactiveRepository::save)
                .flatMap(savedReview -> ServerResponse.ok().bodyValue(savedReview));
    }

    public Mono<ServerResponse> deleteReview(ServerRequest serverRequest) {
        String reviewId = serverRequest.pathVariable("id");
        Mono<Review> existingReview = reviewReactiveRepository.findById(reviewId);

        return existingReview.flatMap(review -> reviewReactiveRepository.deleteById(reviewId))
                .then(ServerResponse.noContent().build());
    }
}
