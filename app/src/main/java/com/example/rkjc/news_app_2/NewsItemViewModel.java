package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class NewsItemViewModel extends AndroidViewModel {

    private NewsItemRepository newsItemRepo;

    private LiveData<List<NewsItem>> news;

    public NewsItemViewModel(Application application) {
        super(application);
        newsItemRepo = new NewsItemRepository(application);
        news = newsItemRepo.loadAllNewsItems();
    }

    LiveData<List<NewsItem>> loadAllNewsItems() {
        return news;
    }

    public void insert(List<NewsItem> newsItems) {
        newsItemRepo.insert(newsItems);
    }
}
