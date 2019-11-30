package com.logic.android.onetoone.views.LiveQuiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.views.homeScreen.MainScreenActivity;

public class FinishQuizActivity extends AppCompatActivity {

    TextView button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_quiz);

        button=findViewById(R.id.gotIt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FinishQuizActivity.this, MainScreenActivity.class));
                finish();
            }
        });
    }
}
