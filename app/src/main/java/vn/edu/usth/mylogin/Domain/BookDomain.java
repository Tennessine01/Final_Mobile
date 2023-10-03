package vn.edu.usth.mylogin.Domain;


import java.io.Serializable;

public class BookDomain implements Serializable {
    private String title;
    private String description;
    private String picUrl;

    private String content;
    private int time;
    private double rating;
    private String author;
    private int numberinLibrary;

    public BookDomain(String title, String description, String content, String picUrl, int time, double rating, String author) {
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.time = time;
        this.rating = rating;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getNumberinLibrary() {
        return numberinLibrary;
    }

    public void setNumberinLibrary(int numberinCart) {
        this.numberinLibrary = numberinCart;
    }
}

