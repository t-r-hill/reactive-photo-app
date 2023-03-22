package com.example.reactivephotoapp.service;

import com.example.reactivephotoapp.model.PexelsPhoto;
import com.example.reactivephotoapp.model.PexelsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PexelsService {

    @Autowired
    WebClient pexelsClient;

    public Mono<PexelsResponse> searchPexels(String searchTerm, String orientation, int pageNum){
        return pexelsClient.get()
                .uri(uri -> uri
                        .queryParam("query", searchTerm)
                        .queryParam("orientation", orientation)
                        .queryParam("page", pageNum)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PexelsResponse.class);
    }

    public Mono<Integer> getTotalPages(String searchTerm, String orientation){
        return pexelsClient.get()
                .uri(uri -> uri
                        .queryParam("query", searchTerm)
                        .queryParam("orientation", orientation)
                        .queryParam("page", "1")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PexelsResponse.class)
                .map(PexelsResponse::getTotalPages);
    }

    public Flux<PexelsPhoto> getPhotos(String searchTerm, String orientation){
        return getTotalPages(searchTerm, orientation)
                .flatMapMany(p -> Flux.range(1, p > 5 ? 5 : p))
                .flatMap(pn -> searchPexels(searchTerm, orientation, pn)
                        .flatMapIterable(PexelsResponse::getPhotos), 5);
    }
}
