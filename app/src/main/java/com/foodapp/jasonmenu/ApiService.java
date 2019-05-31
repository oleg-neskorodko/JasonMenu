package com.foodapp.jasonmenu;


import com.google.gson.JsonElement;

import org.json.JSONArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("/oleg-neskorodko/3e8a01d0ce4a15b64affc55165f2b85e/raw/69172790b67d71b6212bccb45f9c77bef50b3d9e/{link1}")
    Call<JsonElement> getMenu(@Path("link1") String menuLink);

    @GET("/data/all/{link2}")
    Call<JsonElement> getCoinlist(@Path("link2") String coinlistLink);

    @GET("/pavel-zlotarenchuk/{link2}")
    Call<JsonElement> getList(@Path("link2") String coinlistLink);


}
