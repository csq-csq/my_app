package com.example.my_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * Loading类用于显示一个加载页面，页面上有一个ImageView显示旋转动画，
 * 动画结束后，页面会跳转到WeatherActivity
 */
public class Loading extends AppCompatActivity {

    private static final int ROTATION_DURATION = 500; // 旋转动画的时间
    private static final int DELAY_DURATION = 500; // 延迟跳转的时间

    private ImageView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // 找到ImageView并启动旋转动画
        loading = findViewById(R.id.loading_img);
        startRotateAnimation();

        // 使用Handler在指定的时间后发送一个消息，以便启动WeatherActivity
        handler.sendEmptyMessageDelayed(0, DELAY_DURATION);
    }

    // 旋转动画
    private void startRotateAnimation() {
        RotateAnimation rotateAnimation = new RotateAnimation(
                0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(ROTATION_DURATION);
        loading.startAnimation(rotateAnimation);
    }

    // Handler用于在指定的时间后启动WeatherActivity
    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Intent intent = new Intent(Loading.this, WeatherActivity.class);
            startActivity(intent);
            finish();  // 结束当前Activity
            return true;
        }
    });
}
