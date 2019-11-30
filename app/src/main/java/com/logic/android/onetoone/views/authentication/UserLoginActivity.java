package com.logic.android.onetoone.views.authentication;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.logic.android.onetoone.basicvideochat.R;


public class UserLoginActivity extends AppCompatActivity {

    TextView signinTextView,signUpTextView;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
      //  getSupportActionBar().hide();
        signinTextView=findViewById(R.id.sign_in);
        signUpTextView=findViewById(R.id.sign_up);
        linearLayout=findViewById(R.id.loginlayoutBar);
        linearLayout.setVisibility(View.VISIBLE);
        loadFragment(new LoginFragment());
        signinTextView.setBackgroundResource(R.drawable.login_button_design);
        signinTextView.setTextColor(getResources().getColor(R.color.white));
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinTextView.setBackground(null);
                signinTextView.setTextColor(getResources().getColor(R.color.grey));
                signUpTextView.setBackgroundResource(R.drawable.login_button_design);
                signUpTextView.setTextColor(getResources().getColor(R.color.white));
                loadFragment(new RegisterFragment());

            }
        });
        signinTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpTextView.setBackground(null);
                signUpTextView.setTextColor(getResources().getColor(R.color.grey));
                signinTextView.setBackgroundResource(R.drawable.login_button_design);
                signinTextView.setTextColor(getResources().getColor(R.color.white));
                loadFragment(new LoginFragment());
            }
        });
    }
    public void loadFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameContainer,fragment)
                .commit();
    }

}
