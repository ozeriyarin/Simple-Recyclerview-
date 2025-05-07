package com.example.myapplicationrv.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplicationrv.R;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView image  = findViewById(R.id.detailImage);
        TextView name   = findViewById(R.id.detailName);
        TextView actor  = findViewById(R.id.detailActor);

        Intent i = getIntent(); // Get the intent that started this activity (from MainActivity) including the extras (name, actor, imageUrl)
        name.setText(i.getStringExtra("name"));
        actor.setText(i.getStringExtra("actor"));

        String url = i.getStringExtra("imageUrl");
        if (url != null) {
            Glide.with(this).load(url).into(image);
        }
    }
}

