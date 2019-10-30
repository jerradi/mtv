package com.jamaal.exoplayer2example.helpers;

import com.jamaal.exoplayer2example.model.Channel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NetworkHelper {
        @GET("addedItems")
        Call<List<Channel>> getAll();

}
