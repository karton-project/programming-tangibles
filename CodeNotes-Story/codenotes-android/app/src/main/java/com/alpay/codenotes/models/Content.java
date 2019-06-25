package com.alpay.codenotes.models;

public class Content{

    private String id;
    private String name;
    private String detail;
    private String body;
    private String image;
    private String videoLink;

    public Content(){

    }

    public Content(String id, String name, String detail, String body, String image, String link) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.body = body;
        this.image = image;
        this.videoLink = link;
    }

    public String getContentID() {
        return id;
    }

    public void setContentID(String contentID) {
        this.id = contentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }
}
