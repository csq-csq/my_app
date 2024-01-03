package com.example.my_app.Bean;

public class Video_bean {
    private String video_src;
    private String video_img;
    private String video_title;
    private String username;

    public Video_bean(String video_src, String video_img, String video_title, String username){
        this.video_src = video_src;
        this.video_img = video_img;
        this.video_title = video_title;
        this.username = username;
    }

    public String getVideo_src(){
        return video_src;
    }

    public String getVideo_img(){
        return video_img;
    }

    public String getVideo_title(){
        return video_title;
    }

    public String getUsername(){
        return username;
    }
    @Override
    public String toString() {
        return "Video_bean{" +
                "video_src='" + video_src + '\'' +
                ", video_title='" + video_title + '\'' +
                '}';
    }

}
