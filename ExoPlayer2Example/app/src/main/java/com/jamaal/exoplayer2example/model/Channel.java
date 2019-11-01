package com.jamaal.exoplayer2example.model;


import java.io.Serializable;

import lombok.Data;

@Data

public class Channel implements Serializable {

    private   int notworking =0 ;

    private   String uri;

    private   String img;
    private   String language;
    private   String name;
private Channel backup;
    private   int category=0;
    private   long dbid;
    public Channel(String name, String language, String image, String uri) {
        this.name = name;
        this.language = language;
        this.uri = uri;
        this.img = image;
        language="AR";
    }


    public static Channel mock() {
        double x =Math.random();
        return new Channel("MockedTv" , "ar" , x>.5f ?"https://video.togdddgle.sg/image/10362468/16x9/947/533/57b91571f7a6ad4068e0ecb738abc0aa/xu/bein-sports-max-2.png":"https://assets-9gag-fun.9cache.com/s/fab0aa49/326129b4882f507e7df4146af98363076b70423c/static/dist/web6/img/sprite-logo.png" , x>.5f?"http://restream.geniptv.com:8080/live:sageevshantha/vf6nMKIQwq/995" : "https://www.youtube.com/watch?v=xbxDuKqV61Y")
                ;
    }
}