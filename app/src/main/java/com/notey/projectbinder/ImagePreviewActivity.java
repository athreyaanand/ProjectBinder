package com.notey.projectbinder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ImagePreviewActivity extends AppCompatActivity {

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        if (getIntent().getByteArrayExtra("byteArray").length > 0) {
            ImageView preview = (ImageView) findViewById(R.id.imagePreview);
            byte[] byteArray = getIntent().getByteArrayExtra("byteArray");
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            preview.setImageBitmap(bmp);
        }

        final ImageButton button = (ImageButton) findViewById(R.id.btn_takepicture);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ImagePreviewActivity.this, SandboxActivity.class);
                startActivity(intent);
            }
        });
    }
}
