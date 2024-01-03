package com.example.my_app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class AddNews extends AppCompatActivity {

    private ImageView img1;
    private ImageView img2;
    private EditText input_title;
    private EditText input_abstract;
    private EditText input_article;
   // private EditText input_subtitle;
    private RadioButton R1;
    private RadioButton R2;
    private String title;
    private String abstr;
    private String article;
    private String subtitle;
    public String img_path;
    private String tip;
    public int sig;
    public int sig2;
    RadioGroup radioGroup;

    private NewsDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);
        dbHelper = new NewsDBHelper(this);
        setupViews();
        setupListeners();
    }

    private void setupViews() {
        img1 = findViewById(R.id.news_pic1);
       // img2 = findViewById(R.id.news_pic2);
        radioGroup = findViewById(R.id.RadioGroup);
        R1 = findViewById(R.id.radioButton1);
        R2 = findViewById(R.id.radioButton2);
        input_title = findViewById(R.id.input_title);
        input_abstract = findViewById(R.id.input_abstract);
        input_article = findViewById(R.id.input_article);
  //      input_subtitle = findViewById(R.id.input_subtitle);
    }

    private void setupListeners() {
        setupImageSelectionListeners();
        setupBackButtonListener();
        setupRadioGroupListener();
        setupSubmitButtonListener();
    }

    private void setupImageSelectionListeners() {
        View.OnClickListener imageClickListener = v -> {
            if (ContextCompat.checkSelfPermission(AddNews.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AddNews.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
            sig = v == img1 ? 1 : 2;
            openAlbum();
        };

        img1.setOnClickListener(imageClickListener);
    //    img2.setOnClickListener(imageClickListener);
    }

    private void setupBackButtonListener() {
        findViewById(R.id.back_button).setOnClickListener(v -> {
            finish();
        });
    }


    private void setupRadioGroupListener() {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButton1)
                sig2 = 1;
            else if (checkedId == R.id.radioButton2)
                sig2 = 2;
        });
    }

    // 修改 setupSubmitButtonListener 方法来插入新的新闻
    private void setupSubmitButtonListener() {
        findViewById(R.id.submit_news).setOnClickListener(v -> {
            title = input_title.getText().toString();
            abstr = input_abstract.getText().toString();
            article = input_article.getText().toString();
            tip = sig2 == 1 ? "置顶" : "热点";

            if (title.trim().isEmpty()  || article.trim().isEmpty() ||abstr.trim().isEmpty()) {
                Toast.makeText(AddNews.this, "请填写完整后再提交", Toast.LENGTH_SHORT).show();
                return;
            }

            // 插入新的新闻
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NewsDBHelper.COLUMN_TITLE, title);
            values.put(NewsDBHelper.COLUMN_ABSTRACT, abstr);
            values.put(NewsDBHelper.COLUMN_ARTICLE, article);
            values.put(NewsDBHelper.COLUMN_IMAGE_PATH, img_path);
            values.put(NewsDBHelper.COLUMN_TIP, tip);
            long id = db.insert(NewsDBHelper.TABLE_NAME, null, values);

            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            bundle.putString("subtitle", abstr);
            bundle.putString("content", article);
            bundle.putString("pic", img_path);
            bundle.putString("tip", tip);
            bundle.putInt("flag", 1);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);

            finish();
        });
    }


    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Build.VERSION.SDK_INT >= 19) {
            handleImageOnKitKat(data);
        } else {
            handleImageBeforeKitKat(data);
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        img_path = null;
        try {
            Uri uri = data.getData();
            if (DocumentsContract.isDocumentUri(this, uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                    String id = docId.split(":")[1];
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    img_path = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                    img_path = getImagePath(contentUri, null);
                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                img_path = getImagePath(uri, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                img_path = uri.getPath();
            }
            displayImage(img_path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        img_path = getImagePath(uri, null);
        displayImage(img_path);
    }

    @SuppressLint("Range")
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            if (sig == 1)
                img1.setImageBitmap(bitmap);
            else
                img2.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
}
