package com.example.reactivephotoapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URI;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Urls {

    URI raw;
    URI full;
    URI small;
    URI regular;
    URI thumb;

    @JsonProperty("small_s3")
    URI smallS3;
}
