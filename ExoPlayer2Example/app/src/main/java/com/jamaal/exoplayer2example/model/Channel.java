package com.jamaal.exoplayer2example.model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;





@Entity
public class Channel implements Serializable {
    static final    long  serialVersionUID = 536871008l;
   @Id
    private Long id;
    private   Integer notworking =0 ;

    private   String uri;

    private   String img;
    private   String language;
    private   String name; 
    private   Integer category=0;
    private   Long dbid;

    public Channel(String name, String language, String image, String uri) {
        this.name = name;
        this.language = language;
        this.uri = uri;
        this.img = image;
        language="AR";
    }


    @Generated(hash = 444641327)
    public Channel(Long id, Integer notworking, String uri, String img, String language, String name, Integer category, Long dbid) {
        this.id = id;
        this.notworking = notworking;
        this.uri = uri;
        this.img = img;
        this.language = language;
        this.name = name;
        this.category = category;
        this.dbid = dbid;
    }


    @Generated(hash = 459652974)
    public Channel() {
    }


    public static Channel mock() {
        double x =Math.random();
        return new Channel("MockedTv" , "ar" , x>.5f ?"https://video.togdddgle.sg/image/10362468/16x9/947/533/57b91571f7a6ad4068e0ecb738abc0aa/xu/bein-sports-max-2.png":"https://assets-9gag-fun.9cache.com/s/fab0aa49/326129b4882f507e7df4146af98363076b70423c/static/dist/web6/img/sprite-logo.png" , x>.5f?"http://restream.geniptv.com:8080/live:sageevshantha/vf6nMKIQwq/995" : "https://www.youtube.com/watch?v=xbxDuKqV61Y")
                ;
    }

    public static Long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNotworking() {
        return notworking;
    }

    public void setNotworking(Integer notworking) {
        this.notworking = notworking;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Long getDbid() {
        return dbid;
    }

    public void setDbid(Long dbid) {
        this.dbid = dbid;
    }
}