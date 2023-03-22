package com.example.reactivephotoapp.service;

import com.example.reactivephotoapp.model.Photo;
import com.example.reactivephotoapp.model.UnsplashResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class UnsplashService {

    @Autowired
    WebClient webClient;

    public Flux<Photo> getPhotos(String searchText, String orientation) {
        return getTotalPages(searchText, orientation)
                .flatMapMany(t -> Flux.range(1, t > 5 ? 5 : t))
                .flatMap(f -> searchUnsplash(searchText, orientation, f)
                        .flatMapIterable(UnsplashResponse::getResults), 1);
    }

    public Mono<Integer> getTotalPages(String searchText, String orientation) {
        return webClient.get()
                .uri(uri -> uri
                        .queryParam("page", "1")
                        .queryParam("query", searchText)
                        .queryParam("orientation", orientation).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(UnsplashResponse.class)
                .map(UnsplashResponse::getTotalPages)
                .map(Integer::valueOf);
    }

    public Mono<UnsplashResponse> searchUnsplash(String searchText, String orientation, int pageNumber) {
        return Mono.empty()
                        .delaySubscription(Duration.ofSeconds(2))
                                .then(
                webClient.get()
                .uri(uri -> uri
                        .queryParam("page", pageNumber)
                        .queryParam("query", searchText)
                        .queryParam("orientation", orientation)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UnsplashResponse.class));
    }
}
