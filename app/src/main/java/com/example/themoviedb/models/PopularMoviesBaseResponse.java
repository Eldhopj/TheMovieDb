package com.example.themoviedb.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PopularMoviesBaseResponse {
    @SerializedName("results")
    @Expose
    private List<MovieList> movieLists = null;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("dates")
    @Expose
    private Dates dates;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;

    public List<MovieList> getMovieLists() {
        return movieLists;
    }

    public void setMovieLists(List<MovieList> movieLists) {
        this.movieLists = movieLists;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Dates getDates() {
        return dates;
    }

    public void setDates(Dates dates) {
        this.dates = dates;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
