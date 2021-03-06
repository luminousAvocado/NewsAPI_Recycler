package com.example.rkjc.news_app_2;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "news_item")
public class NewsItem {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;

    public NewsItem(int id, String author, String title, String description, String url, String urlToImage, String publishedAt) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    @Ignore
    public NewsItem(String author, String title, String description, String url, String urlToImage, String publishedAt) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) { this.author = author; }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) { this.description = description; }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) { this.url = url; }

    public String getUrlToImage() {
        return this.urlToImage;
    }

    public void setUrlToImage(String urlToImage) { this.urlToImage = urlToImage; }

    public String getPublishedAt() {
        return this.publishedAt;
    }

    public void setPublishedAt(String publishedAt) { this.publishedAt = publishedAt; }

}