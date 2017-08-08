package br.com.luisfelipeas5.givemedetails.model.model.reviews;

import com.google.gson.annotations.SerializedName;

public class ReviewTMDb implements Review {
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getContent() {
        return content;
    }
}
