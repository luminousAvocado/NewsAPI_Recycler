package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

public class NewsItemViewModel extends AndroidViewModel {

    private NewsItemRepository newsItemRepo;
    private LiveData<List<NewsItem>> news;

    public NewsItemViewModel(Application application) {
        super(application);
        newsItemRepo = new NewsItemRepository(application);
        news = newsItemRepo.getAllNewsItems();
    }

    public void syncDb(Context context) {
        newsItemRepo.sync(context);
    }

    LiveData<List<NewsItem>> getAllNews() {
        return news;
    }

    public NewsItemRepository getNewsRepo() {
        return newsItemRepo;
    }

    public void setNewsRepo(NewsItemRepository mRepo) {
        this.newsItemRepo = mRepo;
    }

    public void setNews(LiveData<List<NewsItem>> mNews) {
        this.news = mNews;
    }
}
