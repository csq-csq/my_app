package com.example.my_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.my_app.Adapter.WeatherAdapter;
import com.example.my_app.Bean.Weather_bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Calendar;
public class WeatherActivity extends AppCompatActivity {

    private List<Weather_bean> weatherList = new ArrayList<>();//存储实例化的数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);


        //返回
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(WeatherActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addWeather();//添加weather信息
        RecyclerView recyclerView = findViewById(R.id.hour_wea);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);//添加布局管理器
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);  //设置为横向水平滚动
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        WeatherAdapter adapter = new WeatherAdapter(weatherList);
        recyclerView.setAdapter(adapter);

    }
    //随机生成24小时天气情况的方法
    private void addWeather() {
        // 获取当前的小时数
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        Weather_bean now = new Weather_bean(currentHour + "时", R.drawable.clear);
        weatherList.add(now);

        int wea[] = {R.drawable.clear, R.drawable.cloudy, R.drawable.rainy};
        Random random = new Random(); // 创建一个Random对象

        for (int i = 1; i < 24; i++) {
            int hour = (currentHour + i) % 24; // 计算下一小时，如果超过24小时，会自动减去24
            Weather_bean last = new Weather_bean(hour + "时", wea[random.nextInt(3)]); // 使用随机数生成器
            weatherList.add(last);
        }
    }

}