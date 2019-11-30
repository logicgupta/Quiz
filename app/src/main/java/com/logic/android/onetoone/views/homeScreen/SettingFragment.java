package com.logic.android.onetoone.views.homeScreen;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.views.authentication.UserLoginActivity;

public class SettingFragment extends Fragment {

    CardView cardViewLogout,cardViewPrivacy,cardViewAbout,cardViewRate,cardViewTerm,cardViewCashback;
    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        getActivity().setTitle("Settings");
        cardViewLogout=getView().findViewById(R.id.logout);
        cardViewPrivacy=getView().findViewById(R.id.privacy);
        cardViewAbout=getView().findViewById(R.id.aboutUs);
        cardViewTerm=getView().findViewById(R.id.terms);
        cardViewRate=getView().findViewById(R.id.rate);
        cardViewCashback=getView().findViewById(R.id.cashback);

        cardViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), UserLoginActivity.class));
                getActivity().finish();
            }
        });

        cardViewPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cardViewRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cardViewAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cardViewTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cardViewCashback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



}
