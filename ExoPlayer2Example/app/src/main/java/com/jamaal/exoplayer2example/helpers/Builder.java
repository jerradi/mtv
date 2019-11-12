package com.jamaal.exoplayer2example.helpers;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Builder {
  private static   NetworkHelper networkHelper = null ;
  public static NetworkHelper getNetworkHelper(){
      if (networkHelper==null) {
          Retrofit retrofit = new Retrofit.Builder()
                  .baseUrl("http://192.168.1.174:8108/")
                  .addConverterFactory(GsonConverterFactory.create())
                  .build();

          networkHelper = retrofit.create(NetworkHelper.class);

      } return  networkHelper;
  }
}
