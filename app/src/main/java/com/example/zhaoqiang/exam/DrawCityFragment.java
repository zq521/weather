package com.example.zhaoqiang.exam;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;


/**
 * Created by 轩韩子 on 2017/4/24.
 * at 08:42
 */

public class DrawCityFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView draw_listview;
    private RequestQueue requestQueue;
    private ArrayList<String> list = new ArrayList<>();
    DrwaAdapter drwaAdapter;
    private String str;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.frag_draw, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        draw_listview = (ListView) view.findViewById(R.id.draw_listview);
        draw_listview.setOnItemClickListener(this);
        requestQueue = Volley.newRequestQueue(getActivity());

//应答监听
        Response.Listener<String> response = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("onResponse", response);
                getCity(response);

            }
        };
//错误监听
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.getMessage(), error);
            }
        };

        StringRequest stringRequest = new StringRequest(
                "https://cdn.heweather.com/china-city-list.json"
                , response
                , errorListener);

        requestQueue.add(stringRequest);
    }

    /*
    得到城市列表
     */
    private void getCity(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                String weatherContent = jsonArray.getJSONObject(i).toString();
                Gson gson = new Gson();
                User user = gson.fromJson(weatherContent, User.class);
                str = user.getCityZh();
                list.add(str);
                   Log.e("城市", user.getCityZh());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        drwaAdapter = new DrwaAdapter(getActivity(), list);
        draw_listview.setAdapter(drwaAdapter);
        drwaAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("name", list.get(position));
        getActivity().startActivity(intent);

    }
}