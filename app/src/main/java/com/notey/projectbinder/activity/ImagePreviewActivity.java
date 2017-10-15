package com.notey.projectbinder.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.evernote.edam.type.LinkedNotebook;
import com.evernote.edam.type.Notebook;
import com.notey.projectbinder.R;
import com.notey.projectbinder.task.CreateNewNoteTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;

public class ImagePreviewActivity extends AppCompatActivity {

    private Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);

        if (getIntent().getByteArrayExtra("byteArray").length > 0) {
            ImageView preview = (ImageView) findViewById(R.id.imagePreview);
            byte[] byteArray = getIntent().getByteArrayExtra("byteArray");
            bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            preview.setImageBitmap(bmp);
        }

        final ImageButton button = (ImageButton) findViewById(R.id.btn_check);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println(bmp);
                storeImage(bmp);
            }
        });
    }
    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            System.out.println(
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            String date = dateFormat.format(cal);
            CreateNewNoteTask.ImageData i = new CreateNewNoteTask.ImageData(pictureFile.getPath(), date, "image/png");
            System.out.println(i.getPath());
            new CreateNewNoteTask(date, "", i, null, null).start(ImagePreviewActivity.this);
            presentSuccessAlert();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error accessing file: " + e.getMessage());
        }
    }
    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        File mediaFile;
        String mImageName="MI_.jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    public void presentSuccessAlert() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(ImagePreviewActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(ImagePreviewActivity.this);
        }
        builder.setTitle("All Set!")
                .setMessage("Your note was uploaded successfully")
                .setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(ImagePreviewActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                })
                .setNegativeButton(R.string.take_another, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(0)
                .show();
    }
}
