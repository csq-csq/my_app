package com.example.my_app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.my_app.Adapter.NewsAdapter;
import com.example.my_app.AddNews;
import com.example.my_app.Bean.News_bean;
import com.example.my_app.JsonParse;
import com.example.my_app.Loading;
import com.example.my_app.MainActivity;
import com.example.my_app.R;
import com.example.my_app.SearchActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag_home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LinearLayout weather;
    private ImageButton addnews;
    private List<News_bean> newsList = new ArrayList<>();//定义新闻信息列表


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Frag_home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_home.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag_home newInstance(String param1, String param2) {
        Frag_home fragment = new Frag_home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        weather = (LinearLayout) getActivity().findViewById(R.id.weather);
        //天气动画
        AlphaAnimation animaAlpha = new AlphaAnimation(0, 1);
        animaAlpha.setDuration(3000);    //持续时间
        animaAlpha.setFillAfter(true);    //动画停留最后一帧
        weather.startAnimation(animaAlpha);

        //跳转天气界面
        weather.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), Loading.class);

                startActivity(intent);
            }
        });


    }
}