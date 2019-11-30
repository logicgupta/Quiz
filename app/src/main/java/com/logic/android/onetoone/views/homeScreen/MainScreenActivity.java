package com.logic.android.onetoone.views.homeScreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.logic.android.onetoone.basic_video_chat.dashboard.DashBoardActivity;
import com.logic.android.onetoone.basicvideochat.R;

public class MainScreenActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener  {
    BottomNavigationView navView;

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        loadFragmnet(new HomeFragment());
        navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.navigation_home);
        navView.setOnNavigationItemSelectedListener(this);
    }

    public boolean loadFragmnet(Fragment fragment){
        if (fragment!=null)
        {
            getSupportFragmentManager()
                    .beginTransaction().replace(R.id.fragment_container,fragment)
                    .commit();
            return true;
        }
        return  false;
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {

        if (exit) {
            //  super.onBackPressed();
            moveTaskToBack(true);
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                exit = false;
            }
        }, 3 * 1000);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment=null;
        switch (menuItem.getItemId()) {
            case R.id.navigation_question:
                fragment=new MyQuizesFragment();
                loadFragmnet(fragment);
                break;

            case R.id.navigation_home:
                fragment=new HomeFragment();
                loadFragmnet(fragment);
                break;
            case R.id.navigation_profile:
                fragment=new ProfileFragment();
                loadFragmnet(fragment);
                break;
            case R.id.navigation_videocall:
                startActivity(new Intent(this, DashBoardActivity.class));
                break;
            case R.id.navigation_setting:
                fragment=new SettingFragment();
                loadFragmnet(fragment);
                break;
        }
        return true;
    }
}
