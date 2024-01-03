package com.example.my_app.login;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务类，负责处理用户相关的操作
 */
public class UserService {
    private DatabaseHelper dbHelper;

    public UserService(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * 用户登录
     */
    public boolean login(String username, String password) {
        try (SQLiteDatabase sdb = dbHelper.getReadableDatabase()) {
            String sql = "select * from user where username=? and password=?";
            try (Cursor cursor = sdb.rawQuery(sql, new String[]{username, password})) {
                return cursor.moveToFirst();
            }
        }
    }

    /**
     * 用户注册
     */
    public boolean register(User user) {
        try (SQLiteDatabase sdb = dbHelper.getReadableDatabase()) {
            String sql = "insert into user(username,password,age,phone,sex,photo) values(?,?,?,?,?,?)";
            Object obj[] = {user.getUsername(), user.getPassword(), user.getAge(), user.getPhone(), user.getSex(), user.getPhoto()};
            sdb.execSQL(sql, obj);
            return true;
        }
    }

    /**
     * 用户名查重
     */
    public boolean check(String username) {
        try (SQLiteDatabase sdb = dbHelper.getReadableDatabase()) {
            String sql = "select * from user where username=?";
            try (Cursor cursor = sdb.rawQuery(sql, new String[]{username})) {
                return cursor.moveToFirst();
            }
        }
    }

    /**
     * 查询性别
     */
    public String gender() {
        try (SQLiteDatabase sdb = dbHelper.getReadableDatabase()) {
            String sql = "select sex from user where username=?";
            try (Cursor cursor = sdb.rawQuery(sql, new String[]{LoginActivity.name})) {
                cursor.moveToFirst();
                return cursor.getString(0);
            }
        }
    }

    /**
     * 查询年龄
     */
    public String age() {
        try (SQLiteDatabase sdb = dbHelper.getReadableDatabase()) {
            String sql = "select age from user where username=?";
            try (Cursor cursor = sdb.rawQuery(sql, new String[]{LoginActivity.name})) {
                cursor.moveToFirst();
                return cursor.getString(0);
            }
        }
    }

    /**
     * 获取用户信息
     */
    public Map get_userinfo(String username) {
        Map<String, String> info = new HashMap<>();
        try (SQLiteDatabase sdb = dbHelper.getReadableDatabase()) {
            String sql = "select * from user where username=?";
            try (Cursor cursor = sdb.rawQuery(sql, new String[]{username})) {
                cursor.moveToFirst();
                info.put("username", username);
                info.put("age", Integer.toString(cursor.getInt(cursor.getColumnIndexOrThrow("age"))));
                info.put("sex", cursor.getString(cursor.getColumnIndexOrThrow("sex")));
                info.put("phone", cursor.getString(cursor.getColumnIndexOrThrow("phone")));
                info.put("photo", cursor.getString(cursor.getColumnIndexOrThrow("photo")));
            }
        }
        return info;
    }
/*
    *//**
     * 修改头像
     *//*
    public void update_photo(String username, String value) {
        try (SQLiteDatabase sdb = dbHelper.getReadableDatabase()) {
            String sql = "update  user  set photo = ? where username = ?";
            sdb.execSQL(sql,new String[]{value,username});
        }
    }*/
}
