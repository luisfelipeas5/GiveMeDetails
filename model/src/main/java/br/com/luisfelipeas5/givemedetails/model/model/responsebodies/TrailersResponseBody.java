package br.com.luisfelipeas5.givemedetails.model.model.responsebodies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.model.trailer.TrailerTMDb;

public class TrailersResponseBody {
    @SerializedName("results")
    private List<TrailerTMDb> trailers;

    public List<TrailerTMDb> getTrailers() {
        return trailers;
    }
}
