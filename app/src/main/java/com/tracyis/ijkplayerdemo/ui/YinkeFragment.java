package com.tracyis.ijkplayerdemo.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.tracyis.ijkplayerdemo.Constant;
import com.tracyis.ijkplayerdemo.R;
import com.tracyis.ijkplayerdemo.VideoViewActivity;
import com.tracyis.ijkplayerdemo.adapter.LivesAdapter;
import com.tracyis.ijkplayerdemo.entity.YinkeLives;
import com.tracyis.ijkplayerdemo.http.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trasys on 2017/5/13.
 */
public class YinkeFragment extends Fragment {
    private static final String TAG = "YinkeFragment";
    private GridView mGvLivingList;
    private SpinKitView mSkv;
    private LivesAdapter mAdapter;
    private List<YinkeLives> mYinkeLivesList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_yinke, null);


        mGvLivingList = (GridView) view.findViewById(R.id.gvLivingList);
        mSkv = (SpinKitView) view.findViewById(R.id.spin_kit);

        mYinkeLivesList = new ArrayList<>();
        mAdapter = new LivesAdapter(getContext(), mYinkeLivesList);
        mGvLivingList.setAdapter(mAdapter);


        mGvLivingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), VideoViewActivity.class);
                intent.putExtra("stream_addr", mYinkeLivesList.get(position).getStream_addr());
                startActivity(intent);
            }
        });

        new RequestLivingTask().execute(Constant.YINKE_URL);
        return view;

    }

    private class RequestLivingTask extends AsyncTask<String, Void, List<YinkeLives>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSkv.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<YinkeLives> doInBackground(String... params) {
            SystemClock.sleep(1000);

            String url = params[0];
            List<YinkeLives> liveList = new ArrayList<>();
            try {
                String livingList = HttpUtils.getInstance().get(url);
                JSONObject livingObj = new JSONObject(livingList);
                JSONArray livesArr = livingObj.optJSONArray("lives");
                for (int i = 0; i < livesArr.length(); i++) {
                    JSONObject live = livesArr.optJSONObject(i);
                    YinkeLives yinkeLives = new YinkeLives(live);
                    liveList.add(yinkeLives);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return liveList;
        }

        @Override
        protected void onPostExecute(List<YinkeLives> lives) {
            super.onPostExecute(lives);
            mYinkeLivesList.addAll(lives);
            mAdapter.notifyDataSetChanged();
            mSkv.setVisibility(View.GONE);

        }
    }

}
