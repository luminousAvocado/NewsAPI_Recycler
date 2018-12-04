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
    private NewsItemRoomDatabase db;

    public NewsItemRepository(Application application) {
        db = NewsItemRoomDatabase.getDatabase(application.getApplicationContext());
        mNewsItemDao = db.newsDao();
        mAllNews = mNewsItemDao.loadAllNewsItems();
        // ANOTHER
        getDataAsyncCall();
    }

    LiveData<List<NewsItem>> getAllNewsItems() {
        return mAllNews;
    }

    public void getDataAsyncCall() {
        new getDataAsyncTask(mNewsItemDao).execute();
    }

    private static class getDataAsyncTask extends AsyncTask<Void, Void, Void> {
        private NewsItemDao mAsyncTaskNewsDao;

        getDataAsyncTask(NewsItemDao dao) {
            mAsyncTaskNewsDao = dao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mAsyncTaskNewsDao.loadAllNewsItems();
            return null;
        }
    }

    public void sync(Context context) {
        URL url = NetworkUtils.buildURL();
        new syncDataAsyncTask(db).execute(url);
    }

    protected static class syncDataAsyncTask extends AsyncTask<URL, Void, String> {
        private final NewsItemDao mDao;
        private List<NewsItem> mNewsItems = new ArrayList<NewsItem>();

        syncDataAsyncTask(NewsItemRoomDatabase db) {
            mDao = db.newsDao();
        }

        @Override
        protected String doInBackground(final URL... url) {
            mDao.clearAll();
            String results = "";

            try {
                results = NetworkUtils.getResponseFromHttpUrl(url[0]);
            } catch(IOException e) {
                e.printStackTrace();
            }

            mNewsItems = JsonUtils.parseNews(results);
            mDao.insert(mNewsItems);

            return results;
        }
    }

}
