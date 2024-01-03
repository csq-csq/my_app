package com.example.my_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;


import com.example.my_app.Adapter.NewsAdapter;
import com.example.my_app.Adapter.VideoAdapter;
import com.example.my_app.Bean.News_bean;
import com.example.my_app.Bean.Video_bean;
import com.example.my_app.Fragment.Frag_home;
import com.example.my_app.Fragment.Frag_user;
import com.example.my_app.Fragment.Frag_video;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView homeButton;
    private ImageView videoButton;
    private ImageView userButton;
    private Bundle bundle;
    private String addNews;
    public static String username;
    public int currentFragment;
    private NewsAdapter newsAdapter;
    private List<News_bean> newsList;

    private NewsDBHelper dbHelper;

    static public String newsData = "[" +
            "{" +
            "\"title\":\"外星人入侵地球，地球领导者请求和平谈判\"," +
            "\"subtitle\":\"银河日报\"," +
            "\"tip\":\"置顶\"," +
            "\"content\":\"在一次震惊世界的事件中，外星人突然入侵地球。地球上的各国领导者已经开始寻求和平谈判的可能性，希望能够避免进一步的冲突。\"," +
            "\"pic\":\"https://img2.baidu.com/it/u=802492684,3025670586&fm=253&fmt=auto&app=138&f=JPEG?w=667&h=500\"" +
            "}," +
            "{" +
            "\"title\":\"科学家发现独角兽真实存在，全球震惊\"," +
            "\"subtitle\":\"神奇生物观察家\"," +
            "\"tip\":\"热点\"," +
            "\"content\":\"在一次前所未有的科学发现中，科学家们证实了独角兽的真实存在。这个消息引发了全球的震惊和热烈讨论。\"," +
            "\"pic\":\"https://img1.baidu.com/it/u=3247430413,76058777&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=750\"" +
            "}," +
            "{" +
            "\"title\":\"巧克力雨降落在比利时，市民欢呼庆祝\"," +
            "\"subtitle\":\"甜蜜新闻网\"," +
            "\"tip\":\"热点\"," +
            "\"content\":\"在一次奇特的天气现象中，比利时的一座城市突然降下了巧克力雨。市民们对这场意想不到的甜蜜降雨表示欢呼和庆祝.\"," +
            "\"pic\":\"https://img2.baidu.com/it/u=2090375588,3401070218&fm=253&fmt=auto&app=138&f=JPEG?w=750&h=500\"" +
            "}," +
            "{" +
            "\"title\":\"海豚开发出新的通信语言，人类科学家正在破译\"," +
            "\"subtitle\":\"海洋生物研究中心\"," +
            "\"tip\":\"热点\"," +
            "\"content\":\"海豚被发现开发出了一种新的通信语言。人类科学家们正在努力破译这种语言，以深入理解海豚的社交行为和情绪表达.\"," +
            "\"pic\":\"https://img1.baidu.com/it/u=1108768059,1969241992&fm=253&fmt=auto&app=138&f=JPEG?w=705&h=500\"" +
            "}," +
            "{" +
            "\"title\":\"年度最受欢迎宠物大赛，懒洋洋的熊猫夺冠\"," +
            "\"subtitle\":\"动物星球新闻\"," +
            "\"tip\":\"热点\"," +
            "\"content\":\"在今年的最受欢迎宠物大赛中，一只懒洋洋的熊猫赢得了冠军。它的可爱举止和亲和力赢得了评委和观众的一致好评.\"," +
            "\"pic\":\"https://img1.baidu.com/it/u=4124795151,1823615635&fm=253&fmt=auto&app=138&f=JPEG?w=656&h=437\"" +
            "}" +
            "]";




    static public String videoData = "[" +
            "{" +
            "\"video_title\":\"疯狂动物城爆笑上映\"," +
            "\"username\":\"lukai\"," +
            "\"video_img\":\"https://img2.baidu.com/it/u=3599238479,2998054692&fm=253&fmt=auto&app=120&f=JPEG?w=1281&h=800\"," +
            "\"video_src\":\"http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4\"" +
            "}," +
            "{" +
            "\"video_title\":\"海鸥飞跃太平洋\"," +
            "\"username\":\"lk\"," +
            "\"video_img\":\"https://pic.birdnet.cn/forum/2012/06/20/9/4813092171291.jpg\"," +
            "\"video_src\":\"http://vjs.zencdn.net/v/oceans.mp4\"" +
            "}," +
            "{" +
            "\"video_title\":\"三体人宣布放弃入侵地球\"," +
            "\"username\":\"lk\"," +
            "\"video_img\":\"https://inews.gtimg.com/newsapp_bt/0/13971415717/641\"," +
            "\"video_src\":\"http://vjs.zencdn.net/v/oceans.mp4\"" +
            "}," +
            "{" +
            "\"video_title\":\"国足勇夺世界杯，高俅绝杀\"," +
            "\"username\":\"alex\"," +
            "\"video_img\":\"https://i1.hdslb.com/bfs/archive/812e62823996b801419ba22a2bb2aa886ca64e45.jpg\"," +
            "\"video_src\":\"http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4\"" +
            "}" +

            "]"
            ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeButton = findViewById(R.id.home);
        videoButton = findViewById(R.id.video);
        userButton = findViewById(R.id.user);

        homeButton.setOnClickListener(this::switchFragment);
        videoButton.setOnClickListener(this::switchFragment);
        userButton.setOnClickListener(this::switchFragment);

        bundle = this.getIntent().getExtras();
        if (bundle != null) {
            int actionType = bundle.getInt("flag");
            switch (actionType) {
                case 1:
                   // handleaddNews();
                    break;
                case 2:
                case 3:
                    ImageView button = actionType == 2 ? videoButton : userButton;
                    switchFragment(button);
                    break;
                default:
                    switchFragment(homeButton);
                    break;
            }
        } else {
            switchFragment(homeButton);
        }
        SearchView searchView = findViewById(R.id.searchView);
        Button searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(v -> {
            String query = searchView.getQuery().toString();
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putExtra("query", query);
            startActivity(intent);
        });
// Find the RecyclerView in the layout
        RecyclerView recyclerView = findViewById(R.id.news_recycler_view);

// Create a LinearLayoutManager
// This will define how the RecyclerView displays its items
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
     //   Log.d("MainActivity","come here1");

 //Parse the newsData string into a list of News_bean objects
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<News_bean>>(){}.getType();
       // Log.d("MainActivity","come here2");
        newsList = gson.fromJson(newsData, listType);
       // Log.d("MainActivity","come here3");
        dbHelper = new NewsDBHelper(this);

// Query all news from the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(NewsDBHelper.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int titleIndex = cursor.getColumnIndex(NewsDBHelper.COLUMN_TITLE);
                int subtitleIndex = cursor.getColumnIndex(NewsDBHelper.COLUMN_ABSTRACT);
                int contentIndex = cursor.getColumnIndex(NewsDBHelper.COLUMN_ARTICLE);
                int picIndex = cursor.getColumnIndex(NewsDBHelper.COLUMN_IMAGE_PATH);
                int tipIndex = cursor.getColumnIndex(NewsDBHelper.COLUMN_TIP);

                if (titleIndex == -1 || subtitleIndex == -1 || contentIndex == -1 || picIndex == -1 || tipIndex == -1) {
                    throw new RuntimeException("Error: Required column not found in database");
                }

                String title = cursor.getString(titleIndex);
                String subtitle = cursor.getString(subtitleIndex);
                String content = cursor.getString(contentIndex);
                String pic = cursor.getString(picIndex);
                String tip = cursor.getString(tipIndex);

                News_bean news = new News_bean(title, subtitle, tip, pic, content);
                newsList.add(news);
            } while (cursor.moveToNext());
        }
        cursor.close();

        newsAdapter = new NewsAdapter(newsList);
       // Log.d("MainActivity","come here4");
        recyclerView.setAdapter(newsAdapter);

    }

    public void handleaddNews(News_bean news) {
        // Add the news to the list
        newsList.add(news);

        // Notify the RecyclerView that the data has changed
        newsAdapter.notifyDataSetChanged();
    }



    public void setBundle(Bundle newBundle) {
        this.bundle = newBundle;
    }


    public void switchFragment(View v) {
        Fragment fragment;
        int newCurrentFragment;
        // 获取搜索框和滚动新闻条
        View searchBox = findViewById(R.id.searchView);
        View newsTicker = findViewById(R.id.news_recycler_view);
        View searchButton=findViewById(R.id.searchButton);
        switch (v.getId()) {
            case R.id.home:
                fragment = new Frag_home();
                newCurrentFragment = 1;
                // 当用户点击主页选项时，显示搜索框和滚动新闻条
                searchBox.setVisibility(View.VISIBLE);
                newsTicker.setVisibility(View.VISIBLE);
                searchButton.setVisibility(View.VISIBLE);
                break;
            case R.id.video:
                fragment = new Frag_video();
                newCurrentFragment = 2;
                // 当用户点击视频选项时，隐藏搜索框和滚动新闻条
                searchBox.setVisibility(View.GONE);
                newsTicker.setVisibility(View.GONE);
                searchButton.setVisibility(View.GONE);
                break;
            case R.id.user:
                fragment = new Frag_user();
                newCurrentFragment = 3;
                // 当用户点击用户选项时，隐藏搜索框和滚动新闻条
                searchBox.setVisibility(View.GONE);
                newsTicker.setVisibility(View.GONE);
                searchButton.setVisibility(View.GONE);
                break;
            default:
                return;
        }



        if (currentFragment != newCurrentFragment) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frag, fragment);
            ft.commit();
            currentFragment = newCurrentFragment;
        }
    }

}



