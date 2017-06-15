package com.example.kdblue.ItechVcet;

/**
 * Created by kulde on 3/29/2017.
 */

public class Topics {
    String id,article,content,givenby,completearticle,imageUrl;



    public Topics() {
        this.id=id;
        this.article=article;
        this.content=content;
        this.givenby=givenby;
        this.completearticle=completearticle;
        this.imageUrl=imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getContent() {

        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setGivenby(String givenby) {
        this.givenby = givenby;
    }
    public String getGivenby() {
        return givenby;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
