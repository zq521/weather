package com.example.zhaoqiang.exam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 轩韩子 on 2017/4/24.
 * at 09:00
 */

public class DrwaAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> list = new ArrayList<>();

    public DrwaAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false);
            holder.city_name = (TextView) convertView.findViewById(R.id.city_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.city_name.setText(list.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView city_name;
    }

}
