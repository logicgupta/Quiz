package com.logic.android.onetoone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.logic.android.onetoone.basicvideochat.R;

import java.util.ArrayList;

public class SlidingImageAdapter2  extends PagerAdapter {

    private ArrayList<String> IMAGES;
    private LayoutInflater inflater;
    private Context context;

    public SlidingImageAdapter2(ArrayList<String> IMAGES, Context context) {
        this.IMAGES = IMAGES;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View imageLayout =inflater.inflate(R.layout.imageslidderlayout2,container,false);
        assert imageLayout!=null;
        final ImageView imageView=(ImageView) imageLayout.findViewById(R.id.imageView);
        container.addView(imageLayout,0);
        Glide.with(context).load(IMAGES.get(position)).into(imageView);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }
}
