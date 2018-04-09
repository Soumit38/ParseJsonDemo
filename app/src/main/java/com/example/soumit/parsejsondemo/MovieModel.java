package com.example.soumit.parsejsondemo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieModel {

    private String  movie;
    private String year;
    private String rating;
    private String duration;
    private String director;
    private String tagline;
    @SerializedName("cast")
    private List<Cast> castList;
    private String image;
    private String story;

    public MovieModel() {
    }

    public MovieModel(String movie, String year, String rating, String duration,
                      String director, String tagline, List<Cast> castList, String image, String story) {
        this.movie = movie;
        this.year = year;
        this.rating = rating;
        this.duration = duration;
        this.director = director;
        this.tagline = tagline;
        this.castList = castList;
        this.image = image;
        this.story = story;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public List<Cast> getCastList() {
        return castList;
    }

    public void setCastList(List<Cast> castList) {
        this.castList = castList;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    @Override
    public String toString() {
        return "MovieModel{" +
                "movie='" + movie + '\'' +
                ", year='" + year + '\'' +
                ", rating='" + rating + '\'' +
                ", duration='" + duration + '\'' +
                ", director='" + director + '\'' +
                ", tagline='" + tagline + '\'' +
                ", castList=" + castList +
                ", image='" + image + '\'' +
                ", story='" + story + '\'' +
                '}';
    }
}
