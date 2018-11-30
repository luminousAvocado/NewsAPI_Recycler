package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.net.Network;
import android.os.AsyncTask;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsItemRepository {
    private NewsItemDao mNewsItemDao;
    private LiveData<List<NewsItem>> mAllNews;

    public NewsItemRepository(Application application) {
        NewsItemRoomDatabase db = NewsItemRoomDatabase.getDatabase(application.getApplicationContext());
        mNewsItemDao = db.newsDao();
        mAllNews = mNewsItemDao.loadAllNewsItems();
    }

    LiveData<List<NewsItem>> loadAllNewsItems() {
        return mAllNews;
    }

    public void insert(List<NewsItem> items) {
        new insertAsyncTask(mNewsItemDao).execute(items);
    }

    public void clearAll(List<NewsItem> items) {
        new clearAllAsyncTask(mNewsItemDao).execute(items);
    }

    // EDIT
    public ArrayList<NewsItem> syncDb(Context context) {
        ArrayList<NewsItem> result;
        URL url = NetworkUtils.buildURL();
        newsApiRequest req = new newsApiRequest(mNewsItemDao);
        newsApiRequest.execute(url);

        return result;
    }

    // EDIT
    public void getNewsDb() {
        new databaseASync(mNewsItemDao);
    }

    // EDIT
    private static class apiCall extends AsyncTask<URL, NewsItemDao, String> {
        String results = null;
        ArrayList<NewsItem> news = new ArrayList<>();

        private NewsItemDao mAsyncTaskDao;

        apiCall(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected String doInBackground(URL... urls) {
            try {
                results = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(results != null && !results.equals("")) {
                mAsyncTaskDao.clearAll();
                ArrayList<NewsItem> news = JsonUtils.parseNews(results);
                mAsyncTaskDao.insert(news);
                mAsyncTaskDao.loadAllNewsItems();
            }

            return null;
        }
    }

}
