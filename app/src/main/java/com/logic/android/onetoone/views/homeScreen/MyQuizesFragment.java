package com.logic.android.onetoone.views.homeScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.logic.android.onetoone.adapters.MyQuizesPager;
import com.logic.android.onetoone.basicvideochat.R;


public class MyQuizesFragment extends Fragment {


    public MyQuizesFragment() {
        // Required empty public constructor
    }

    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_myquizes, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("My Quizes");
        tabLayout=getActivity().findViewById(R.id.tabLayout);
        viewPager=getActivity().findViewById(R.id.viewPager);
        MyQuizesPager pager=new MyQuizesPager(getFragmentManager());
        viewPager.setAdapter(pager);
        tabLayout.setupWithViewPager(viewPager);

    }

}
