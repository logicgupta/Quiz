package com.logic.android.onetoone.adapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.logic.android.onetoone.views.homeScreen.MyQuiz.ActiveQuizFragment;
import com.logic.android.onetoone.views.homeScreen.MyQuiz.RecentQuizFragment;

public class MyQuizesPager extends FragmentStatePagerAdapter {

    public MyQuizesPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment=null;
        if (i==0){
            fragment=new ActiveQuizFragment();
            return fragment;
        }
        else {
            fragment=new RecentQuizFragment();
            return fragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0){
            return "Active";
        }
        else {
            return "Recent ";
        }
    }
}
