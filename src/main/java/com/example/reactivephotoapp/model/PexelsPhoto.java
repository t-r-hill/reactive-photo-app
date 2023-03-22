package com.example.reactivephotoapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PexelsPhoto {

    String id;

    @JsonProperty("alt")
    String description;

    @JsonProperty("src")
    Src urls;

}
