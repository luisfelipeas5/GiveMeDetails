package br.com.luisfelipeas5.givemedetails.model.model.trailer;

import com.google.gson.annotations.SerializedName;

public class TrailerTMDb implements Trailer {

    @SerializedName("name")
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getThumbUrl() {
        return null;
    }
}
