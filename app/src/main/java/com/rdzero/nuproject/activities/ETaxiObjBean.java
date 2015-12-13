package com.rdzero.nuproject.activities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ETaxiObjBean {

    @SerializedName("favorites")
    public List<ETaxiBean> favorite = new ArrayList<ETaxiBean>();

    public ETaxiObjBean(List<ETaxiBean> favorite) {
        this.favorite = favorite;
    }

    public List<ETaxiBean> getFavorite() {
        return favorite;
    }

    public void setFavorite(List<ETaxiBean> favorite) {
        this.favorite = favorite;
    }
}
