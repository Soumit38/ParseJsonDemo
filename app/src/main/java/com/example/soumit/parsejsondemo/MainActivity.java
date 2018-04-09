package com.example.soumit.parsejsondemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private final String URL_TO_HIT =
            "https://jsonparsingdemo-cec5b.firebaseapp.com/jsonData/moviesData.txt";
    private TextView   tvData;
    private ListView lvMovies;
    private ProgressDialog dialog;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        initImageLoader();
        showDialog();

        lvMovies = (ListView) findViewById(R.id.lvMovies);

        new JsonTask().execute(URL_TO_HIT);


    }

    private void initImageLoader(){
        Log.d(TAG, "initImageLoader: setup");

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(false) // default
                .cacheOnDisk(false)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .build();

        ImageLoader.getInstance().init(config);
    }

    private void showDialog(){
        Log.d(TAG, "showDialog: showing...");

        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait....");
    }

    public class JsonTask extends AsyncTask<String , Void, List<MovieModel> >{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<MovieModel> doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ( ((line = reader.readLine()) != null)){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                Log.d(TAG, "doInBackground: finalJson \n" + finalJson);

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("movies");

                List<MovieModel> movieModelList = new ArrayList<>();

                Gson gson = new Gson();
                for(int i=0; i<parentArray.length(); i++){
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    MovieModel movieModel = gson.fromJson(finalObject.toString(), MovieModel.class);
                    Log.d(TAG, "doInBackground:  movieModel --> " + movieModel);
                    movieModelList.add(movieModel);
                }

                return movieModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground: MalformedURLException " + e.getMessage() );
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground: IOException " + e.getMessage() );
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground: JSONException  " + e.getMessage() );
            }finally {
                if(connection != null){
                    connection.disconnect();
                }
                try {
                    if(reader != null){
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(final List<MovieModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();

            if(result != null){
                //set value to adapter
                if(result != null) {
                    MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.row, result);
                    lvMovies.setAdapter(adapter);
                    lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {  // list item click opens a new detailed activity
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.d(TAG, "onItemClick: item clicked : " + result.get(position));
//                            MovieModel movieModel = result.get(position); // getting the model
//                            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
//                            intent.putExtra("movieModel", new Gson().toJson(movieModel)); // converting model json into string type and sending it via intent
//                            startActivity(intent);
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.action_refresh){
            new JsonTask().execute(URL_TO_HIT);
        }

        return true;
    }
}
































