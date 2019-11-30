package com.logic.android.onetoone.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.models.QuizDetails;
import com.logic.android.onetoone.views.quizCategory.SubCategoriesActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter  extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    Context context;
    RecyclerView recyclerView;
    List<QuizDetails> quizDetailsList=new ArrayList<>();

    public CategoryAdapter(Context context, RecyclerView recyclerView, List<QuizDetails> quizDetailsList) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.quizDetailsList = quizDetailsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.custom_category_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryAdapter.ViewHolder viewHolder, int i) {
        QuizDetails details=quizDetailsList.get(i);
        Glide.with(context).load(details.getImageUrl()).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    viewHolder.linearLayout.setBackground(resource);
                }
            }
            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                viewHolder.linearLayout.setBackground(placeholder);
            }
        });
        viewHolder.textView.setText(details.getCategoryName());
    }
    @Override
    public int getItemCount() {
        return quizDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout linearLayout;
        TextView textView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.linearLayout);
            textView=itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos=recyclerView.getChildLayoutPosition(itemView);
                    QuizDetails details=quizDetailsList.get(pos);
                    loadActivity(details.getCategoryName());
                }
            });
        }
    }

    public void loadActivity(String cat){
        Intent intent=new Intent(context, SubCategoriesActivity.class);
        intent.putExtra("category",cat);
        context.startActivity(intent);
    }
}
