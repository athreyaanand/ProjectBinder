package com.notey.projectbinder.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.notey.projectbinder.R;
import com.notey.projectbinder.fragment.note.CreateNoteDialogFragment;

import org.w3c.dom.Text;

public class ClassDetailsActivity extends AppCompatActivity {

    TextView toolbarTitle, details;
    Toolbar activityToolbar;
    private String name, startDate, endDate, subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);
        name = getIntent().getStringExtra("Name");
        startDate = getIntent().getStringExtra("StartDate");
        endDate = getIntent().getStringExtra("EndDate");
        subject = getIntent().getStringExtra("Subject");

        toolbarTitle = (TextView) findViewById(R.id.class_toolbar_title);
        toolbarTitle.setText(name);

        details = (TextView) findViewById(R.id.class_info);
        details.setText(subject + " \n\n" + startDate + " - " + endDate);

        activityToolbar = (Toolbar) findViewById(R.id.class_toolbar);
        activityToolbar.setTitle("");
        activityToolbar.setBackground(getHeaderImage());
        if(getHeaderImage() == null)
        activityToolbar.setBackgroundColor(getResources().getColor(R.color.tb_bg));
        setSupportActionBar(activityToolbar);


        final ImageButton button = (ImageButton) findViewById(R.id.close_class);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.class_toolbar_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new CreateNoteDialogFragment().show(getSupportFragmentManager(), CreateNoteDialogFragment.TAG);
            }
        });

    }
    private Drawable getHeaderImage() {
        switch(subject) {
            case "Math":
                return getResources().getDrawable(R.drawable.class_math_background);
            case "English":
                return getResources().getDrawable(R.drawable.class_lit_background);
            case "Science":
                return getResources().getDrawable(R.drawable.class_science_background);
            case "History":
                return getResources().getDrawable(R.drawable.class_history_background);
            case "CS":
                return getResources().getDrawable(R.drawable.class_cs_background);
            default:
                return null;
        }
    }
    private void setupActionBar() {
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View v = inflator.inflate(R.layout.toolbar_title, null);
        //ab.setCustomView(v);
    }
}
