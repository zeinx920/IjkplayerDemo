package com.tracyis.ijkplayerdemo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tracyis.ijkplayerdemo.R;
import com.tracyis.ijkplayerdemo.entity.YinkeLives;

import java.util.List;

public class LivesAdapter extends BaseAdapter {

    private List<YinkeLives> mLives;
    private Context mContext;
    private LayoutInflater mInflater;

    public LivesAdapter(Context context, List<YinkeLives> lives) {
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
            convertView = mInflater.inflate(R.layout.adapter_lives, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        YinkeLives yinkeLives = mLives.get(position);
        holder.tvNameAndCity.setText("来自" + yinkeLives.getCity() + "的" + (TextUtils.isEmpty(yinkeLives.getName()) ? " 未起名 " : yinkeLives.getName()) + "距离您" + yinkeLives.getDistance());
        Glide.with(mContext)
                .load(yinkeLives.getCreator().getPortrait())
                .centerCrop()
                .crossFade()
                .into(holder.ivPortrait);

        return convertView;
    }

    private static class ViewHolder {
        TextView tvNameAndCity;
        ImageView ivPortrait;

        public ViewHolder(View convertView) {
            tvNameAndCity = (TextView) convertView.findViewById(R.id.tvNameAndCity);
            ivPortrait = (ImageView) convertView.findViewById(R.id.ivPortrait);
        }
    }

}
