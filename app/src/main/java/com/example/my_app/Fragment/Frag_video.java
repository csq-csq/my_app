package com.example.my_app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.my_app.Adapter.VideoAdapter;
import com.example.my_app.Bean.Video_bean;
import com.example.my_app.JsonParse;
import com.example.my_app.MainActivity;
import com.example.my_app.R;
import com.example.my_app.SearchActivity;
import com.google.android.exoplayer2.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag_video#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_video extends Fragment {
    // 参数名，用于创建新的Frag_video实例时传递参数
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // 视频列表，用于保存解析自JSON数据的Video_bean对象
    private List<Video_bean> videoList = new ArrayList<>();

    // 用于保存从newInstance方法接收的参数
    private String mParam1;
    private String mParam2;


    public Frag_video() {
        // Required empty public constructor
    }

    // 用于创建新的Frag_video实例的工厂方法
    public static Frag_video newInstance(String param1, String param2) {
        Frag_video fragment = new Frag_video();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    // 从Bundle中获取参数，并初始化视频列表
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        addVideo();
    }

    // onCreateView方法在onCreate之后被调用，用于创建Fragment的视图
    // 这里将定义在frag_video.xml布局文件中的视图加载到Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_video, container, false);
    }

    // onActivityCreated方法在onCreateView之后被调用，当Activity的onCreate方法返回后
    // 这里设置RecyclerView和搜索按钮
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupRecyclerView();
     //   setupSearchButton();
    }

    // setupRecyclerView方法用于设置RecyclerView，包括设置布局管理器和适配器
    private void setupRecyclerView() {
        RecyclerView recyclerView = getActivity().findViewById(R.id.video_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        VideoAdapter videoAdapter = new VideoAdapter(videoList); // videoList is your list of video data
        recyclerView.setAdapter(videoAdapter);




    }

    // setupSearchButton方法用于设置搜索按钮的点击监听器
    // 当点击搜索按钮时，跳转到SearchActivity
/*    private void setupSearchButton() {

        getActivity().findViewById(R.id.search2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }*/

    // addVideo方法用于解析JSON数据，生成Video_bean对象列表，并赋值给videoList
    private void addVideo() {
        videoList = JsonParse.getInstance().getVideoList(MainActivity.videoData);
        for (Video_bean video : videoList) {
            Log.d("Frag_video", "video_src: " + video.getVideo_src());
        }
    }
}
