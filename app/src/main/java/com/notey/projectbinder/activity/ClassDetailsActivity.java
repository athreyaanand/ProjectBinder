package com.notey.projectbinder.activity;

import android.content.Context;
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

    TextView toolbarTitle;
    Toolbar activityToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);
        String title = getIntent().getStringExtra("Title");

        toolbarTitle = (TextView) findViewById(R.id.class_toolbar_title);
        toolbarTitle.setText(title);

        activityToolbar = (Toolbar) findViewById(R.id.class_toolbar);
        activityToolbar.setTitle("");
        activityToolbar.setBackground(getResources().getDrawable(R.drawable.class_history_background));
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
    private void setupActionBar() {
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View v = inflator.inflate(R.layout.toolbar_title, null);
        //ab.setCustomView(v);
    }
}
