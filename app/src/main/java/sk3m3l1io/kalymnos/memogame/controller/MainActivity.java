package sk3m3l1io.kalymnos.memogame.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import sk3m3l1io.kalymnos.memogame.R;

public class MainActivity extends AppCompatActivity {
    private Button play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = findViewById(R.id.play);
        play.setOnClickListener(view -> {
            Intent i = new Intent();
        });
    }
}
