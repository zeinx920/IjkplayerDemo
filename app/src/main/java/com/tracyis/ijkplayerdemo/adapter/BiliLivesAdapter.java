package com.tracyis.ijkplayerdemo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tracyis.ijkplayerdemo.R;
import com.tracyis.ijkplayerdemo.entity.BiliLives;

import java.util.List;

public class BiliLivesAdapter extends BaseAdapter {
    private static final String TAG = "BiliLivesAdapter";

    private List<BiliLives.DataBean> mLives;
    private Context mContext;
    private LayoutInflater mInflater;

    public BiliLivesAdapter(Context context, List<BiliLives.DataBean> lives) {
        mLives = lives;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mLives.size();
    }

    @Override
    public Object getItem(int position) {
        return mLives.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_bililives, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BiliLives.DataBean biliLives = mLives.get(position);
        Log.d(TAG, "getView: "+biliLives.playurl);
        holder.tvNameAndCity.setText("来自" + biliLives.area + "的" + (TextUtils.isEmpty(biliLives.owner.name) ? " 未起名 " : biliLives.owner.name+"正在直播"));
        Glide.with(mContext)
                .load(biliLives.cover.src)
                .centerCrop()
                .crossFade()
                .into(holder.ivPortrait);

        return convertView;
    }

    private static class ViewHolder {
        TextView tvNameAndCity;
        ImageView ivPortrait;

        public ViewHolder(View convertView) {
            tvNameAndCity = (TextView) convertView.findViewById(R.id.tvBiliNameAndCity);
            ivPortrait = (ImageView) convertView.findViewById(R.id.ivBiliPortrait);
        }
    }

}
