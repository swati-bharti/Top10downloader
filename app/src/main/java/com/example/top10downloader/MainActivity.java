package com.example.top10downloader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    FeedAdapter adapter;
    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

       getItems("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=25/xml");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        String url;
        switch (id) {
            case R.id.menu_songs:
                Toast.makeText(this, "Songs Tapped", Toast.LENGTH_SHORT).show();
                url = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=25/xml";
                break;
            case R.id.menu_free_apps:
                Toast.makeText(this, "Free Apps Tapped", Toast.LENGTH_SHORT).show();
                url = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml";
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        getItems(url);
        return true;
    }


    public void getItems(String url){
        DownloaderTask task = new DownloaderTask();

        task.execute(url);

    }

    //Downloader Class
    private class DownloaderTask extends AsyncTask<String, Void, String> {
        private static final String TAG = "DownloaderTask";
        //private Context context;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String rssFeed = downloadData(strings[0]);
            if (rssFeed == null) {
                Log.e(TAG, "doInBackground: Download Failed");
            }
            return rssFeed;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: " + s);
            ParseApp app = new ParseApp();
            app.parse(s);
            Log.i(TAG, "onPostExecute: list size"+app.getItems().size());

            adapter = new FeedAdapter(MainActivity.this, app.getItems());

            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }

        String downloadData(String mainUrl) {
            StringBuilder builderRes = new StringBuilder();
            try {
                URL url = new URL(mainUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int respCode = connection.getResponseCode();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);

                int charsRead;
                char[] inputBuffer = new char[2000];
                while (true) {
                    charsRead = reader.read(inputBuffer);
                    if (charsRead < 0) {
                        break;
                    } else {
                        builderRes.append(String.copyValueOf(inputBuffer, 0, charsRead));

                    }
                }
                reader.close();
                return builderRes.toString();

            } catch (Exception e) {
                Log.e(TAG, "downloadData: " + e.getMessage());
            }
            return null;

        }

    }
}
