package com.example.kdblue.ItechVcet;

import android.app.DownloadManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by kulde on 3/29/2017.
 */

public class CollegeNewsLetter extends Fragment {

    boolean mDualPane;
    int mCurCheckPosition = 0;
    private ImageView imageView;
    private TextView downloadMagazine;

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RecyclerViewCollegeNewsLetter recyclerViewCollegeNewsLetter;
    boolean refreshing = false;
    SweetAlertDialog pDialog;
    boolean refresh_status = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_college_news_letter, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swiperefresh_for_college_news);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView_college_news);
        imageView = (ImageView)root.findViewById(R.id.imageView);
        downloadMagazine=(TextView)root.findViewById(R.id.download_magazine);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        downloadMagazine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long downloadReference;
                DownloadManager downloadManager = (DownloadManager)getActivity().getSystemService(DOWNLOAD_SERVICE);
                Uri Download_Uri = Uri.parse("http://euphoriant-confiden.000webhostapp.com/itech.pdf");
                DownloadManager.Request request = new DownloadManager.Request(Download_Uri);

                //Restrict the types of networks over which this download may proceed.
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                //Set whether this download may proceed over a roaming connection.
                request.setAllowedOverRoaming(true);
                //Set the title of this download, to be displayed in notifications (if enabled).
                request.setTitle("Itech Magazine 2017");
                //Set a description of this download, to be displayed in notifications (if enabled)
                request.setDescription("Itech Magazine 2017");
                //Set the local destination for the downloaded file to a path within the application's external files directory
                request.setDestinationInExternalFilesDir(getActivity(), Environment.DIRECTORY_DOWNLOADS,"Itech Magazine 2017");

                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                //Enqueue a new download and same the referenceId
                downloadReference = downloadManager.enqueue(request);


            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RecyclerViewCollegeNewsLetter.topicses.clear();
                new JsonTask().execute();
                swipeRefreshLayout.setRefreshing(true);
                refreshing = true;
                swipeRefreshLayout.requestDisallowInterceptTouchEvent(true);

            }
        });

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (refreshing)
                    return true;
                else {
                    return false;
                }


            }
        });



        new JsonTask().execute();



    }


    class JsonTask extends AsyncTask<Void, Void, String> {
        HttpURLConnection connection = null;
        boolean flag = true;

        @Override
        protected void onPreExecute() {
            if (refresh_status == false) {
                pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#00BCD4"));
                pDialog.setTitleText("Fetching Data....");
                pDialog.setCancelable(false);
                pDialog.show();

            }
        }

        @Override
        protected String doInBackground(Void... params) {
            Log.d("MainActivity", "doInBackground");
            String str = "";

            //URL url=new URL("http://javalovers.net16.net/showdata.php");
            URL url = null;// this api link
            try {
                url = new URL("http://euphoriant-confiden.000webhostapp.com/itechShowData.php");
                Log.d("cccc", "url" + url.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                connection = (HttpURLConnection) url.openConnection();
                Log.d("cccc", "connection" + connection.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                connection.setRequestMethod("POST");
                Log.d("cccc", "connection set request" + connection.toString());
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            try {
                connection.connect();
                Log.d("cccc", "connect" + connection.toString());
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("cccc", "connect exception" + connection.toString());
                flag = false;
            }


            try {
                if (connection.getResponseCode() == 200) {
                    //Toast.makeText(getBaseContext(),"Everything is right",Toast.LENGTH_SHORT).show();
                    InputStream stream = connection.getInputStream(); //here getting response
                    BufferedReader br = new BufferedReader(new InputStreamReader(stream));
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        // buffer.append(line);
                        str = str + line;
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return str;
        }

        @Override
        protected void onPostExecute(String result) {

            if (!flag) {
                Toast.makeText(getContext(), "Check Your Internet Connection",
                        Toast.LENGTH_SHORT).show();
            }

            String json_string = result;
            Log.d("ssss_news", json_string);
            jsonMethod(json_string);
            refreshing = false;
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    public void jsonMethod(String json) {
        try {

            Topics topics;
            ArrayList<Topics> topicsArrayList = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("LOGIN");
            JSONObject jsonObject1;
            String id = "", article = "", content = "", givenby = "",imageUrl="";

            for (int i = 0; i < jsonArray.length(); i++) {
                topics = new Topics();
                jsonObject1 = jsonArray.getJSONObject(i);
                id = jsonObject1.getString("timestamp");
                topics.setId(id);
                Log.d("ssss_news", "" + id);
                article = jsonObject1.getString("article");
                topics.setArticle(article);
                Log.d("ssss_aricle", "" + article);
                content = jsonObject1.getString("content");
                topics.setContent(content);
                givenby = jsonObject1.getString("givenby");
                topics.setGivenby(givenby);
                imageUrl = jsonObject1.getString("image_url");
                topics.setImageUrl(imageUrl);

                topicsArrayList.add(topics);
            }
            recyclerViewCollegeNewsLetter = new RecyclerViewCollegeNewsLetter(getActivity(), topicsArrayList);
            recyclerView.setAdapter(recyclerViewCollegeNewsLetter);
            if (refresh_status == false) {
                pDialog.dismiss();
                refresh_status=true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

    }
}
