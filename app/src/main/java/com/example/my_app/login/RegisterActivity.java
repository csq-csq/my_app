package com.example.my_app.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my_app.R;

/**
 * 用户注册界面
 */
public class RegisterActivity extends AppCompatActivity {

    // UI elements
    private EditText username;
    private EditText password;
    private EditText age;
    private EditText phone;
    private RadioGroup sex;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Initialize views
        findViews();

        // Set click listener for register button
        register.setOnClickListener(v -> processRegistration());
    }

    /**
     * Initialize views
     */
    private void findViews() {
        username = findViewById(R.id.usernameRegister);
        password = findViewById(R.id.passwordRegister);
        age = findViewById(R.id.ageRegister);
        phone = findViewById(R.id.phoneRegister);
        sex = findViewById(R.id.sexRegister);
        register = findViewById(R.id.Register);
    }

    /**
     * Process user registration
     */
    private void processRegistration() {
        String name = username.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String ageStr = age.getText().toString().trim();
        String phoneNum = phone.getText().toString().trim();
        String sexStr = ((RadioButton)findViewById(sex.getCheckedRadioButtonId())).getText().toString();

        Log.i("TAG",name+"_"+pass+"_"+ageStr+"_"+phoneNum+"_"+sexStr);

        UserService uService = new UserService(RegisterActivity.this);
        boolean isUserExist = uService.check(name);
        if (isUserExist) {
            Toast.makeText(RegisterActivity.this, "用户名已存在，请更换用户名后再注册！", Toast.LENGTH_LONG).show();
        } else {
            User user = new User();
            user.setUsername(name);
            user.setPassword(pass);
            user.setAge(Integer.parseInt(ageStr));
            user.setPhone(phoneNum);
            user.setSex(sexStr);
            uService.register(user);
            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}

