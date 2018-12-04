package com.example.rkjc.news_app_2;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.JobParameters;

public class NewsJob extends JobService {

    static AsyncTask mBackgroundTask;

    private static final String TAG = "NewsJob";

    @Override
    public boolean onStartJob(final JobParameters job) {
        mBackgroundTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                Toast.makeText(NewsJob.this, "News updated!", Toast.LENGTH_SHORT).show();
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object[] params) {
                Log.d(TAG, "doInBackground: Notification sync");
                RefreshTask.executeTask(NewsJob.this, RefreshTask.ACTION_NOTIFICATION);
                return null;
            }

            @Override
            protected  void onPostExecute(Object o) {
                jobFinished(job, false);
                super.onPostExecute(o);
            }
        };

        mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        if(mBackgroundTask != null) mBackgroundTask.cancel(false);

        return true;
    }
}
