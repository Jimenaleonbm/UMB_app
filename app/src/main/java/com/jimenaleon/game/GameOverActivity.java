package com.jimenaleon.game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    TextView endScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        int score = getIntent().getIntExtra("SCORE", 0);
        endScore = (TextView) findViewById(R.id.endScore);
        endScore.setText("Score: " + score);


    }

    public void closeApp(View view) {
        finish();
        System.exit(0);
    }
}
