package com.example.zhaoqiang.exam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by 轩韩子 on 2017/4/24.
 * at 8:40
 */
public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private TextView text;
    private String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
       text = (TextView) findViewById(R.id.text);
        str = getIntent().getStringExtra("name");
        if (TextUtils.isEmpty(str)){
            str="北京";
        }
        //应答监听
        Response.Listener<String> response = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("onResponse", response);
                getJson(response);

            }
        };
        //错误监听
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.getMessage(), error);
            }
        };

        String uri="https://free-api.heweather.com/v5/weather?city="+str+"&key=2a265bed2d2a4a49b43bae49be2e3fe1";
        StringRequest stringRequest = new StringRequest(
                uri
                , response
                , errorListener);

        requestQueue.add(stringRequest);
    }
   /*
   得到json并且解析
    */
    private void getJson(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather5");
            String weather = jsonArray.getJSONObject(0).toString();
            Gson gson = new Gson();
            JavaBean.HeWeather5Bean HeWeather5Bean = gson.fromJson(weather, JavaBean.HeWeather5Bean.class);

            text.setText(HeWeather5Bean.toString());
            Log.e("xxx", HeWeather5Bean.toString());

            getSupportActionBar().setTitle(str);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
