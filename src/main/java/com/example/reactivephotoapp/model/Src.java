package com.example.reactivephotoapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URI;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Src {

    private URI original;
    private URI large;
    private URI medium;
    private URI small;
    @JsonProperty("tiny")
    private URI thumb;
}
