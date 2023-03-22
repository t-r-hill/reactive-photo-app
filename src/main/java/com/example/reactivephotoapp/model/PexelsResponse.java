package com.example.reactivephotoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PexelsResponse {

    private Integer page;

    @JsonProperty("per_page")
    private Integer perPage;

    @JsonProperty("total_results")
    private Long totalResults;

    @JsonIgnore
    private Integer totalPages;

    private List<PexelsPhoto> photos;

    public Integer getTotalPages(){
        return Math.toIntExact(totalResults % perPage == 0 ? totalResults / perPage : totalResults / perPage + 1);
    }

}
