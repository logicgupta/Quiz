package com.logic.android.onetoone.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.views.authentication.UserLoginActivity;
import com.logic.android.onetoone.views.homeScreen.MainScreenActivity;
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                updateUI(FirebaseAuth.getInstance().getCurrentUser());

            }
        },3000);
    }

    public void updateUI(FirebaseUser user){
        if (user!=null){
            startActivity(new Intent(SplashActivity.this, MainScreenActivity.class));
            finish();
        }
        else {
            startActivity(new Intent(SplashActivity.this, UserLoginActivity.class));
            finish();
        }

    }
}
