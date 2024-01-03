package com.example.my_app.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my_app.MainActivity;
import com.example.my_app.R;

/**
 * 用户登录界面
 */
public class LoginActivity extends AppCompatActivity {

    // UI elements
    private EditText username;
    private EditText password;
    private Button login;
    private Button register;

    public static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        findViews();
    }

    /**
     * Initialize views
     */
    private void findViews() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        // Set click listener for login button
        login.setOnClickListener(v -> processLogin());

        // Set click listener for register button
        register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Process user login
     */
    private void processLogin() {
        name = username.getText().toString();
        String pass = password.getText().toString();

        Log.i("TAG", name + "_" + pass);

        UserService uService = new UserService(LoginActivity.this);
        boolean flag = uService.login(name, pass);

        if (flag) {
            Log.i("TAG", "登录成功");
            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
            MainActivity.username = name;
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("flag", 3);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Log.i("TAG", "登录失败");
            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
        }
    }
}
