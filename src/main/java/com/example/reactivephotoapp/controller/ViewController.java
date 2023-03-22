package com.example.reactivephotoapp.controller;

import com.example.reactivephotoapp.model.PexelsPhoto;
import com.example.reactivephotoapp.model.Photo;
import com.example.reactivephotoapp.model.SearchKeyword;
import com.example.reactivephotoapp.service.PexelsService;
import com.example.reactivephotoapp.service.UnsplashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class ViewController {

    @Autowired
    UnsplashService unsplashService;

    @Autowired
    PexelsService pexelsService;

    @GetMapping("/")
    public String displayIndex(Model model) {
        model.addAttribute("searchKeyword", new SearchKeyword());
        model.addAttribute("photos", Flux.empty());
        return "index";
    }

    @PostMapping("/")
    public String performSearch(@ModelAttribute("searchKeyword") SearchKeyword searchKeyword, Model model) {
        Flux<PexelsPhoto> pexelsPhotos = pexelsService.getPhotos(searchKeyword.getText(), searchKeyword.getOrientation());
        Mono<Integer> pexelsPages = pexelsService.getTotalPages(searchKeyword.getText(), searchKeyword.getOrientation());

        Flux<Photo> unsplashPhotos = unsplashService.getPhotos(searchKeyword.getText(), searchKeyword.getOrientation());
        Mono<Integer> unsplashPages =unsplashService.getTotalPages(searchKeyword.getText(), searchKeyword.getOrientation());

        ReactiveDataDriverContextVariable reactivePhotos;
        Mono<Integer> pages;

        if (searchKeyword.getSource().equals("unsplash")){
            reactivePhotos = new ReactiveDataDriverContextVariable(unsplashPhotos, 1);
            pages = unsplashPages;
        } else if (searchKeyword.getSource().equals("pexels")) {
            reactivePhotos = new ReactiveDataDriverContextVariable(pexelsPhotos, 1);
            pages = pexelsPages;
        } else {
            reactivePhotos = new ReactiveDataDriverContextVariable(Flux.concat(pexelsPhotos, unsplashPhotos));
            pages = Mono.zip(unsplashPages, pexelsPages, Integer::sum);
        }

        model.addAttribute("photos", reactivePhotos);
        model.addAttribute("pages", pages);
        model.addAttribute("searchText", searchKeyword.getText());
        return "index";
    }
}
