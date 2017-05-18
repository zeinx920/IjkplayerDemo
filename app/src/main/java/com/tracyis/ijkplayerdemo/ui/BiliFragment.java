package com.tracyis.ijkplayerdemo.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.tracyis.ijkplayerdemo.Constant;
import com.tracyis.ijkplayerdemo.R;
import com.tracyis.ijkplayerdemo.VideoViewActivity;
import com.tracyis.ijkplayerdemo.adapter.BiliLivesAdapter;
import com.tracyis.ijkplayerdemo.entity.BiliLives;
import com.tracyis.ijkplayerdemo.http.HttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trasys on 2017/5/13.
 */
public class BiliFragment extends Fragment {
    private static final String TAG = "BiliFragment";
    private SpinKitView mSkv;
    private GridView mGvLivingList;
    private BiliLivesAdapter mAdapter;
    private List<BiliLives.DataBean> mBiliLivesList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_bili, null);


        mGvLivingList = (GridView) view.findViewById(R.id.gvBiliLivingList);
        mSkv = (SpinKitView) view.findViewById(R.id.spin_kit);

        mBiliLivesList = new ArrayList<>();
        mAdapter = new BiliLivesAdapter(getContext(), mBiliLivesList);
        mGvLivingList.setAdapter(mAdapter);


        mGvLivingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), VideoViewActivity.class);
                intent.putExtra("stream_addr", mBiliLivesList.get(position).playurl);
                startActivity(intent);
            }
        });
        new RequestLivingTask().execute(Constant.BILIBILI_URL);

        return view;

    }

    private class RequestLivingTask extends AsyncTask<String, Void, List<BiliLives.DataBean>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSkv.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<BiliLives.DataBean> doInBackground(String... params) {
            SystemClock.sleep(1000);

            List<BiliLives.DataBean> lives = new ArrayList<>();
            String url = params[0];
            try {
                String liveurl = HttpUtils.getInstance().get(url);
                Gson gson = new Gson();
                BiliLives biliLives = gson.fromJson(liveurl, BiliLives.class);
                lives = biliLives.data;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return lives;
        }

        @Override
        protected void onPostExecute(List<BiliLives.DataBean> lives) {

            mBiliLivesList.addAll(lives);
//            mAdapter.notifyDataSetChanged();
            mGvLivingList.setAdapter(mAdapter);
            Log.d(TAG, "onPostExecute: "+lives.size() + " mAdapter "+mAdapter.getCount());
            mSkv.setVisibility(View.GONE);

        }
    }
}
